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
import com.cs.springcloud.admin.api.model.SysDictValue;
import com.cs.springcloud.admin.server.mapper.SysDictValueMapper;
import com.cs.springcloud.base.core.biz.BaseBusinessBiz;
import com.cs.springcloud.base.common.constant.CommonConstant;
import com.cs.springcloud.base.common.exception.BusinessException;
import com.cs.springcloud.base.common.util.BeanUtil;
import com.cs.springcloud.base.common.util.UUIDUtil;
import com.cs.springcloud.base.common.vo.ParamQuery;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * SysDictValue 数据字典值.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-07 21:41
 */
@Service
public class SysDictValueBiz extends BaseBusinessBiz<SysDictValueMapper, SysDictValue> {

    @Override
    public boolean insertModel(SysDictValue entity) {
        int count = this.getByDictTypeCode(entity.getDictCode());
        if (count > 0) {
            throw new BusinessException("字典编码已存在！");
        }
        entity.setId(UUIDUtil.randomUUID());
        return super.insertModel(entity);
    }


    @Override
    public boolean removeById(Serializable id) {
        SysDictValue dictType = super.getById(id);
        if (dictType == null) {
            throw new BusinessException("未找到删除的字典类型！");
        }
        dictType.setIsDeleted(CommonConstant.IS_FALSE);
        return super.updateById(dictType);
    }

    @Override
    public List<SysDictValue> selectListAll(ParamQuery query) {
        List<SysDictValue> dataList = super.selectFieldListAll(query, new String[]{"id", "dict_title", "dict_code", "type_id"});
        return BeanUtil.isNotEmpty(dataList) ? dataList : new ArrayList<>();
    }

    @Override
    public List<SysDictValue> selectTableByParamQuery(ParamQuery query) {
        return super.selectListAll(query, new String[]{"id", "dict_code", "dict_title", "description", "typeId", "order_num", "update_time"});
    }

    /**
     * 根据code条件进行查询总数
     *
     * @param dictCode 编码
     * @return 匹配个数
     */
    public int getByDictTypeCode(String dictCode) {
        QueryWrapper<SysDictValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code", dictCode);
        return this.baseMapper.selectCount(queryWrapper);
    }
}