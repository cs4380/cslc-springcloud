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

package com.cs.springcloud.auth.server.feign;

import com.cs.springcloud.admin.api.dto.UserInfoDTO;
import com.cs.springcloud.base.core.result.ObjectResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * AdminFeign admin服务feign接口 .
 *
 * @author cs
 * @version 1.0
 * @date 2019-12-15 20:10
 */
@FeignClient(name = "cs-admin")
public interface AdminFeign {
    /**
     * 获取用户信息：用户基本信息、角色集
     */
    @GetMapping("/baseUsers/info/{username}")
    ObjectResult<UserInfoDTO> getUserInfoByUsername(@PathVariable("username") String username);

}
