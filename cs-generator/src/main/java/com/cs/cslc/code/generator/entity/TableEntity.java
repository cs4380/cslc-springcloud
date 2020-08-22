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

package com.cs.cslc.code.generator.entity;

import lombok.Data;

import java.util.List;

/**
 * 表数据
 */
@Data
public class TableEntity {
    /**
     * 表的名称
     */
    private String tableName;
    /**
     * 表的备注
     */
    private String comments;
    /**
     * 表的主键
     */
    private ColumnEntity pk;
    /**
     * 表的列名(不包含主键)
     */
    private List<ColumnEntity> columns;
    /**
     * 类名(第一个字母大写)，如：sys_user => SysUser
     */
    private String classNameMax;
    /**
     * 类名(第一个字母小写)，如：sys_user => sysUser
     */
    private String classNameMin;

}