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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cs.springcloud.base.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * SysRole 系统基础角色.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-06 14:33
 */
@Getter
@Setter
@TableName("sys_role")
@ApiModel("系统角色")
public class SysRole extends BaseModel implements Serializable {

    private static final long serialVersionUID = 146560826438800043L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId
    private String id;

    /**
     * 父级菜单id
     */
    @ApiModelProperty("父级菜单id")
    @TableField("parent_id")
    private String parentId;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    @TableField("role_code")
    private String roleCode;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @TableField("role_name")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    @TableField("description")
    private String description;

    /**
     * 是否删除（1是|0否）
     */
    @ApiModelProperty("是否删除")
    @TableField("is_deleted")
    private Integer isDeleted;
}