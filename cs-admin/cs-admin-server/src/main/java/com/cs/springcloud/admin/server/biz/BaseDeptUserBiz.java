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
import com.cs.springcloud.admin.api.model.BaseDeptUser;
import com.cs.springcloud.admin.server.mapper.BaseDeptUserMapper;
import com.cs.springcloud.base.core.biz.BaseBusinessBiz;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseDeptUser 基础角色部门关系表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-12-06 09:14
 */
@Service
public class BaseDeptUserBiz extends BaseBusinessBiz<BaseDeptUserMapper, BaseDeptUser> {

    @Override
    public boolean insertModel(BaseDeptUser entity) {
        return super.insertModel(entity);
    }

    /**
     * 删除部门用户关系
     *
     * @param deptUser 部门用户
     */
    public int delDeptUser(BaseDeptUser deptUser) {
        return this.deleteModel(deptUser);
    }

    /**
     * 查询用户部门编码集合
     *
     * @param userId 用户主键
     * @return 用户的部门关系
     */
    public List<BaseDeptUser> getDeptUserByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return new ArrayList<>();
        }
        QueryWrapper<BaseDeptUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        return super.list(queryWrapper);
    }

    /**
     * 查询用户部门编码集合
     *
     * @param userId 用户主键
     * @return 用户的部门编码集
     */
    public List<String> getDeptCodesByUserId(String userId) {
        List<String> codes = new ArrayList<>();
        List<BaseDeptUser> deptUserList = this.getDeptUserByUserId(userId);
        for (BaseDeptUser baseDeptUser : deptUserList) {
            codes.add(baseDeptUser.getDeptCode());
        }
        return codes;
    }
}