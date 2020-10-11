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

package com.cs.springcloud.base.common.enums;

import lombok.Getter;

/**
 * HttpStatusEnums .
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-04 15:56
 */
@Getter
public enum HttpStatusEnums {
    /**
     * 业务响应：成功
     */
    SUCCESS(200, "success"),
    /**
     * 业务响应：失败
     */
    FAIL(500, "fail"),
    /**
     * 业务响应：没有访问权限
     */
    AUTHORITY_FAIL(401, "authority_fail");

    /**
     * 编码
     */
    private int code;
    /**
     * 提示信息
     */
    private String value;

    HttpStatusEnums(int code, String value) {
        this.code = code;
        this.value = value;
    }
}