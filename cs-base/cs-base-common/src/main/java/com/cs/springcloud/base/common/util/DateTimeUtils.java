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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeUtils 日期工具类.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-02 23:23
 */
public class DateTimeUtils {
    /**
     * 模板样式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 模板样式：yyyy-MM-dd HH:mm
     */
    public static final String DATETIME_YMDHM_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 模板样式：yyyy-MM-dd
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 模板样式：HH:mm:ss
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 日期转为字符串
     * <p>
     * 默认格式：yyyy-MM-dd HH:mm:ss
     * </p>
     *
     * @param date 日期
     * @return
     */
    public static String format(LocalDateTime date) {
        return format(date, DATETIME_FORMAT);
    }

    /**
     * 日期转为字符串，安装模板样式转换
     *
     * @param date    日期
     * @param pattern 模板
     * @return
     */
    public static String format(LocalDateTime date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    /**
     * 分转毫秒
     *
     * @param minutes 分钟
     * @return 毫秒
     */
    public static long minutesToMilliseconds(int minutes) {
        return minutes * 60 * 1000;
    }
}