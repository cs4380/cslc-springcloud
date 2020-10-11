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

import com.cs.springcloud.admin.server.biz.SysRoleBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ApplicationStartInitListener 应用启动时初始化.
 *
 * @author cs
 * @version 1.0
 * @date 2020-10-06
 */
@Component
@Slf4j
public class ApplicationStartInitListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private SysRoleBiz roleBiz;

    /**
     * 初始启动quartz
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            log.info("初始缓存数据开始");
            roleBiz.refreshRoleCache();
            log.info("初始缓存数据结束");
        } catch (Exception e) {
            log.info("初始缓存数据异常: {}", e.getMessage());
        }
    }

}
