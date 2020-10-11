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
import com.cs.springcloud.admin.api.model.BaseDept;
import com.cs.springcloud.admin.api.model.BaseDeptUser;
import com.cs.springcloud.admin.api.vo.BaseDeptTreeVO;
import com.cs.springcloud.admin.server.mapper.BaseDeptMapper;
import com.cs.springcloud.base.core.biz.BaseBusinessBiz;
import com.cs.springcloud.base.common.constant.CommonConstant;
import com.cs.springcloud.base.common.constant.InitialCapacityConstant;
import com.cs.springcloud.base.common.exception.BusinessException;
import com.cs.springcloud.base.common.util.BeanUtil;
import com.cs.springcloud.base.common.util.TreeUtil;
import com.cs.springcloud.base.common.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseDept 基础部门信息表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-12-06 09:14
 */
@Service
public class BaseDeptBiz extends BaseBusinessBiz<BaseDeptMapper, BaseDept> {

    @Autowired
    private BaseDeptUserBiz deptUserBiz;

    @Override
    public boolean insertModel(BaseDept entity) {
        int count = this.getDeptByDeptCode(entity.getDeptCode());
        if (count > 0) {
            throw new BusinessException("部门编码已存在！");
        }
        entity.setId(UUIDUtil.randomUUID());
        if (StringUtils.isBlank(entity.getParentId())) {
            entity.setParentId(CommonConstant.ROOT);
        }
        return super.insertModel(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        if (null == id) {
            throw new BusinessException("请选择待删除的部门！");
        }
        String deptId = String.valueOf(id);
        int count = this.getSonCountByParentId(deptId);
        if (count > 0) {
            throw new BusinessException("请先删除部门的子部门！");
        }
        // TODO 判断是否存在用户和部门的关系是否删除，有关系不允许删除
        BaseDept baseDept = new BaseDept();
        baseDept.setId(deptId);
        baseDept.setIsDeleted(CommonConstant.IS_FALSE);
        return this.updateById(baseDept);
    }

    /**
     * 通过部门编码统计历史部门个数
     *
     * @param deptCode 部门编码
     * @return 历史个数
     */
    public int getDeptByDeptCode(String deptCode) {
        QueryWrapper<BaseDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_code", deptCode);
        return this.baseMapper.selectCount(queryWrapper);
    }

    /**
     * 通过部门编码统计历史部门个数
     *
     * @param parentId 父级主键id
     * @return 儿子个数
     */
    public int getSonCountByParentId(String parentId) {
        QueryWrapper<BaseDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        queryWrapper.eq("is_deleted", CommonConstant.IS_TRUE);
        return this.baseMapper.selectCount(queryWrapper);
    }

    /**
     * 获取部门树
     *
     * @return 部门树
     */
    public List<BaseDeptTreeVO> getBaseDeptTree() {
        String root = "root";
        String[] fields = {"id", "parent_id", "dept_code", "dept_name", "update_time", "create_time"};
        // 获取全部数据
        List<BaseDept> deptList = this.selectFieldListAll(fields);
        // 缓存树型结构数据
        List<BaseDeptTreeVO> tree = new ArrayList<>(InitialCapacityConstant.INITIAL_128_NUMBER);
        // 构造数据
        if (BeanUtil.isNotEmpty(deptList)) {
            for (BaseDept baseDept : deptList) {
                // 根节点展开
                tree.add(new BaseDeptTreeVO(baseDept));
            }
        }
        List<BaseDeptTreeVO> result = TreeUtil.buildByRecursive(tree, root);
        return BeanUtil.isEmpty(result) ? new ArrayList<>() : result;
    }

    /**
     * 添加部门用户关系
     *
     * @param deptCode 部门编码
     * @param userIds  用户集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void addDeptUser(String deptCode, List<String> userIds) {
        if (BeanUtil.isNotEmpty(userIds)) {
            for (String userId : userIds) {
                BaseDeptUser deptUser = new BaseDeptUser();
                deptUser.setDeptCode(deptCode);
                deptUser.setUserId(userId);
                deptUserBiz.insertModel(deptUser);
            }
        }
    }

    /**
     * 通过部门编码和用户删除
     *
     * @param deptCode 部门编码
     * @param userId   用户主键
     */
    public int delDeptUserByUserId(String deptCode, String userId) {
        if (StringUtils.isNotBlank(deptCode) && StringUtils.isNotBlank(userId)) {
            BaseDeptUser deptUser = new BaseDeptUser();
            deptUser.setUserId(userId);
            deptUser.setDeptCode(deptCode);
            return deptUserBiz.delDeptUser(deptUser);
        }
        return 0;
    }
}