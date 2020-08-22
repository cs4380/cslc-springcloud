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

import com.cs.springcloud.admin.api.model.SysMenuButton;
import com.cs.springcloud.admin.server.biz.SysMenuButtonBiz;
import com.cs.springcloud.auth.client.annotation.CheckAuthToken;
import com.cs.springcloud.base.core.rest.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SysMenuButton 系统基础菜单按钮表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-12 10:22
 */
@CheckAuthToken
@RestController
@RequestMapping("/sysMenuButtons")
@Api(tags = "系统基础菜单按钮表")
public class SysMenuButtonController extends BaseController<SysMenuButtonBiz, SysMenuButton> {

}