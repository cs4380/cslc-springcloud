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
 * HttpResponseConstant http响应常量类.
 * <p>
 * 自定义业务http状态码
 * </p>
 *
 * @author cs
 * @version 1.0
 * @date 2019-09-28 21:25
 */
public class HttpStatusConstant {
    /**
     * 业务响应：成功
     */
    public static final int SUCCESS = 200;

    /**
     * 业务响应：失败
     */
    public static final int FAIL = 500;

    /**
     * 业务响应：没有访问权限
     */
    public static final int AUTHORITY_FAIL = 401;
}