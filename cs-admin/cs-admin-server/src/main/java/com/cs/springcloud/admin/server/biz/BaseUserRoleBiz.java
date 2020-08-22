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

package com.cs.springcloud.admin.server.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cs.springcloud.admin.api.model.BaseUserRole;
import com.cs.springcloud.admin.server.mapper.BaseUserRoleMapper;
import com.cs.springcloud.base.core.biz.BaseBusinessBiz;
import com.cs.springcloud.base.common.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BaseUserRole 用户角色关系表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-14 13:50
 */
@Service
public class BaseUserRoleBiz extends BaseBusinessBiz<BaseUserRoleMapper, BaseUserRole> {

    /**
     * 通过用户id，获取用户角色列表
     *
     * @param userId 用户id
     * @return 用户的角色集 List<role>
     */
    public List<String> getRolesByUserId(String userId) {
        List<BaseUserRole> userRoleList = this.getUserRoleByUserId(userId);
        if (BeanUtil.isEmpty(userRoleList)) {
            return new ArrayList<>();
        }
        return userRoleList.stream().map(BaseUserRole::getRoleCode).collect(Collectors.toList());
    }

    /**
     * 通过用户id，获取用户角色关系
     *
     * @param userId 用户id
     * @return 用户角色关系集
     */
    public List<BaseUserRole> getUserRoleByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return new ArrayList<>();
        }
        BaseUserRole baseUserRole = new BaseUserRole();
        baseUserRole.setUserId(userId);
        return super.selectModelList(baseUserRole);
    }

    /**
     * 删除指定用户的角色关系
     *
     * @param userId 用户主键
     * @return 删除总行数
     */
    public int delUserRoleByRoleCodes(String userId) {
        return this.delUserRoleByRoleCodes(userId, null);
    }

    /**
     * 通过用户主键或者用户编码集合删除
     *
     * @param userId    用户主键
     * @param roleCodes 角色编码集
     * @return 删除总行数
     */
    public int delUserRoleByRoleCodes(String userId, List<String> roleCodes) {
        QueryWrapper<BaseUserRole> queryWrapper = new QueryWrapper<>();
        // 判断是否存在角色编码
        if (BeanUtil.isEmpty(roleCodes)) {
            queryWrapper.in("role_code", roleCodes);
        }
        // 判断是否指定用户
        if (StringUtils.isNotBlank(userId)) {
            queryWrapper.eq("user_id", userId);
        }
        return this.baseMapper.delete(queryWrapper);
    }

    /**
     * 添加用户角色关系
     *
     * @param userId    用户主键
     * @param roleCodes 角色编码集
     */
    public void addUserRole(String userId, List<String> roleCodes) {
        if (BeanUtil.isEmpty(roleCodes)) {
            return;
        }
        for (String role : roleCodes) {
            BaseUserRole userRole = new BaseUserRole();
            userRole.setRoleCode(role);
            userRole.setUserId(userId);
            this.baseMapper.insert(userRole);
        }
    }

}