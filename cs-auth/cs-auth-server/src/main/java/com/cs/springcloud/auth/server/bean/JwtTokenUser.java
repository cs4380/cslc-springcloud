/*
 *MIT License
 *
 *Copyright (c) 2019 chenshuai cs4380@163.com
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in all
 *copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *SOFTWARE.
 */

package com.cs.springcloud.auth.server.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * JWTUserBean jwt用户信息.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-02 18:22
 * @description
 */
@Getter
@Setter
public class JwtTokenUser extends User implements UserDetails {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 是否超级管理员
     */
    private Integer isSuperAdmin;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 用户角色
     */
    private List<String> roles;

    /**
     * 用户部门
     */
    private List<String> depts;

    public JwtTokenUser(String username, String password, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}