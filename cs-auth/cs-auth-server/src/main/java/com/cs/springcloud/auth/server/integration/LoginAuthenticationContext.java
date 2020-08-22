package com.cs.springcloud.auth.server.integration;


/**
 * LoginAuthenticationContext 登陆请求参数缓存 .
 *
 * @author cs
 * @version 1.0
 * @date 2020-06-03 20:10
 */
public class LoginAuthenticationContext {

    private static ThreadLocal<LoginAuthParamDTO> holder = new ThreadLocal<>();

    public static void set(LoginAuthParamDTO integrationAuthentication) {
        holder.set(integrationAuthentication);
    }

    public static LoginAuthParamDTO get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}