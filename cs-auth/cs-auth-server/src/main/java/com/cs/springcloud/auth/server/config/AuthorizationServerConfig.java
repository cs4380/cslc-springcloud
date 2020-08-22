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

package com.cs.springcloud.auth.server.config;

import com.cs.springcloud.auth.server.integration.LoginAuthenticationFilter;
import com.cs.springcloud.base.common.constant.RedisKeysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * AuthorizationServerConfigurer 授权服务器配置类.
 * 验证clients相关信息
 *
 * @author cs
 * @version 1.0
 * @date 2020-1-21 14:49
 */
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    public UserDetailsService userDetailsService;

    @Autowired
    private LoginAuthenticationFilter loginfilter;

    /**
     * jwt自定义封装属性
     */
    @Resource(name = "accessTokenConverter")
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Bean
    public TokenStore redisTokenStore() {
        // 使用redis存储token
        RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
        // 设置redis token存储中的前缀
        redisTokenStore.setPrefix(RedisKeysConstant.USER_TOKENS);
        return redisTokenStore;
    }

    /**
     * 创建一个默认的资源服务token
     */
    @Bean
    public ResourceServerTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter);
        defaultTokenServices.setTokenStore(redisTokenStore());
        return defaultTokenServices;
    }

    /**
     * 配置授权服务器端点，如令牌存储，令牌自定义，用户批准和授权类型，不包括端点安全配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 配置认证管理器
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter)
                // 配置token存储的服务与位置
                .tokenStore(redisTokenStore())
                // 配置用户服务
                .userDetailsService(userDetailsService);
    }


    /**
     * 授权服务安全配置，主要用于放行客户端访问授权服务接口
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        // 允许客户端表单提交
        oauthServer.allowFormAuthenticationForClients()
                // 客户端校验token访问许可
                .checkTokenAccess("permitAll()")
                // 客户端token调用许可
                .tokenKeyAccess("permitAll()")
                // 将登陆拦截器放入到认证链条中。
                .addTokenEndpointAuthenticationFilter(loginfilter);
    }


    /**
     * 获取数据库中的Client信息
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 配置客户端详情
     * 配置 ClientDetailsService 也就是客户端属性信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }

    /**
     * filter将手动注册到SpringSecurity。但SpringBoot会自动注册任何一个bean组件，
     * 这样导致注册两次从而每次调用都会 触发两次，故通过此来让SpringBoot别自动注册此filter
     *
     * @param loginAuthenticationFilter 自定义认证过去器
     * @return
     */
    @Bean
    public FilterRegistrationBean registration(LoginAuthenticationFilter loginAuthenticationFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(loginAuthenticationFilter);
        registration.setEnabled(false);
        return registration;
    }

    public static void main(String[] args) {
        // 客户端密码
        System.out.println(new BCryptPasswordEncoder().encode("app"));
    }
}