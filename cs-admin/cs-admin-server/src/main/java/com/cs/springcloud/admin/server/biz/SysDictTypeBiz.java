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
import com.cs.springcloud.admin.api.model.SysDictType;
import com.cs.springcloud.admin.api.vo.SysDictTypeTreeVO;
import com.cs.springcloud.admin.server.mapper.SysDictTypeMapper;
import com.cs.springcloud.base.core.biz.BaseBusinessBiz;
import com.cs.springcloud.base.common.constant.CommonConstant;
import com.cs.springcloud.base.common.exception.BusinessException;
import com.cs.springcloud.base.common.util.BeanUtil;
import com.cs.springcloud.base.common.util.TreeUtil;
import com.cs.springcloud.base.common.util.UUIDUtil;
import com.cs.springcloud.base.common.vo.ParamQuery;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * SysDictType 数据字典类型.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-07 21:41
 */
@Service
public class SysDictTypeBiz extends BaseBusinessBiz<SysDictTypeMapper, SysDictType> {

    @Override
    public boolean insertModel(SysDictType entity) {
        int count = this.getByDictTypeCode(entity.getDictTypeCode());
        if (count > 0) {
            throw new BusinessException("字典类型编码已存在！");
        }
        entity.setId(UUIDUtil.randomUUID());
        return super.insertModel(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        SysDictType dictType = super.getById(id);
        if (dictType == null) {
            throw new BusinessException("未找到删除的字典类型！");
        }
        dictType.setIsDeleted(CommonConstant.IS_FALSE);
        return super.updateById(dictType);
    }

    @Override
    public List<SysDictType> selectListAll(ParamQuery query) {
        List<SysDictType> dataList = super.selectFieldListAll(query, new String[]{"id", "dict_type_name", "dict_type_code", "parent_id"});
        return BeanUtil.isNotEmpty(dataList) ? dataList : new ArrayList<>();
    }

    /**
     * 获取数据类型树结构
     *
     * @return
     */
    public List<SysDictTypeTreeVO> getDictTypeTree() {
        // 获取全部数据
        List<SysDictType> dictTypeList = this.selectListAll(null);
        // 缓存树型结构数据
        List<SysDictTypeTreeVO> tree = new ArrayList<>();
        // 构造数据
        if (BeanUtil.isNotEmpty(dictTypeList)) {
            for (SysDictType dictType : dictTypeList) {
                if (CommonConstant.ROOT.equals(dictType.getParentId())) {
                    // 根节点展开
                    tree.add(new SysDictTypeTreeVO(dictType.getId(), dictType.getParentId(), dictType.getDictTypeName(), dictType.getDictTypeCode()));
                } else {
                    // 二级节点不展开
                    tree.add(new SysDictTypeTreeVO(dictType.getId(), dictType.getParentId(), dictType.getDictTypeName(), dictType.getDictTypeCode(), false));
                }
            }
        }
        List<SysDictTypeTreeVO> result = TreeUtil.buildByRecursive(tree, CommonConstant.ROOT);
        return BeanUtil.isEmpty(result) ? new ArrayList<>() : result;
    }

    /**
     * 根据code条件进行查询总数
     *
     * @param dictTypeCode 编码
     * @return 匹配个数
     */
    public int getByDictTypeCode(String dictTypeCode) {
        QueryWrapper<SysDictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type_code", dictTypeCode);
        return this.baseMapper.selectCount(queryWrapper);
    }
}