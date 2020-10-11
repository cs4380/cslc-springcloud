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
import com.cs.springcloud.admin.api.model.BaseUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BaseUser 基础用户.
 *
 * @author cs
 * @version 1.0
 * @date 2019-09-30 15:23:58
 */
public interface BaseUserMapper extends BaseMapper<BaseUser> {
    /**
     * 通过部门编码获取用户列表
     *
     * @param deptCode 部门编码
     * @return 指定部门的用户列表
     */
    List<BaseUser> selectDeptUsersByDeptCode(@Param("deptCode") String deptCode);

    /**
     * 排除指定部门的用户列表
     *
     * @param excludeDeptCode 排除指定部门
     * @return 获取所有部门用户列表
     */
    List<BaseUser> selectUsersExcludeDept(@Param("excludeDeptCode") String excludeDeptCode);
}