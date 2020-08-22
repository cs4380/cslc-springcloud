package com.cs.springcloud.auth.server.integration;

import lombok.Data;

import java.util.Map;

/**
 * LoginAuthParamDTO 登陆认证请求参数.
 *
 * @author cs
 * @version 1.0
 * @date 2020-1-21 14:49
 */
@Data
public class LoginAuthParamDTO {
    /**
     * 请求方式：短信登陆（sms）、微信（wechat）、qq、默认（密码）
     */
    private String LoginType;
    /**
     * 请求账号
     */
    private String username;
    /**
     * 前端请求登陆参数
     */
    private Map<String, String[]> authParameters;

    /**
     * 获取前端请求参数
     *
     * @param paramter 请求的name
     * @return 请求的vlaue
     */
    public String getAuthParameter(String paramter) {
        String[] values = this.authParameters.get(paramter);
        if (values != null && values.length > 0) {
            return values[0];
        }
        return null;
    }
}