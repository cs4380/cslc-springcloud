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
import com.cs.springcloud.admin.api.dto.MenuButtonCodeDTO;
import com.cs.springcloud.admin.api.model.SysMenuButton;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SysMenuButton 系统基础菜单按钮表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-12 10:22
 */
public interface SysMenuButtonMapper extends BaseMapper<SysMenuButton> {

    /**
     * 通过主键id获取菜单按钮关系编码
     *
     * @param menuButtonIds 主键ids
     * @return
     */
    List<MenuButtonCodeDTO> selectMenuCodeAndButCodeByButIds(@Param("menuButtonIds") List<Integer> menuButtonIds);
}