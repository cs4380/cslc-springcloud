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

package com.cs.springcloud.admin.server.rest;

import com.cs.springcloud.admin.api.model.SysRole;
import com.cs.springcloud.admin.api.vo.SysRoleTreeVO;
import com.cs.springcloud.admin.server.biz.SysRoleBiz;
import com.cs.springcloud.auth.client.annotation.CheckAuthToken;
import com.cs.springcloud.base.core.result.ObjectResult;
import com.cs.springcloud.base.core.rest.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SysRole 系统基础角色接口实现类.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-06 14:33
 */
@CheckAuthToken
@RestController
@RequestMapping("/sysRoles")
@Api(tags = "系统基础角色表")
public class SysRoleController extends BaseController<SysRoleBiz, SysRole> {

    @GetMapping("/tree")
    @ApiOperation(value = "获取角色树")
    public ObjectResult<List<SysRoleTreeVO>> getSysRoleTree() {
        ObjectResult<List<SysRoleTreeVO>> result = new ObjectResult<>();
        result.setData(this.baseBiz.getSysRoleTree());
        return result;
    }

    @GetMapping("/refresh/role/auth")
    @ApiOperation(value = "刷新角色权限缓存")
    public ObjectResult<Void> refreshRoleCache() {
        ObjectResult<Void> result = new ObjectResult<>();
        this.baseBiz.refreshRoleCache();
        return result;
    }
}