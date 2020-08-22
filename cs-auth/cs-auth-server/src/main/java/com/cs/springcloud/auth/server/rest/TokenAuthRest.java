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

package com.cs.springcloud.auth.server.rest;

import com.cs.springcloud.base.common.constant.RedisKeysConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TokenAuthRest token验证接口 .
 *
 * @author cs
 * @version 1.0
 * @date 2020-06-03 21:10
 */
@RestController
@RequestMapping("/token")
public class TokenAuthRest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * token缓存认证，匹配key是否存在
     *
     * @param token 用户登陆token
     * @return ture存在，false不存在
     */
    @GetMapping("/verify")
    public Boolean verifyToken(@RequestParam("token") String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        return stringRedisTemplate.hasKey(RedisKeysConstant.USER_TOKENS_ACCESS + token);
    }
}
