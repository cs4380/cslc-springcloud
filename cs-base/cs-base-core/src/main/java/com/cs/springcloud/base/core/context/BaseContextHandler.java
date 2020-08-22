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

package com.cs.springcloud.base.core.context;

import com.cs.springcloud.base.common.constant.InitialCapacityConstant;
import com.cs.springcloud.base.common.util.BeanUtil;
import com.cs.springcloud.base.common.util.ListUtils;
import com.cs.springcloud.base.core.constant.JwtUserConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseContextHandler 当前上下文的用户（token）信息.
 *
 * @author cs
 * @version 1.0
 * @date 2019-09-28 21:21
 */
public class BaseContextHandler {

    /**
     * 缓存当前操作用户信息
     */
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    /**
     * 设置当前操作用户信息属性名和属性值
     *
     * @param key   用户的属性名
     * @param value 用户的信息值
     */
    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(InitialCapacityConstant.INITIAL_20_NUMBER);
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    /**
     * 通过属性名获取当前操作用户信息
     *
     * @param key 用户的属性名
     * @return 用户的信息值
     */
    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(InitialCapacityConstant.INITIAL_20_NUMBER);
            threadLocal.set(map);
        }
        return map.get(key);
    }

    /**
     * 设置用户账号
     */
    public static void setUsername(String username) {
        set(JwtUserConstant.JWT_KEY_USER_NAME, username);
    }

    /**
     * 获取用户账号
     *
     * @return 用户账号
     */
    public static String getUsename() {
        Object value = get(JwtUserConstant.JWT_KEY_USER_NAME);
        return BeanUtil.getObjectValueToString(value);
    }

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    public static String getUserId() {
        Object value = get(JwtUserConstant.JWT_KEY_USER_ID);
        return BeanUtil.getObjectValue(value);
    }

    /**
     * 设置用户id
     */
    public static void setUserId(String userId) {
        set(JwtUserConstant.JWT_KEY_USER_ID, userId);
    }

    /**
     * 获取用户部门信息
     *
     * @return 用户部门编码集
     */
    public static List<String> getDeparts() {
        Object value = get(JwtUserConstant.JWT_KEY_DEPARTS);
        return ListUtils.castArrayList(value, String.class);
    }

    /**
     * 设置用户部门信息
     */
    public static void setDeparts(List<String> departs) {
        set(JwtUserConstant.JWT_KEY_DEPARTS, departs);
    }

    /**
     * 获取用户角色
     *
     * @return 用户角色编码集
     */
    public static List<String> getRoles() {
        Object value = get(JwtUserConstant.JWT_KEY_USER_ROLES);
        return ListUtils.castArrayList(value, String.class);
    }

    /**
     * 设置用户角色
     */
    public static void setRoles(List<String> roles) {
        set(JwtUserConstant.JWT_KEY_USER_ROLES, roles);
    }

    /**
     * 获取用户租户信息
     *
     * @return 租户id
     */
    public static String getTenantId() {
        Object value = get(JwtUserConstant.JWT_KEY_TENANT_ID);
        return BeanUtil.getObjectValue(value);
    }

    /**
     * 设置用户租户信息
     */
    public static void setTenantId(String tenant) {
        set(JwtUserConstant.JWT_KEY_TENANT_ID, tenant);
    }


    /**
     * 设置用户名字信息
     */
    public static void setName(String name) {
        set(JwtUserConstant.JWT_KEY_NAME, name);
    }

    /**
     * 获取用户名字信息
     *
     * @return 用户姓名
     */
    public static String getName() {
        Object value = get(JwtUserConstant.JWT_KEY_NAME);
        return BeanUtil.getObjectValueToString(value);
    }

    /**
     * 获取：是否超级管理员
     *
     * @return 1：是，0否
     */
    public static Integer getIsSuperAdmin() {
        Object value = get(JwtUserConstant.JWT_KEY_USER_IS_ADMIN);
        if (null == value) {
            return 0;
        }
        return Integer.valueOf(value.toString());
    }

    /**
     * 设置：是否超级管理员
     */
    public static void setIsSuperAdmin(Integer isSuperAdmin) {
        set(JwtUserConstant.JWT_KEY_USER_IS_ADMIN, isSuperAdmin);
    }


    /**
     * 删除token信息
     */
    public static void remove() {
        threadLocal.remove();
    }
}