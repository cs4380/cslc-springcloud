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

package com.cs.springcloud.admin.api.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * SysRoleAuthorization 系统角色授权关系.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-13 10:43
 */
@Getter
@Setter
@TableName("sys_role_authorization")
@ApiModel("系统角色授权")
public class SysRoleAuthorization implements Serializable {

    private static final long serialVersionUID = 3023826693434582074L;

    /**
     * 资源类型: 菜单
     */
    public static final Integer MENU_RESOURCE_TYPE = 0;
    /**
     * 资源类型: 按钮
     */
    public static final Integer BUTTON_RESOURCE_TYPE = 1;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    @TableField("role_code")
    private String roleCode;

    /**
     * 资源id
     */
    @ApiModelProperty("资源id")
    @TableField("resource_id")
    private String resourceId;

    /**
     * 菜单按钮对应的菜单编码
     */
    @ApiModelProperty("菜单按钮对应的菜单编码")
    @TableField("menu_code")
    private String menuCode;

    /**
     * 资源类型（0菜单|1按钮）
     */
    @ApiModelProperty("资源类型（0菜单|1按钮）")
    @TableField("resource_type")
    private Integer resourceType;

    /**
     * 租户Id
     */
    @ApiModelProperty("租户Id")
    @TableField("tenant_id")
    private String tenantId;
}