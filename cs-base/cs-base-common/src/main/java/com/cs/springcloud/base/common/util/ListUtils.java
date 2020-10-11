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

import java.util.ArrayList;
import java.util.List;

/**
 * ListUtils list集合工具类.
 *
 * @author cs
 * @version 1.0
 * @date 2019-12-05 11:08
 */
public class ListUtils {
    /**
     * object转换为ArrayList
     *
     * @param obj   object对象
     * @param clazz 集合中的对象
     * @return 集合对象, 如何obj为null则返回空集合
     */
    public static <T> List<T> castArrayList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (obj == null) {
            return result;
        }
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
        }
        return result;
    }
}