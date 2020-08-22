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
 * LoginAuthHandler 登陆认证处理器.
 *
 * @author cs
 * @version 1.0
 * @date 2020-06-03 20:49
 */
public interface LoginAuthHandler {

    /**
     * 通过各自登陆方式获取用户信息
     *
     * @param loginAuthParam 前端请求参数
     * @return 认证成功后返回的用户信息
     */
    UserInfoDTO authenticate(LoginAuthParamDTO loginAuthParam);

    /**
     * 不同登陆方式，执行各自的验证
     *
     * @param loginAuthParam 前端请求参数
     */
    void prepare(LoginAuthParamDTO loginAuthParam);

    /**
     * 判断是否支持登陆认证类型
     *
     * @param loginAuthParam 前端请求参数
     * @return ture:支持当前登陆认证方式，false：不支持当前登陆认证方式
     */
    boolean support(LoginAuthParamDTO loginAuthParam);

    /**
     * 认证结束后执行
     *
     * @param loginAuthParam 前端请求参数
     */
    void complete(LoginAuthParamDTO loginAuthParam);
}
