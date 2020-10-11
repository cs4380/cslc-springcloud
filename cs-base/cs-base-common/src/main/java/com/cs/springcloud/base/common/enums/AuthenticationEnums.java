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
 * AuthenticationEnums 认证状态码.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-30 14:49
 */
@Getter
public enum AuthenticationEnums {
    /**
     * 用户授权过期或者其他端已登出，请重新登陆！
     */
    AUTH_FAIL(401000, "访问权限异常，请联系管理员！"),
    EXPIRED_TOKEN(401001, "用户授权过期或者其他端已登出，请重新登陆！"),
    UNKNOWN_ACCOUNT(401002, "用户账号或密码错误！"),
    DISABLED_ACCOUNT(401003, "用户账号已禁用，请联系管理员！");

    /**
     * 编码
     */
    private int code;
    /**
     * 提示信息
     */
    private String value;

    AuthenticationEnums(int code, String value) {
        this.code = code;
        this.value = value;
    }
}