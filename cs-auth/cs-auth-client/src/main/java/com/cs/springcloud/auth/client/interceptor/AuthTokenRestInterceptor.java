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

package com.cs.springcloud.auth.client.interceptor;

import com.cs.springcloud.auth.client.annotation.CheckAuthToken;
import com.cs.springcloud.auth.client.annotation.IgnoreAuthToken;
import com.cs.springcloud.base.common.constant.HttpHeadersConstant;
import com.cs.springcloud.base.core.context.BaseContextHandler;
import com.cs.springcloud.base.core.model.JWTUserBean;
import com.cs.springcloud.base.core.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AuthTokenRestInterceptor 接口层token检查.
 *
 * @author cs
 * @version 1.0
 * @date 2020-5-25 14:49
 */
@Slf4j
public class AuthTokenRestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       String method = request.getMethod();
        // 跳过OPTIONS请求
        if (HttpMethod.OPTIONS.matches(method)) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod handlerMethod = null;
        if(handler instanceof HandlerMethod){
            // 获取Spring MVC 层类或者方法的注解
            handlerMethod = (HandlerMethod) handler;
        }
        if(null == handlerMethod){
            return super.preHandle(request, response, handler);
        }
        // 获取请求对应的类或者方法的CheckAuthToken注解
        Class<?> obj = handlerMethod.getBeanType();
        CheckAuthToken checkAuthToken = obj.getAnnotation(CheckAuthToken.class);
        if (checkAuthToken == null) {
            checkAuthToken = handlerMethod.getMethodAnnotation(CheckAuthToken.class);
        }

        // 获取请求方法对应的方法的IgnoreAuthToken注解
        IgnoreAuthToken ignoreUserToken = handlerMethod.getMethodAnnotation(IgnoreAuthToken.class);

        // 判断请求是否添加Token认证，添加验证token，并缓存到BaseContextHandler中，否则放行
        if (checkAuthToken == null || ignoreUserToken != null) {
            return super.preHandle(request, response, handler);
        } else {
            // 请求头中获取token
            String token = request.getHeader(HttpHeadersConstant.HEADERS_USER_AUTH_TOKEN);
            if (StringUtils.isNotBlank(token)) {
                token = token.replaceAll("Bearer ", "");
                try {
                    // 2.获取JWT的用户信息
                    JWTUserBean jwtUserBean = JWTUtil.getUserBeanByToken(token);
                    BaseContextHandler.setUsername(jwtUserBean.getUsername());
                    BaseContextHandler.setUserId(jwtUserBean.getUserId());
                    BaseContextHandler.setName(jwtUserBean.getName());
                    BaseContextHandler.setDeparts(jwtUserBean.getDepts());
                    BaseContextHandler.setRoles(jwtUserBean.getRoles());
                    BaseContextHandler.setIsSuperAdmin(jwtUserBean.getIsSuperAdmin());
                } catch (RuntimeException ex) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    log.error(ex.getMessage(), ex);
                    response.setContentType("UTF-8");
                    response.getOutputStream().println("token认证失败");
                    return false;
                }
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("UTF-8");
                response.getOutputStream().println("token  fall");
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}