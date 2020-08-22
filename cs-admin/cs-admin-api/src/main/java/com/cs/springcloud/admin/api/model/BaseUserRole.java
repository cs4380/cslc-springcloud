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
 * BaseUserRole 用户角色关系.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-14 13:50
 */
@Getter
@Setter
@TableName("base_user_role")
@ApiModel("用户角色关系")
public class BaseUserRole implements Serializable {

    private static final long serialVersionUID = -542985434064954538L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户Id
     */
    @ApiModelProperty("用户Id")
    @TableField("user_id")
    private String userId;
    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    @TableField("role_code")
    private String roleCode;
    /**
     * 租户Id
     */
    @ApiModelProperty("租户Id")
    @TableField("tenant_id")
    private String tenantId;
}