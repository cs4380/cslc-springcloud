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
import com.cs.springcloud.auth.server.integration.LoginAuthParamDTO;

/**
 * AbstractLoginAuthHandler 抽象登陆认证处理器.
 *
 * @author cs
 * @version 1.0
 * @date 2020-06-03 20:49
 */
public abstract class AbstractLoginAuthHandler implements LoginAuthHandler {

    @Override
    public abstract UserInfoDTO authenticate(LoginAuthParamDTO loginAuthParam);

    @Override
    public abstract void prepare(LoginAuthParamDTO loginAuthParam);

    @Override
    public abstract boolean support(LoginAuthParamDTO loginAuthParam);

    @Override
    public void complete(LoginAuthParamDTO loginAuthParam) {

    }
}