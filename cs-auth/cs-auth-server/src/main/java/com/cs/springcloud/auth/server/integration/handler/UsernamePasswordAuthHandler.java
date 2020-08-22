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
import com.cs.springcloud.auth.server.feign.AdminFeign;
import com.cs.springcloud.auth.server.integration.LoginAuthParamDTO;
import com.cs.springcloud.base.core.result.ObjectResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * UsernamePasswordAuthHandler 密码模式登陆处理器.
 *
 * @author cs
 * @version 1.0
 * @date 2020-06-03 20:49
 */
@Component
@Primary
public class UsernamePasswordAuthHandler extends AbstractLoginAuthHandler {

    @Autowired
    private AdminFeign adminFeign;

    @Override
    public UserInfoDTO authenticate(LoginAuthParamDTO loginAuthParam) {
        ObjectResult<UserInfoDTO> userInfoResult = adminFeign.getUserInfoByUsername(loginAuthParam.getUsername());
        return userInfoResult.getData();
    }

    @Override
    public void prepare(LoginAuthParamDTO loginAuthParam) {

    }

    @Override
    public boolean support(LoginAuthParamDTO loginAuthParam) {
        // 默认登陆方式，所以不需要填写登陆类型
        return StringUtils.isEmpty(loginAuthParam.getLoginType());
    }
}