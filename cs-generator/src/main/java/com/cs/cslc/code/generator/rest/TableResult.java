/*
 *MIT License
 *
 *Copyright (c) 2019 chenshuai cs4380@163.com
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in all
 *copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *SOFTWARE.
 */

package com.cs.cslc.code.generator.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseResponse 表格数据响应类.
 *
 * @author cs
 * @version 1.0
 * @date 2018.11.29
 * @description
 */
@Getter
@Setter
public class TableResult<T> {

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
    @Setter
    public class TableData<T> {
        /**
         * 总条数
         */
        private long total;

        /**
         * 列表集合
         */
        private List<T> rows;

        public TableData(long total, List<T> rows) {
            this.total = total;
            this.rows = rows;
        }
    }
}