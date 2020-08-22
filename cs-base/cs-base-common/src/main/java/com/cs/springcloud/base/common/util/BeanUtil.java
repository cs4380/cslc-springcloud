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

package com.cs.springcloud.base.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * BeanUtil bean实体操作类.
 *
 * @author cs
 * @version 1.0
 * @date 2018年11月29日
 */
public class BeanUtil {

    /**
     * 判断obj对象是否为空
     * <p>
     * = Object：null返回true<br/>
     * = Collection集合：null或空集合返回true<br/>
     * = Map集合：null或空集合返回true<br/>
     * = 数组: null或空数组返回true<br/>
     * </p>
     *
     * @param obj 待判断的对象
     * @return true: 为空，并且有没有值.false: 不为空，并且有值.
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }
        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
        }
        if (obj instanceof Optional) {
            return !((Optional) obj).isPresent();
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        return false;
    }

    /**
     * 判断obj对象是否为不为空
     * <p>
     * = Object：null返回false<br/>
     * = Collection集合：null或空集合返回false<br/>
     * = Map集合：null或空集合返回false<br/>
     * = 数组: null或空数组返回false<br/>
     * </p>
     *
     * @param obj 待判断的对象
     * @return true: 不为空，并且有值.false: 为空，并且有没有值.
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断bean是否存在指定字段
     *
     * @param clz       bean
     * @param fieldName 字段名
     * @return ture: 存在, false: 不存在
     */
    public boolean isExistField(Class clz, String fieldName) {
        boolean isExist = false;
        if (clz == null) {
            return isExist;
        }
        try {
            Field field = clz.getDeclaredField(fieldName);
            if (field != null) {
                isExist = true;
            }
        } catch (Exception e) {
            return isExist;
        }
        return isExist;
    }

    /**
     * 去掉bean中所有属性为字符串的前后空格
     *
     * @param bean 实体类
     */
    public static void beanAttributeValueTrim(Object bean) {
        if (bean != null) {
            // 获取所有的字段包括public,private,protected,private
            Field[] fields = bean.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                if ("java.lang.String".equals(f.getType().getName())) {
                    // 获取字段名
                    String key = f.getName();
                    Object value = getFieldValue(bean, key);
                    if (value == null) {
                        continue;
                    }
                    setFieldValue(bean, key, value.toString().trim());
                }
            }
        }
    }

    /**
     * 利用反射通过get方法获取bean中字段fieldName的值
     *
     * @param bean
     * @param fieldName
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static Object getFieldValue(Object bean, String fieldName) {
        StringBuffer result = new StringBuffer();
        String methodName = result.append("get").append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();
        Object rObject = null;
        Method method = null;

        Class[] classArr = new Class[0];
        try {
            method = bean.getClass().getMethod(methodName, classArr);
            rObject = method.invoke(bean, new Object[0]);
        } catch (Exception e) {
        }
        return rObject;
    }

    /**
     * 利用发射调用bean.set方法将value设置到字段
     *
     * @param bean
     * @param fieldName
     * @param value
     */
    @SuppressWarnings("rawtypes")
    private static void setFieldValue(Object bean, String fieldName, Object value) {
        StringBuffer result = new StringBuffer();
        String methodName = result.append("set").append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();

        /**
         * 利用发射调用bean.set方法将value设置到字段
         */
        Class[] classArr = new Class[1];
        classArr[0] = "java.lang.String".getClass();
        try {
            Method method = bean.getClass().getMethod(methodName, classArr);
            method.invoke(bean, value);
        } catch (Exception e) {
        }
    }

    /**
     * 获取对象的字符串，没有则返回null
     *
     * @param obj 对象
     * @return
     */
    public static String getObjectValue(Object obj) {
        return obj == null ? null : obj.toString();
    }

    /**
     * 获取对象的字符串，没有则返回空字符串
     *
     * @param obj 对象
     * @return
     */
    public static String getObjectValueToString(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}