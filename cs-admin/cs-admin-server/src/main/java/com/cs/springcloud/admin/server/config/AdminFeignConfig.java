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

package com.cs.springcloud.admin.server.config;

import com.cs.springcloud.base.common.config.FeignTokenConfig;
import org.springframework.context.annotation.Configuration;

/**
 * AdminFeignConfig admin服务的feign配置.
 *
 * @author cs
 * @version 1.0
 * @date 2020-05-24 14:14
 */
@Configuration
public class AdminFeignConfig extends FeignTokenConfig {

}
