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

package com.cs.springcloud.admin.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.springcloud.admin.api.dto.RoleMenuAuthDTO;
import com.cs.springcloud.admin.api.model.SysRoleAuthorization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SysRoleAuthorization 系统角色授权关系表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-13 10:43
 */
public interface SysRoleAuthorizationMapper extends BaseMapper<SysRoleAuthorization> {
    /**
     * 通过角色编码获取角色权限
     *
     * @param roleCodeList 角色编码集合
     * @return
     */
    List<SysRoleAuthorization> selectRoleAuthByRoleCodes(@Param("roleCodeList") List<String> roleCodeList);

    /**
     * 获取角色的菜单权限
     *
     * @param roleCode 角色编码
     * @return
     */
    List<String> selectMenuAuthByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 获取角色菜单按钮权限列表
     *
     * @return 角色菜单列表
     */
    List<RoleMenuAuthDTO> selectRoleMenus();
}