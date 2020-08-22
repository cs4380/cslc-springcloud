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

import com.cs.springcloud.admin.api.model.BaseUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * UserInfoDTO 用户信息和权限DTO.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-14 10:52
 */
@Setter
@Getter
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 5492050756300144926L;

    /**
     * 用户基本信息
     */
    @ApiModelProperty("用户基本信息")
    private BaseUser baseUser;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码集")
    private List<String> roles;

    /**
     * 角色对应的菜单树
     */
    @ApiModelProperty("角色对应的菜单树")
    private List<MenuTreeDTO> menuTree;

    /**
     * 菜单按钮对应关系
     * <p>
     * 数据格式：
     * menuButton:
     * {
     * menuCode1:{ butCode1:1, butCode2:1, ... }，
     * menuCode2:{ butCode1:1, butCode2:1, ... },
     * ...
     * }
     * 目的：
     * 方便前端做按钮权限认证
     * </p>
     */
    @ApiModelProperty("菜单按钮对应关系")
    private MenuButtonDTO<String, MenuButtonDTO<String, Integer>> menuButton;

}