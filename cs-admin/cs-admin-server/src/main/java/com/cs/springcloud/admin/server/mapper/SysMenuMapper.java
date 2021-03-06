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

package com.cs.springcloud.admin.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.springcloud.admin.api.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SysMenu 系统基础菜单表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-11 19:51
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 通过菜单ids，获取菜单列表
     *
     * @param menuIds 菜单ids
     * @return
     */
    List<SysMenu> selectMenuListByMenuIds(@Param("menuIds") List<String> menuIds);
}