/*
 * Copyright (c) 2020, chenshuai (cs4380@163.com).
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.cs.springcloud.admin.server.biz;

import com.cs.springcloud.admin.api.dto.MenuButtonCodeDTO;
import com.cs.springcloud.admin.api.dto.MenuButtonDTO;
import com.cs.springcloud.admin.api.dto.UserInfoDTO;
import com.cs.springcloud.admin.api.model.SysMenu;
import com.cs.springcloud.admin.api.model.SysMenuButton;
import com.cs.springcloud.admin.api.model.SysRole;
import com.cs.springcloud.admin.api.model.SysRoleAuthorization;
import com.cs.springcloud.admin.api.vo.SysRoleTreeVO;
import com.cs.springcloud.admin.server.mapper.SysRoleMapper;
import com.cs.springcloud.base.core.biz.BaseBusinessBiz;
import com.cs.springcloud.base.common.constant.CommonConstant;
import com.cs.springcloud.base.common.constant.HttpStatusConstant;
import com.cs.springcloud.base.common.constant.InitialCapacityConstant;
import com.cs.springcloud.base.common.constant.RedisKeysConstant;
import com.cs.springcloud.base.common.exception.BusinessException;
import com.cs.springcloud.base.common.util.BeanUtil;
import com.cs.springcloud.base.common.util.TreeUtil;
import com.cs.springcloud.base.common.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SysRole 系统基础角色表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-06 14:33
 */
@Service
public class SysRoleBiz extends BaseBusinessBiz<SysRoleMapper, SysRole> {

    @Autowired
    private SysRoleAuthorizationBiz roleAuthorizationBiz;

    @Autowired
    private SysMenuBiz sysMenuBiz;

    @Autowired
    private SysMenuButtonBiz menuButtonBiz;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean insertModel(SysRole entity) {
        BeanUtil.beanAttributeValueTrim(entity);
        entity.setId(UUIDUtil.randomUUID());
        return super.insertModel(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 主键id
     */
    @Override
    public boolean removeById(Serializable id) {
        if (null == id) {
            throw new BusinessException("请选择待删除对象！", HttpStatusConstant.FAIL);
        }
        int sons = this.getSonCountByParentId(id.toString());
        if (sons > 0) {
            throw new BusinessException("请先删除当前角色的子角色！", HttpStatusConstant.FAIL);
        }
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode(sysRole.getId() + "_" + sysRole.getRoleCode());
        sysRole.setId(id.toString());
        sysRole.setIsDeleted(CommonConstant.IS_FALSE);
        return super.updateById(sysRole);
    }

    /**
     * 通过父级角色id，统计子集个数
     *
     * @param parentId 父级角色id
     */
    public int getSonCountByParentId(String parentId) {
        if (StringUtils.isBlank(parentId)) {
            return 0;
        }
        SysRole sysRole = new SysRole();
        sysRole.setParentId(parentId);
        sysRole.setIsDeleted(CommonConstant.IS_TRUE);
        return this.selectCount(sysRole);
    }

    /**
     * 获取菜单树
     */
    public List<SysRoleTreeVO> getSysRoleTree() {
        String[] fields = {"id", "parent_id", "role_code", "role_name", "description"};
        // 获取全部数据
        List<SysRole> dictTypeList = this.selectFieldListAll(fields);
        // 缓存树型结构数据
        List<SysRoleTreeVO> tree = new ArrayList<>();
        // 构造数据
        if (BeanUtil.isNotEmpty(dictTypeList)) {
            for (SysRole sysRole : dictTypeList) {
                // 根节点展开
                tree.add(new SysRoleTreeVO(sysRole.getId(), sysRole.getParentId(), sysRole.getRoleCode(),
                        sysRole.getRoleName(), sysRole.getDescription()));
            }
        }
        List<SysRoleTreeVO> result = TreeUtil.buildByRecursive(tree, CommonConstant.ROOT);
        return BeanUtil.isEmpty(result) ? new ArrayList<>() : result;
    }

    /**
     * 获取所有角色编码
     *
     * @return 未删除的角色编码集
     */
    public List<String> getRoles() {
        List<SysRole> roleList = super.selectFieldListAll(new String[]{"role_code"});
        List<String> roles = roleList.stream().map(role -> role.getRoleCode()).collect(Collectors.toList());
        return roles;
    }

    /**
     * 刷新角色权限缓存
     */
    public void refreshRoleCache() {
        List<String> roles = this.getRoles();
        for (String role : roles) {
            UserInfoDTO userInfoDTO = new UserInfoDTO();

            List<SysRoleAuthorization> roleAuthList = roleAuthorizationBiz.getRoleAuthByRoleCode(role);

            // 获取角色对应的菜单ids和按钮ids
            List<String> menuIdList = new ArrayList<>(InitialCapacityConstant.INITIAL_256_NUMBER);
            List<Integer> menuButtonIds = new ArrayList<>(InitialCapacityConstant.INITIAL_512_NUMBER);
            // 获取角色权限集
            roleAuthList.forEach(roleAuth -> {
                if (SysRoleAuthorization.MENU_RESOURCE_TYPE.equals(roleAuth.getResourceType())) {
                    menuIdList.add(roleAuth.getResourceId());
                } else {
                    menuButtonIds.add(Integer.valueOf(roleAuth.getResourceId()));
                }
            });

            // 查询用户的角色菜单权限
            List<SysMenu> sysMenuList = sysMenuBiz.getMenuListByMenuIds(menuIdList);
            // 查询用户的角色按钮权限
            List<MenuButtonCodeDTO> menuButtonList = menuButtonBiz.getMenuCodeAndButCodeByButIds(menuButtonIds);

            this.refreshButtonAuth(userInfoDTO, sysMenuList, menuButtonList);

            this.refreshMenuButtonCache(role, menuButtonIds);

        }

        this.refreshAdminAuth();
    }

    /**
     * 缓存角色按钮权限
     *
     * @param role          角色编码
     * @param menuButtonIds 菜单按钮列表
     */
    private void refreshMenuButtonCache(String role, List<Integer> menuButtonIds) {
        // 获取角色对应菜单权限
        List<SysMenuButton> buttons = menuButtonBiz.getMenuButtonByIds(menuButtonIds);
        // 缓存角色按钮权限
        redisTemplate.opsForHash().put(RedisKeysConstant.ROLES_AUTH_KEY, role, buttons);
    }

    /**
     * 缓存菜单按钮权限
     */
    private void refreshButtonAuth(UserInfoDTO userInfoDTO, List<SysMenu> sysMenuList, List<MenuButtonCodeDTO> menuButtonList) {
        // 组装用户的角色菜单权限
        userInfoDTO.setMenuTree(sysMenuBiz.getMenuTreeSysMenuTree(sysMenuList));
        // 组装用户的角色按钮权限
        userInfoDTO.setMenuButton(this.generateMenuButton(menuButtonList));
        // 缓存角色菜单按钮权限
        redisTemplate.opsForHash().put(RedisKeysConstant.ROLES_BUTTON_AUTH_KEY, RedisKeysConstant.ADMIN_ROLES_AUTH_KEY, userInfoDTO);
    }

    /**
     * 缓存超级管理员权限
     */
    private void refreshAdminAuth() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        // 获取系统所有权限
        List<SysMenu> sysMenuList = sysMenuBiz.getAllMenuList();
        List<MenuButtonCodeDTO> menuButtonList = menuButtonBiz.getAllMenuCodeAndButCode();
        this.refreshButtonAuth(userInfoDTO, sysMenuList, menuButtonList);
    }


    /**
     * 菜单和按钮管理列表转换json格式
     *
     * @param menuButtonList 菜单和按钮管理列表
     *                       数据格式：
     *                       [
     *                       menuCode1:butCode1
     *                       menuCode1:butCode2
     *                       ]
     * @return 数据格式：
     * menuButton:
     * {
     * menuCode1:{ butCode1:1, butCode2:1, ... }，
     * menuCode2:{ butCode1:1, butCode2:1, ... },
     * ...
     * }
     */
    private MenuButtonDTO<String, MenuButtonDTO<String, Integer>> generateMenuButton(
            List<MenuButtonCodeDTO> menuButtonList) {
        MenuButtonDTO<String, MenuButtonDTO<String, Integer>> menuButton = new MenuButtonDTO<>();
        for (MenuButtonCodeDTO menuButtonCodeDTO : menuButtonList) {
            // 菜单编码
            String menuCode = menuButtonCodeDTO.getMenuCode();
            // 关系数据丢失则跳过
            if (StringUtils.isBlank(menuCode)) {
                continue;
            }
            // 按钮编码
            String buttonCode = menuButtonCodeDTO.getButtonCode();

            MenuButtonDTO<String, Integer> tmpeMenuButtonDTO = menuButton.get(menuCode);
            if (BeanUtil.isEmpty(tmpeMenuButtonDTO)) {
                tmpeMenuButtonDTO = new MenuButtonDTO<>();
            }
            // value值只是为了前端使用，不做其他处理
            tmpeMenuButtonDTO.put(buttonCode, 1);
            menuButton.put(menuCode, tmpeMenuButtonDTO);
        }
        return menuButton;
    }
}