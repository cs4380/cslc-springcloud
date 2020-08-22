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

package com.cs.springcloud.auth.server.integration;

import com.cs.springcloud.auth.server.integration.handler.LoginAuthHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * LoginAuthenticationFilter 自定义登陆拦截器.
 * <p>
 * 1、根据参数获取当前的是认证类型
 * 2、根据不同的认证类型调用不同的LoginAuthHandler.prepar进行处理
 * <p>
 * 自定义实现密码、短信、第三方认证
 *
 * @author cs
 * @version 1.0
 * @date 2020-06-03 20:10
 */
@Component
public class LoginAuthenticationFilter extends GenericFilterBean implements ApplicationContextAware {

    private static final String AUTH_TYPE_PARM_NAME = "login_type";

    private static final String OAUTH_TOKEN_URL = "/oauth/token";

    private Collection<LoginAuthHandler> loginAuthHandlers;

    private ApplicationContext applicationContext;

    private RequestMatcher requestMatcher;

    /**
     * 构造拦截地址和请求方式
     */
    public LoginAuthenticationFilter() {
        this.requestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "GET"),
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "POST")
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 拦截登陆请求
        if (requestMatcher.matches(request)) {
            // 根据参数获取当前的是认证类型
            LoginAuthParamDTO loginAuthParam = new LoginAuthParamDTO();
            loginAuthParam.setLoginType(request.getParameter(AUTH_TYPE_PARM_NAME));
            loginAuthParam.setAuthParameters(request.getParameterMap());
            LoginAuthenticationContext.set(loginAuthParam);

            try {
                // 前置处理
                this.prepare(loginAuthParam);

                filterChain.doFilter(request, response);

                // 后置处理
                this.complete(loginAuthParam);
            } finally {
                LoginAuthenticationContext.clear();
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 进行预处理
     *
     * @param loginAuthParam 前端请求参数
     */
    private void prepare(LoginAuthParamDTO loginAuthParam) {

        // 延迟加载认证器
        if (this.loginAuthHandlers == null) {
            synchronized (this) {
                // 获取所有自定义登陆处理器
                Map<String, LoginAuthHandler> integrationAuthenticatorMap = applicationContext.getBeansOfType(LoginAuthHandler.class);
                if (integrationAuthenticatorMap != null) {
                    this.loginAuthHandlers = integrationAuthenticatorMap.values();
                }
            }
        }

        if (this.loginAuthHandlers == null) {
            this.loginAuthHandlers = new ArrayList<>();
        }

        for (LoginAuthHandler loginAuthHandler : loginAuthHandlers) {
            // 判断当前登陆认证类型是否匹配
            if (loginAuthHandler.support(loginAuthParam)) {
                // 不同登陆方式，执行各自的验证
                loginAuthHandler.prepare(loginAuthParam);
            }
        }
    }

    /**
     * 认证结束后执行
     */
    private void complete(LoginAuthParamDTO loginAuthParam) {
        for (LoginAuthHandler loginAuthHandler : loginAuthHandlers) {
            // 判断当前登陆认证类型是否匹配
            if (loginAuthHandler.support(loginAuthParam)) {
                loginAuthHandler.complete(loginAuthParam);
            }
        }
    }

    /**
     * 获取bean管理类
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}