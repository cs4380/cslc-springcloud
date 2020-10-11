package com.cs.springcloud.admin.server.config;

import com.cs.springcloud.auth.client.interceptor.AuthTokenRestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig 自定义配置mvc拦截器.
 *
 * @author cs
 * @version 1.0
 * @date 2020-05-25 10:53
 * @description
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 自定义配置mvc拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthTokenRestInterceptor()).addPathPatterns("/**").excludePathPatterns("/error");
    }
}