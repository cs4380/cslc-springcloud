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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cs.springcloud.admin.api.dto.UserInfoDTO;
import com.cs.springcloud.admin.api.model.BaseUser;
import com.cs.springcloud.admin.api.model.BaseUserRole;
import com.cs.springcloud.admin.server.mapper.BaseUserMapper;
import com.cs.springcloud.base.core.biz.BaseBusinessBiz;
import com.cs.springcloud.base.common.constant.CommonConstant;
import com.cs.springcloud.base.common.constant.HttpStatusConstant;
import com.cs.springcloud.base.common.constant.RedisKeysConstant;
import com.cs.springcloud.base.core.context.BaseContextHandler;
import com.cs.springcloud.base.common.exception.BusinessException;
import com.cs.springcloud.base.core.result.ObjectResult;
import com.cs.springcloud.base.core.result.TableResult;
import com.cs.springcloud.base.common.util.BeanUtil;
import com.cs.springcloud.base.common.util.Sha256Util;
import com.cs.springcloud.base.common.util.UUIDUtil;
import com.cs.springcloud.base.common.vo.ParamQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BaseUser 基础用户.
 *
 * @author cs
 * @version 1.0
 * @date 2019-09-30 15:23:58
 */
@Service
public class BaseUserBiz extends BaseBusinessBiz<BaseUserMapper, BaseUser> {

    @Autowired
    private BaseUserRoleBiz baseUserRoleBiz;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean insertModel(BaseUser entity) {
        entity.setId(UUIDUtil.randomUUID());
        String password = entity.getPassword();
        // Base64解码
        password = new String(Base64.getDecoder().decode(password));
        // 密码加密
        entity.setPassword(Sha256Util.getSHA256(password));
        return super.insertModel(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        BaseUser baseUser = super.getById(id);
        if (baseUser == null) {
            throw new BusinessException("请选择需要删除的用户！");
        }
        baseUser.setIsDeleted(CommonConstant.IS_FALSE);
        return super.updateById(baseUser);
    }

    @Override
    public List<BaseUser> selectListAll(ParamQuery params) {
        ParamQuery paramQuery = new ParamQuery(params);
        // 排除超级管理员
        paramQuery.put("is_super_admin", CommonConstant.IS_TRUE);

        String[] fields = new String[]{"id", "username", "name", "is_disabled", "create_time", "update_time"};
        List<BaseUser> result = super.selectFieldListAll(paramQuery, fields);
        return result;
    }

    /**
     * 通过用户账号获取用户信息
     * <p>
     * 排除逻辑删除，禁用的账号
     * </p>
     *
     * @param username 用户账号
     * @return 包含用户全部基本信息
     */
    public BaseUser getBaseUserByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return new BaseUser();
        }
        BaseUser baseUser = new BaseUser();
        baseUser.setUsername(username);
        return this.selectModel(baseUser);
    }

    /**
     * 查询用户基本信息
     *
     * @param userId 用户主键
     * @return 只包含用户基本信息
     */
    public BaseUser getUserInfoByUserId(String userId) {
        String[] fields = new String[]{"id", "username", "name", "user_sex", "mobile_phone", "portrait",
                "user_email", "create_time"};

        BaseUser baseUser = new BaseUser();
        baseUser.setId(userId);
        // 排除禁用用户
        baseUser.setIsDisabled(CommonConstant.IS_TRUE);
        return super.selectModel(baseUser, fields);
    }

    /**
     * 获取用户信息
     * <p>
     * 获取用户基本信息
     * </p>
     *
     * @return 用户基本信息
     */
    public UserInfoDTO getUserInfo() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        // 组装用户基本信息
        BaseUser baseUser = this.getUserInfoByUserId(BaseContextHandler.getUserId());
        userInfoDTO.setBaseUser(baseUser);
        return userInfoDTO;
    }

    /**
     * 获取用户基本信息：用户基本信息、角色集，排除禁用和删除
     *
     * @param username 用户账号
     */
    public UserInfoDTO getUserInfoByUsername(String username) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        // 组装用户基本信息
        BaseUser baseUser = this.getBaseUserByUsername(username);
        userInfoDTO.setBaseUser(baseUser);
        List<String> roles = this.getUserRolesByUserId(baseUser.getId());
        if (1 == baseUser.getIsSuperAdmin()) {
            roles.add(RedisKeysConstant.ADMIN_ROLES_AUTH_KEY);
        }
        userInfoDTO.setRoles(roles);
        return userInfoDTO;
    }

    /**
     * 用户权限信息
     * <p>
     * 菜单权限、按钮权限
     * </p>
     *
     * @return 用户权限信息
     */
    public UserInfoDTO getUserPermission() {
        List<String> roles = BaseContextHandler.getRoles();
        // 缓存角色菜单按钮权限
        UserInfoDTO userInfoDTO = (UserInfoDTO) redisTemplate.opsForHash().get(RedisKeysConstant.ROLES_BUTTON_AUTH_KEY, roles.get(0));
        return userInfoDTO;
    }

    /**
     * 修改密码
     *
     * @param password    原始密码
     * @param newPassword 新密码
     */
    public ObjectResult changePassword(String password, String newPassword) {
        ObjectResult result = new ObjectResult();
        // Base64解码
        password = new String(Base64.getDecoder().decode(password));
        newPassword = new String(Base64.getDecoder().decode(newPassword));

        // 加密后
        password = Sha256Util.getSHA256(password);
        newPassword = Sha256Util.getSHA256(newPassword);

        // 通过主键和原始密码查询记录
        BaseUser user = new BaseUser();
        user.setId(BaseContextHandler.getUserId());
        user.setPassword(password);
        int count = super.selectCount(user);
        if (count <= 0) {
            result.setStatus(HttpStatusConstant.FAIL);
            result.setMessage("原始密码不正确请重新输入！");
            return result;
        }
        // 更新密码
        user.setPassword(newPassword);
        this.updateById(user);
        // TODO 密码修改完成，更新用户token缓存
        return result;
    }

    /**
     * 通过部门编码获取用户列表
     *
     * @param deptCode 部门编码
     * @return 指定部门的用户列表
     */
    public TableResult<BaseUser> getDeptUsersByDeptCode(String deptCode, int page, int limit) {
        if (StringUtils.isBlank(deptCode)) {
            return new TableResult<>(0, new ArrayList<>());
        }
        Page pageHelper = PageHelper.startPage(page, limit);
        List<BaseUser> userList = this.baseMapper.selectDeptUsersByDeptCode(deptCode);

        if (BeanUtil.isNotEmpty(userList)) {
            return new TableResult<>(pageHelper.getTotal(), userList);
        } else {
            return new TableResult<>();
        }
    }

    /**
     * 获取用户角色关系
     *
     * @return 角色列表
     */
    public List<String> getUserRolesByUserId(String userId) {
        List<BaseUserRole> userRoleList = baseUserRoleBiz.getUserRoleByUserId(userId);
        return userRoleList.stream().map(userRole -> userRole.getRoleCode()).collect(Collectors.toList());
    }

    /**
     * 设置用户角色关系
     *
     * @param roleCodes 角色编码集
     */
    @Transactional(rollbackFor = Exception.class)
    public void setUserRole(String userId, List<String> roleCodes) {
        // 删除用户关系
        baseUserRoleBiz.delUserRoleByRoleCodes(userId);
        // 新增用户关系
        baseUserRoleBiz.addUserRole(userId, roleCodes);
    }

    /**
     * 排除指定部门的用户列表
     *
     * @param excludeDeptCode 排除指定部门
     * @param page            页码
     * @param limit           页容量
     * @return 获取所有部门用户列表
     */
    public TableResult<BaseUser> getUsersExcludeDept(String excludeDeptCode, int page, int limit) {
        Page pageHelper = PageHelper.startPage(page, limit);
        List<BaseUser> userList = this.baseMapper.selectUsersExcludeDept(excludeDeptCode);
        if (BeanUtil.isNotEmpty(userList)) {
            return new TableResult<>(pageHelper.getTotal(), userList);
        } else {
            return new TableResult<>();
        }
    }
}