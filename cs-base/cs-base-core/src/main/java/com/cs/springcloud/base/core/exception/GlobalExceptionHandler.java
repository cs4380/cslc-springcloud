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

package com.cs.springcloud.base.core.exception;

import com.cs.springcloud.base.common.exception.AuthenticationException;
import com.cs.springcloud.base.common.exception.BusinessException;
import com.cs.springcloud.base.common.exception.LoginException;
import com.cs.springcloud.base.core.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ExceptionHandler 全局异常处理起.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-25 17:05
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthenticationException.class)
    public Result authenticationHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws Exception {
        response.setStatus(401);
        return new Result(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(value = LoginException.class)
    public Result loginExceptionHandler(HttpServletRequest request, HttpServletResponse response, LoginException e) throws Exception {
        response.setStatus(403);
        return new Result(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    public Result businessExceptionHandler(HttpServletRequest request, HttpServletResponse response, BusinessException e) throws Exception {
        response.setStatus(200);
        return new Result(e.getStatus(), e.getMessage());
    }
}