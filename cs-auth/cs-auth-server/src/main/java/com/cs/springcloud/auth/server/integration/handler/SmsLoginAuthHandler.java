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

package com.cs.springcloud.auth.server.integration.handler;

import com.cs.springcloud.admin.api.dto.UserInfoDTO;
import com.cs.springcloud.admin.api.model.BaseUser;
import com.cs.springcloud.auth.server.integration.LoginAuthParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * SmsLoginAuthHandler 短信模式登陆处理器.
 * <p>
 * 通过短信验证码和手机认证
 *
 * @author cs
 * @version 1.0
 * @date 2020-06-03 20:49
 */
@Component
public class SmsLoginAuthHandler extends AbstractLoginAuthHandler implements ApplicationEventPublisherAware {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ApplicationEventPublisher applicationEventPublisher;

    private final static String SMS_AUTH_TYPE = "sms";

    @Override
    public UserInfoDTO authenticate(LoginAuthParamDTO loginAuthParam) {
        UserInfoDTO userInfoResult = new UserInfoDTO();
        BaseUser baseUser = new BaseUser();
        baseUser.setUsername(loginAuthParam.getUsername());
        baseUser.setPassword(passwordEncoder.encode("22213"));
        userInfoResult.setBaseUser(baseUser);
        return userInfoResult;
    }

    @Override
    public void prepare(LoginAuthParamDTO loginAuthParam) {
        // 判断验证码是否过期
        // 判断验证码是否正确错误
        String smsToken = loginAuthParam.getAuthParameter("sms_token");
        String smsCode = loginAuthParam.getAuthParameter("password");
        String username = loginAuthParam.getAuthParameter("username");
        //   throw new OAuth2Exception("验证码错误或已过期");
    }

    @Override
    public boolean support(LoginAuthParamDTO loginAuthParam) {
        return SMS_AUTH_TYPE.equals(loginAuthParam.getLoginType());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}