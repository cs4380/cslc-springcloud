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

package com.cs.springcloud.auth.server;

import com.cs.springcloud.base.common.annotation.EnableSwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * AuthApplication 授权服务器-认证授权服务.
 *
 * @author cs
 * @version 1.0
 * @date 2019-12-05 11:33
 */
@EnableSwaggerConfig
// 开启认证服务器
@EnableAuthorizationServer
@EnableDiscoveryClient
// 启用Feign
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.cs.springcloud.admin.api", "com.cs.springcloud.auth.server"})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}