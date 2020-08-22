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

package com.cs.springcloud.admin.api.vo;

import com.cs.springcloud.admin.api.model.BaseDept;
import com.cs.springcloud.base.common.vo.TreeNodeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * BaseDeptTreeVO 部门树vo.
 *
 * @author cs
 * @version 1.0
 * @date 2019-12-09 14:02
 */
@Getter
@Setter
@ToString
@ApiModel("部门树vo")
public class BaseDeptTreeVO extends TreeNodeVO<BaseDept> {

    private static final long serialVersionUID = -4121445467608449L;

    /**
     * 部门编码
     */
    @ApiModelProperty("部门编码")
    private String deptCode;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptName;

    /**
     * 最后更新日期
     */
    @ApiModelProperty("最后更新日期")
    private LocalDateTime updateTime;

    /**
     * 创建日期
     */
    @ApiModelProperty("创建日期")
    private LocalDateTime createTime;

    /**
     * 构造部门树
     *
     * @param baseDept 部门信息
     */
    public BaseDeptTreeVO(BaseDept baseDept) {
        this.id = baseDept.getId();
        this.parentId = baseDept.getParentId();
        this.deptCode = baseDept.getDeptCode();
        this.deptName = baseDept.getDeptName();
        this.updateTime = baseDept.getUpdateTime();
        this.createTime = baseDept.getCreateTime();
    }
}