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

package com.cs.springcloud.base.core.util;


import com.cs.springcloud.base.common.util.ReflectionUtils;
import com.cs.springcloud.base.core.context.BaseContextHandler;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * EntityUtils 实体工具类.
 * <p>
 * 表中都存在
 * 记录字段：createTime,createUserId,createUserName
 * 更新字段：updateTime,updateUserId,updateUserName
 * 动态快速注入该字段
 * </p>
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-02 11:34
 */
public class EntityUtils {
    /**
     * 创建人记录字段
     */
    private static final String[] CRE_USER_INFO = {"createTime", "createUserId", "createUserName", "tenantId"};

    /**
     * 更新人记录字段
     */
    private static final String[] UP_USER_INFO = {"updateTime", "updateUserId", "updateUserName", "tenantId"};

    /**
     * 添加操作人信息（操作记录）
     * <p>
     * 设置创建人记录和更新人记录
     * </p>
     *
     * @param entity 实体（表）信息
     */
    public static <T> void setCreatAndUpdatInfo(T entity) {
        setCreateInfo(entity);
        setUpdatedInfo(entity);
    }

    /**
     * 设置创建人记录
     *
     * @param entity 实体（表）信息
     */
    public static <T> void setCreateInfo(T entity) {
        setOperate(entity, CRE_USER_INFO);
    }

    /**
     * 设置更新人记录
     *
     * @param entity 实体（表）信息
     */
    public static <T> void setUpdatedInfo(T entity) {
        setOperate(entity, UP_USER_INFO);
    }

    /**
     * 设置操作人操作记录
     *
     * @param entity 实体（表）信息
     * @param fields 更新字段集（更新人、创建人记录字段）
     * @param <T>
     */
    public static <T> void setOperate(T entity, String[] fields) {
        String name = "admin";//BaseContextHandler.getName();
        String userId = "1"; //BaseContextHandler.getUserId();
        String tenantId = BaseContextHandler.getTenantId();

        // 判断实体中是否存在updateTime或者createTime字段
        Field field = ReflectionUtils.getAccessibleField(entity, fields[0]);
        Object[] value = null;
        if (field != null && field.getType().equals(LocalDateTime.class)) {
            // value和fields字段顺序必须一致
            value = new Object[]{LocalDateTime.now(), userId, name, tenantId};
        }
        // 设置记录信息
        setDefaultValues(entity, fields, value);
    }

    /**
     * 依据实体的属性数组和值数组对对象的属性进行赋值
     *
     * @param entity 实体（表）信息
     * @param fields 属性数组
     * @param value  值数组
     */
    private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            if (ReflectionUtils.hasField(entity, field)) {
                ReflectionUtils.invokeSetter(entity, field, value[i]);
            }
        }
    }

    /**
     * 根据主键属性，判断主键是否值为空
     *
     * @param entity 实体（表）信息
     * @param field  字段
     * @return 主键为空，则返回false；主键有值，返回true
     */
    public static <T> boolean isPKNotNull(T entity, String field) {
        if (!ReflectionUtils.hasField(entity, field)) {
            return false;
        }
        Object value = ReflectionUtils.getFieldValue(entity, field);
        return value != null && !"".equals(value);
    }
}