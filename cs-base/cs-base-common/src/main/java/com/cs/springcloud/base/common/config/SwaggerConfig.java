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

package com.cs.springcloud.base.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig SwaggerConfig配置类.
 * <p>
 * 配置项目的扫描包路径、版本、说明等基本信息
 * </p>
 *
 * @author cs
 * @version 1.0
 * @date 2020-07-23 22:27
 */
@Configuration
@EnableKnife4j
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    /**
     * 扫描api包路径
     */
    @Value("${swagger.config.basePackage}")
    private String basePackage;

    /**
     * title
     */
    @Value("${swagger.config.title:api管理}")
    private String title;

    /**
     * 服务地址配置
     */
    @Value("${swagger.config.serviceUrl:localhost}")
    private String serviceUrl;

    /**
     * 版本
     */
    @Value("${swagger.config.version:1.0}")
    private String version;

    /**
     * 说明
     */
    @Value("${swagger.config.description:description}")
    private String description;

    /**
     * 联系人：姓名
     */
    @Value("${swagger.config.contact.name:cs}")
    private String contactName;

    /**
     * 配置分组摘要
     */
    @Bean(value = "userApi")
    @Order(value = 1)
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置基本信息
     */
    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(serviceUrl)
                .version(version)
                .contact(new Contact(contactName, null, null))
                .build();
    }
}