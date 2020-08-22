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

package com.cs.springcloud.base.core.constant;

/**
 * JwtUserConstances jwt中的用户信息字段.
 *
 * @author cs
 * @version 1.0
 * @date 2019-09-28 21:25
 */
public class JwtUserConstant {

    /**
     * 用户账号
     */
    public static final String JWT_KEY_USER_NAME = "username";

    /**
     * 用户姓名
     */
    public static final String JWT_KEY_NAME = "name";


    /**
     * 用户Id
     */
    public static final String JWT_KEY_USER_ID = "userId";

    /**
     * 用户部门
     */
    public static final String JWT_KEY_DEPARTS = "departs";

    /**
     * 租户
     */
    public static final String JWT_KEY_TENANT_ID = "tenantId";

    /**
     * 角色
     */
    public static final String JWT_KEY_USER_ROLES = "roles";

    /**
     * 用户token
     */
    public static final String JWT_KEY_USER_IS_ADMIN = "isSuperAdmin";
    /**
     * token过期日期
     */
    public static final String JWT_KEY_EXPIRE = "expireTime";
}