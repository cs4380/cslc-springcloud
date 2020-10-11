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

import com.cs.springcloud.base.common.vo.TreeNodeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SysDictValue 数据字典类型VO.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-07 21:41
 */
@Setter
@Getter
@ToString
@ApiModel("数据字典类型VO")
public class SysDictTypeTreeVO extends TreeNodeVO<SysDictTypeTreeVO> {

    private static final long serialVersionUID = -7437892276151591267L;

    /**
     * 是否展开
     */
    @ApiModelProperty("是否展开")
    private boolean expand;

    /**
     * 字典类型code
     */
    @ApiModelProperty("字典类型code")
    private String dictTypeCode;

    /**
     * 初始化节点，默认为展开
     *
     * @param id       节点id
     * @param parentId 父节点id
     * @param label    节点标签
     */
    public SysDictTypeTreeVO(Object id, Object parentId, String label) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
    }

    /**
     * 初始化节点，默认为展开
     *
     * @param id           节点id
     * @param parentId     父节点id
     * @param label        节点标签
     * @param dictTypeCode 字典类型code
     */
    public SysDictTypeTreeVO(Object id, Object parentId, String label, String dictTypeCode) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
        this.dictTypeCode = dictTypeCode;
    }

    /**
     * 初始化节点
     *
     * @param id           节点id
     * @param parentId     父节点id
     * @param label        节点标签
     * @param dictTypeCode 字典类型code
     * @param expand       是否展开
     */
    public SysDictTypeTreeVO(Object id, Object parentId, String label, String dictTypeCode, boolean expand) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
        this.dictTypeCode = dictTypeCode;
        this.expand = expand;
    }


}