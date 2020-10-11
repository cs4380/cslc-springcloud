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

package com.cs.springcloud.base.common.constant;

/**
 * CommonConstant 核心常量类.
 *
 * @author cs
 * @version 1.0
 * @date 2018.11.29
 */
public class CommonConstant {
    /**
     * 逻辑值：假
     */
    public static final int IS_FALSE = 1;

    /**
     * 逻辑值：真
     */
    public static final int IS_TRUE = 0;

    /**
     * 页容量最大值
     * <p>
     * 最大翻页容量，防止数据量过大
     * </p>
     */
    public static final Integer LIMIT_MAX = 200;

    /**
     * 树根节点
     * <p>
     * 树形结构默认根节点标志
     * </p>
     */
    public static final String ROOT = "root";

    /**
     * 翻页参数常量类
     */
    public class Page {
        /**
         * 页码
         */
        public static final String PAGE = "page";
        /**
         * 页容量（每页数量）
         */
        public static final String LIMIT = "limit";
    }
}