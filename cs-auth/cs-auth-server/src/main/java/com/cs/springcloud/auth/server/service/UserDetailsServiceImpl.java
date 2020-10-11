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

package com.cs.springcloud.auth.server.service;

import com.cs.springcloud.admin.api.dto.UserInfoDTO;
import com.cs.springcloud.admin.api.model.BaseUser;
import com.cs.springcloud.auth.server.bean.JwtTokenUser;
import com.cs.springcloud.auth.server.enums.OAuthRoleEnums;
import com.cs.springcloud.auth.server.integration.LoginAuthParamDTO;
import com.cs.springcloud.auth.server.integration.LoginAuthenticationContext;
import com.cs.springcloud.auth.server.integration.handler.LoginAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetailsService 获取用户信息.
 * <p>
 * 在loadUserByUsername方法中会调用authenticate方法，
 * 在authenticate方法中会当前上下文种的认证类型调用不同的IntegrationAuthenticator 来获取用户信息
 *
 * @author cs
 * @version 1.0
 * @date 2020-01-21 13:34
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired(required = false)
    private List<LoginAuthHandler> loginAuthHandlers;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginAuthParamDTO loginAuthParam = LoginAuthenticationContext.get();

        if (loginAuthParam == null) {
            loginAuthParam = new LoginAuthParamDTO();
        }
        loginAuthParam.setUsername(username);

        UserInfoDTO userInfoDTO = this.authenticate(loginAuthParam);
        if (userInfoDTO == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        BaseUser baseUser = userInfoDTO.getBaseUser();
        if (baseUser == null) {
            throw new UsernameNotFoundException("未查询到用户名");
        }


        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(OAuthRoleEnums.USER.getRole());
        JwtTokenUser jwtUser = new JwtTokenUser(username, baseUser.getPassword(), authorities);
        jwtUser.setName(baseUser.getUsername());
        jwtUser.setUserId(baseUser.getId());
        jwtUser.setIsSuperAdmin(baseUser.getIsSuperAdmin());
        jwtUser.setTenantId(baseUser.getTenantId());
        jwtUser.setDepts(new ArrayList<>());
        jwtUser.setRoles(userInfoDTO.getRoles());

        return jwtUser;
    }

    /**
     * 通过前端请求参数解析，并获取用户信息
     *
     * @param loginAuthParam 前端请求参数
     * @return 用户信息
     */
    private UserInfoDTO authenticate(LoginAuthParamDTO loginAuthParam) {
        if (this.loginAuthHandlers != null) {
            for (LoginAuthHandler loginAuthHandler : loginAuthHandlers) {
                // 判断当前登陆认证类型是否匹配
                if (loginAuthHandler.support(loginAuthParam)) {
                    // 通过各自登陆方式获取用户信息
                    return loginAuthHandler.authenticate(loginAuthParam);
                }
            }
        }
        return null;
    }
}
