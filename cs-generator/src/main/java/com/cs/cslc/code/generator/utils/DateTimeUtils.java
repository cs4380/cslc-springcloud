/*
 *MIT License
 *
 *Copyright (c) 2019 chenshuai cs4380@163.com
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in all
 *copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *SOFTWARE.
 */

package com.cs.cslc.code.generator.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeUtils 日期工具类.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-02 23:23
 * @description
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