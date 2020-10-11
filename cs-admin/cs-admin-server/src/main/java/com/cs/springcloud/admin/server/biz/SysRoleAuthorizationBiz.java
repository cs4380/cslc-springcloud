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

import com.cs.springcloud.admin.api.model.SysRoleAuthorization;
import com.cs.springcloud.admin.api.vo.RoleAuthorizationVO;
import com.cs.springcloud.admin.server.mapper.SysRoleAuthorizationMapper;
import com.cs.springcloud.base.core.biz.BaseBusinessBiz;
import com.cs.springcloud.base.common.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SysRoleAuthorization 系统角色授权关系表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-13 10:43
 */
@Service
public class SysRoleAuthorizationBiz extends BaseBusinessBiz<SysRoleAuthorizationMapper, SysRoleAuthorization> {

    /**
     * 添加指定角色的菜单权限
     *
     * @param roleCode    角色编码
     * @param roleMenusVO 角色菜单关系
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void setRoleAuth(String roleCode, RoleAuthorizationVO roleMenusVO) {
        Integer resourceType = roleMenusVO.getResourceType();
        List<String> resourceIds = roleMenusVO.getResourceIds();
        String menuCode = roleMenusVO.getMenuCode();
        // 删除指定角色的的菜单权限
        this.delByRoleCode(roleCode, resourceType, roleMenusVO.getMenuCode());
        // 添加指定角色的菜单权限
        SysRoleAuthorization roleAuth;
        for (String resourceId : resourceIds) {
            roleAuth = new SysRoleAuthorization();
            roleAuth.setRoleCode(roleCode);
            roleAuth.setResourceId(resourceId);
            roleAuth.setResourceType(resourceType);
            roleAuth.setMenuCode(menuCode);
            this.baseMapper.insert(roleAuth);
        }
    }

    /**
     * 删除指定角色的权限
     * <p>
     * 可指定资源类型
     * </p>
     *
     * @param roleCode     角色编码
     * @param resourceType 资源类型
     * @param menuCode     菜单按钮对应的菜单编码
     */
    public int delByRoleCode(String roleCode, Integer resourceType, String menuCode) {
        if (StringUtils.isBlank(roleCode)) {
            return 0;
        }
        SysRoleAuthorization roleAuth = new SysRoleAuthorization();
        roleAuth.setRoleCode(roleCode);
        roleAuth.setMenuCode(menuCode);
        if (null != resourceType) {
            roleAuth.setResourceType(resourceType);
        }
        return super.deleteModel(roleAuth);
    }

    /**
     * 获取指定角色的菜单或者按钮权限
     *
     * @param roleCode     角色编码
     * @param resourceType 权限类型
     * @return 权限ids
     */
    public List<String> getAuthIdByRoleCode(String roleCode, Integer resourceType) {
        List<SysRoleAuthorization> roleAuthList = this.getAuthByRoleCode(roleCode, resourceType);
        List<String> resourceIds = roleAuthList.stream().map(SysRoleAuthorization::getResourceId).collect(Collectors.toList());
        return resourceIds;
    }

    /**
     * 获取指定角色权限
     *
     * @param roleCode     角色编码
     * @param resourceType 资源类型
     */
    public List<SysRoleAuthorization> getAuthByRoleCode(String roleCode, Integer resourceType) {
        if (StringUtils.isBlank(roleCode)) {
            return new ArrayList<>();
        }
        SysRoleAuthorization roleAuth = new SysRoleAuthorization();
        roleAuth.setRoleCode(roleCode);
        if (null != resourceType) {
            roleAuth.setResourceType(resourceType);
        }
        return super.selectModelList(roleAuth);
    }

    /**
     * 通过角色编码获取角色权限
     *
     * @param roleCodeList 角色编码集合
     */
    public List<SysRoleAuthorization> getRoleAuthByRoleCodes(List<String> roleCodeList) {
        if (BeanUtil.isEmpty(roleCodeList)) {
            return new ArrayList<>();
        }
        return this.baseMapper.selectRoleAuthByRoleCodes(roleCodeList);
    }

    /**
     * 获取角色对应的菜单权限集合
     *
     * @param roleCode 角色编码
     */
    public List<String> getMenuAuthByRoleCode(String roleCode) {
        return this.baseMapper.selectMenuAuthByRoleCode(roleCode);
    }

    /**
     * 通过角色编码获取角色权限
     *
     * @param roleCode 角色编码
     */
    public List<SysRoleAuthorization> getRoleAuthByRoleCode(String roleCode) {
        if (StringUtils.isEmpty(roleCode)) {
            return new ArrayList<>();
        }
        SysRoleAuthorization roleAuth = new SysRoleAuthorization();
        roleAuth.setRoleCode(roleCode);
        return super.selectModelList(roleAuth);
    }
}