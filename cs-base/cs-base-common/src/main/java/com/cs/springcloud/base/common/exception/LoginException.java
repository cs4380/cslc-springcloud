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

package com.cs.springcloud.base.common.exception;

import com.cs.springcloud.base.common.enums.AuthenticationEnums;

/**
 * LoginException 登陆请求异常.
 * <p>
 * 未登录或者登陆时验证错误采用此异常
 * </p>
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-01 13:29
 */
public class LoginException extends BaseException {

    public LoginException() {
        super(AuthenticationEnums.UNKNOWN_ACCOUNT.getValue());
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, int status) {
        super(message, status);
    }
}