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

package com.cs.springcloud.base.common.vo;

import com.cs.springcloud.base.common.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ParamQuery 查询参数.
 * <p>
 * 无法确认查询参数时，使用此类，否则采用自定义VO层接收
 * </p>
 *
 * @author cs
 * @version 1.0
 * @date 2020-01-14
 */
@Setter
@Getter
public class ParamQuery extends LinkedHashMap<String, Object> {

    /**
     * 默认页码
     */
    private int page = 1;

    /**
     * 默认每页条数
     */
    private int limit = 10;

    public ParamQuery(Map<String, Object> params) {
        // 设置查询参数
        this.putAll(params);


        // 获取请求参数中的页码
        Object page = params.get(CommonConstant.Page.PAGE);
        if (null != page) {
            this.page = Integer.parseInt(page.toString());
        }

        // 获取请求参数中的页容量，如果超过200则替换为200
        Object limit = params.get(CommonConstant.Page.LIMIT);
        if (null != limit) {
            this.limit = Integer.parseInt(limit.toString());
            if (this.limit > CommonConstant.LIMIT_MAX) {
                this.limit = CommonConstant.LIMIT_MAX;
            }
        }

        // 删除多余的分页查询参数
        this.remove(CommonConstant.Page.PAGE);
        this.remove(CommonConstant.Page.LIMIT);
    }

    /**
     * 初始化时优化分页参数
     */
    public ParamQuery() {
        // 获取请求参数中的页码
        Object page = this.get(CommonConstant.Page.PAGE);
        if (null != page) {
            this.page = Integer.parseInt(page.toString());
        }

        // 获取请求参数中的页容量，如果超过200则替换为200
        Object limit = this.get(CommonConstant.Page.LIMIT);
        if (null != limit) {
            this.limit = Integer.parseInt(limit.toString());
            if (this.limit > CommonConstant.LIMIT_MAX) {
                this.limit = CommonConstant.LIMIT_MAX;
            }
        }

        // 删除多余的分页查询参数
        this.remove(CommonConstant.Page.PAGE);
        this.remove(CommonConstant.Page.LIMIT);
    }
}