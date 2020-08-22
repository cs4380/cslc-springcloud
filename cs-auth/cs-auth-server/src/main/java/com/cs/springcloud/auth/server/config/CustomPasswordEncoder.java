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

package com.cs.springcloud.auth.server.config;

import com.cs.springcloud.auth.server.integration.LoginAuthParamDTO;
import com.cs.springcloud.auth.server.integration.LoginAuthenticationContext;
import com.cs.springcloud.base.common.util.Sha256Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

/**
 * CustomPasswordEncoder 自定义密码验证.
 *
 * @author cs
 * @version 1.0
 * @date 2020-5-25 22:22
 */
public class CustomPasswordEncoder extends BCryptPasswordEncoder {

    /**
     * 自定义密码验证
     *
     * @param rawPassword     原始密码(base64编码)
     * @param encodedPassword 数据库密码
     * @return true：匹配
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        LoginAuthParamDTO loginAuthParam = LoginAuthenticationContext.get();

        // 只要不是密码方式，则采用默认验证方式
        if (StringUtils.isNotEmpty(loginAuthParam.getLoginType())) {
            return super.matches(rawPassword, encodedPassword);
        }

        if (encodedPassword != null && encodedPassword.length() != 0) {
            String password = new String(Base64.getDecoder().decode(rawPassword.toString()));
            password = Sha256Util.getSHA256(password);
            if (password.equals(encodedPassword)) {
                return true;
            }
        }
        return super.matches(rawPassword, encodedPassword);
    }
}