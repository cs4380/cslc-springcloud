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

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * SysDictType 数据字典类型.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-07 21:41
 */
@Getter
@Setter
@TableName("sys_dict_type")
@ApiModel("数据字典类型(分组)")
public class SysDictType extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1053998591063973602L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId
    private String id;

    /**
     * 类型编码
     */
    @ApiModelProperty("字典类型编码")
    @Size(min = 1, max = 128)
    @TableField("dict_type_code")
    private String dictTypeCode;

    /**
     * 类型名称
     */
    @ApiModelProperty("字典类型名称")
    @Size(min = 1, max = 256)
    @TableField("dict_type_name")
    private String dictTypeName;

    /**
     * 父id
     */
    @ApiModelProperty("父id")
    @TableField("parent_id")
    private String parentId;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @TableField("description")
    private String description;

    /**
     * 是否删除(1:删除|0:未删除)
     */
    @ApiModelProperty("是否删除")
    @TableField("is_deleted")
    private Integer isDeleted;
}