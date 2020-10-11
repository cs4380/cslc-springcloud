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

package com.cs.springcloud.base.core.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseResponse 列表数据响应类.
 *
 * @author cs
 * @version 1.0
 * @date 2020-01-14
 */
@Getter
@Setter
@ApiModel("响应列表对象")
public class TableResult<T> extends Result {

    @ApiModelProperty("响应列表对象")
    private TableData<T> data;

    /**
     * 构建空列表
     */
    public TableResult() {
        this.data = new TableData<>(0, new ArrayList<>());
    }

    /**
     * 构建列表
     *
     * @param total 列表总数
     * @param rows  列表集合
     */
    public TableResult(long total, List<T> rows) {
        this.data = new TableData<>(total, rows);
    }

    /**
     * 列表数据实体
     */
    @Getter
    @ApiModel("响应对象")
    @Setter
    public class TableData<T> {
        /**
         * 总条数
         */
        @ApiModelProperty("总条数")
        private long total;

        /**
         * 列表集合
         */
        @ApiModelProperty("列表集合")
        private List<T> rows;

        public TableData(long total, List<T> rows) {
            this.total = total;
            this.rows = rows;
        }
    }
}