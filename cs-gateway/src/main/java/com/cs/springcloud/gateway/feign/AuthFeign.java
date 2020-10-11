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

package com.cs.springcloud.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AdminFeign admin服务feign接口 .
 *
 * @author cs
 * @version 1.0
 * @date 2019-12-15 20:10
 */
@FeignClient(name = "cs-auth")
public interface AuthFeign {
    /**
     * token缓存认证，匹配key是否存在
     *
     * @param token 用户登陆token
     * @return ture存在，false不存在
     */
    @GetMapping("/token/verify")
    Boolean verifyToken(@RequestParam("token") String token);
}
