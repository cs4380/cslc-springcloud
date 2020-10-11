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
import com.cs.springcloud.base.common.validate.AddField;
import com.cs.springcloud.base.common.validate.UpdateField;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * BaseDept 基础部门信息.
 *
 * @author cs
 * @version 1.0
 * @date 2019-12-06 09:14
 */
@Getter
@Setter
@TableName("base_dept")
@ApiModel("基础部门")
public class BaseDept extends BaseModel implements Serializable {

    private static final long serialVersionUID = -4475854323090581308L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId
    @NotBlank(groups = UpdateField.class)
    private String id;

    /**
     * 父级部门id
     */
    @ApiModelProperty("父级部门id")
    @NotBlank(groups = UpdateField.class)
    @TableField("parent_id")
    private String parentId;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    @NotBlank(groups = {UpdateField.class, AddField.class})
    @Size(min = 1, max = 256)
    @TableField("dept_name")
    private String deptName;

    /**
     * 部门编码
     */
    @ApiModelProperty("部门编码")
    @NotBlank(groups = {UpdateField.class, AddField.class})
    @Size(min = 1, max = 128)
    @TableField("dept_code")
    private String deptCode;

    /**
     * 部门说明
     */
    @ApiModelProperty("部门说明")
    @TableField("description")
    private String description;

    /**
     * 是否删除(1:删除|0:未删除)
     */
    @ApiModelProperty("是否删除")
    @TableField("is_deleted")
    private Integer isDeleted;
}