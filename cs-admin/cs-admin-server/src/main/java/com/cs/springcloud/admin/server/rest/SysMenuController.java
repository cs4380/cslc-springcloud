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

import com.cs.springcloud.admin.api.model.SysMenu;
import com.cs.springcloud.admin.api.vo.SysMenuTreeVO;
import com.cs.springcloud.admin.server.biz.SysMenuBiz;
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
 * SysMenu 系统基础菜单表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-11 19:51
 */
@CheckAuthToken
@RestController
@RequestMapping("/sysMenus")
@Api(tags = "系统基础菜单表")
public class SysMenuController extends BaseController<SysMenuBiz, SysMenu> {

    @GetMapping("/tree")
    @ApiOperation(value = "获取菜单树")
    public ObjectResult<List<SysMenuTreeVO>> getSysMenuTree() {
        ObjectResult<List<SysMenuTreeVO>> result = new ObjectResult<>();
        result.setData(this.baseBiz.getSysMenuTree());
        return result;
    }
}