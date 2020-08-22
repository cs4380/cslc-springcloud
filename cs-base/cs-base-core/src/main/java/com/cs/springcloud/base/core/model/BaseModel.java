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

package com.cs.springcloud.base.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * BaseModel 实体基类.
 * <p>
 * 表的创建操作记录字段，中间关系表不继承此类
 * </p>
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-08 09:17
 */
@Setter
@Getter
@ApiModel("基础Model类")
public class BaseModel {
    /**
     * 创建日期
     */
    @ApiModelProperty("创建日期")
    private LocalDateTime createTime;
    /**
     * 创建用户Id
     */
    @ApiModelProperty("创建用户Id")
    private String createUserId;
    /**
     * 创建用户姓名
     */
    @ApiModelProperty("创建用户姓名")
    private String createUserName;
    /**
     * 最后更新日期
     */
    @ApiModelProperty("最后更新日期")
    private LocalDateTime updateTime;
    /**
     * 最后更新用户Id
     */
    @ApiModelProperty("最后更新用户Id")
    private String updateUserId;
    /**
     * 最后更新用户姓名
     */
    @ApiModelProperty("最后更新用户姓名")
    private String updateUserName;
    /**
     * 租户Id
     */
    @ApiModelProperty("租户Id")
    private String tenantId;
}