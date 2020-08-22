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

package com.cs.springcloud.admin.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RoleMenusDTO 角色菜单按钮权限列表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-12-08 17:55
 */
@Data
public class RoleMenuAuthDTO {
    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String roleCode;
    /**
     * 请求地址
     */
    @ApiModelProperty("请求地址")
    private String url;
    /**
     * 请求方式
     */
    @ApiModelProperty("请求方式")
    private String method;
}