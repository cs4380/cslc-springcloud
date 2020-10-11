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

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cs.springcloud.base.core.constant.JwtUserConstant;
import com.cs.springcloud.base.core.model.JWTUserBean;

import java.util.List;

/**
 * JWTUtil JWT工具类.
 * <p>
 * jwt操作工具类：生成签名、验证token
 * </p>
 *
 * @author cs
 * @version 1.0
 * @date 2019-05-06 09:27
 */
public class JWTUtil {
    /**
     * 获得token中的信息
     *
     * @return token中包含的UserBean信息
     */
    public static JWTUserBean getUserBeanByToken(String token) {
        JWTUserBean userBean = new JWTUserBean();
        try {
            DecodedJWT jwt = JWT.decode(token);
            userBean.setUsername(jwt.getClaim(JwtUserConstant.JWT_KEY_USER_NAME).asString());
            userBean.setName(jwt.getClaim(JwtUserConstant.JWT_KEY_NAME).asString());
            userBean.setUserId(jwt.getClaim(JwtUserConstant.JWT_KEY_USER_ID).asString());
            userBean.setTenantId(jwt.getClaim(JwtUserConstant.JWT_KEY_TENANT_ID).asString());

            List<String> roles = jwt.getClaim(JwtUserConstant.JWT_KEY_USER_ROLES).asList(String.class);
            userBean.setRoles(roles);

            List<String> depts = jwt.getClaim(JwtUserConstant.JWT_KEY_DEPARTS).asList(String.class);
            userBean.setDepts(depts);

            userBean.setIsSuperAdmin(jwt.getClaim(JwtUserConstant.JWT_KEY_USER_IS_ADMIN).asInt());
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return userBean;
    }

    /**
     * 获得token中的信息
     *
     * @param token 认证信息
     * @param key   token中属性key
     * @return token中数据value
     */
    public static String getJwtValue(String token, String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息集
     *
     * @param token 认证信息
     * @param key   token中属性key
     * @return token中数据values
     */
    public static List<String> getJwtValues(String token, String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asList(String.class);
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}