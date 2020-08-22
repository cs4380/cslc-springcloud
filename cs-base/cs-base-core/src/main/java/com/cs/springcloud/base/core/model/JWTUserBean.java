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

package com.cs.springcloud.base.core.model;

import lombok.Data;

import java.util.List;

/**
 * JWTUserBean jwt用户信息.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-02 18:22
 * @description
 */
@Data
public class JWTUserBean {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 是否超级管理员
     */
    private Integer isSuperAdmin;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 用户角色
     */
    private List<String> roles;

    /**
     * 用户部门
     */
    private List<String> depts;
}