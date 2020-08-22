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

package com.cs.springcloud.admin.server;

import com.cs.springcloud.base.common.annotation.JacksonDateTimeSerializer;
import com.cs.springcloud.base.common.annotation.EnableSwaggerConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * AdminApplication 基础admin管理服务.
 *
 * @author cs
 * @version 1.0
 * @date 2019-09-22 11:33
 */
// 启动注册中心
@EnableDiscoveryClient
// 启用Feign
@EnableFeignClients
// 配置日期格式化
@JacksonDateTimeSerializer
// 开启Swagger
@EnableSwaggerConfig
@MapperScan("com.cs.springcloud.admin.server.mapper")
@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}