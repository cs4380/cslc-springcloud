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
import com.cs.springcloud.admin.api.dto.MenuButtonCodeDTO;
import com.cs.springcloud.admin.api.model.SysMenuButton;
import com.cs.springcloud.admin.server.mapper.SysMenuButtonMapper;
import com.cs.springcloud.base.core.biz.BaseBusinessBiz;
import com.cs.springcloud.base.common.constant.CommonConstant;
import com.cs.springcloud.base.common.constant.HttpStatusConstant;
import com.cs.springcloud.base.common.exception.BusinessException;
import com.cs.springcloud.base.common.util.BeanUtil;
import com.cs.springcloud.base.common.vo.ParamQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * SysMenuButton 系统基础菜单按钮表.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-12 10:22
 */
@Service
public class SysMenuButtonBiz extends BaseBusinessBiz<SysMenuButtonMapper, SysMenuButton> {

    @Override
    public boolean insertModel(SysMenuButton entity) {
        int count = this.getCountByButtonCode(entity.getButtonCode());
        if (count > 0) {
            throw new BusinessException("按钮编码已存在，请重新输入！");
        }
        return super.insertModel(entity);
    }

    /**
     * 通过按钮编码统计个数
     *
     * @param buttonCode 菜单编码
     */
    public int getCountByButtonCode(String buttonCode) {
        if (StringUtils.isBlank(buttonCode)) {
            return 0;
        }
        SysMenuButton sysMenuButton = new SysMenuButton();
        sysMenuButton.setButtonCode(buttonCode);
        return super.selectCount(sysMenuButton);
    }

    @Override
    public boolean removeById(Serializable id) {
        if (null == id) {
            throw new BusinessException("请选择待删除对象！", HttpStatusConstant.FAIL);
        }
        SysMenuButton sysMenuButton = new SysMenuButton();
        sysMenuButton.setId(Integer.valueOf(id.toString()));
        sysMenuButton.setButtonCode(sysMenuButton.getId() + "_" + sysMenuButton.getButtonCode());
        sysMenuButton.setIsDeleted(CommonConstant.IS_FALSE);
        return super.updateById(sysMenuButton);
    }

    @Override
    public List<SysMenuButton> selectTableByParamQuery(ParamQuery query) {
        String[] fields = new String[]{"id", "button_title", "button_code", "url", "method", "description"};
        return super.selectListAll(query, fields);
    }

    /**
     * 通过主键id获取菜单按钮关系列表
     *
     * @param menuButtonIds 主键ids
     */
    public List<MenuButtonCodeDTO> getMenuCodeAndButCodeByButIds(List<Integer> menuButtonIds) {
        if (BeanUtil.isEmpty(menuButtonIds)) {
            return new ArrayList<>();
        }
        List<MenuButtonCodeDTO> menuButtonList = this.baseMapper.selectMenuCodeAndButCodeByButIds(menuButtonIds);
        return BeanUtil.isEmpty(menuButtonList) ? new ArrayList<>() : menuButtonList;
    }

    /**
     * 获取所有菜单按钮关系
     */
    public List<MenuButtonCodeDTO> getAllMenuCodeAndButCode() {
        List<MenuButtonCodeDTO> menuButtonList = this.baseMapper.selectMenuCodeAndButCodeByButIds(null);
        return BeanUtil.isEmpty(menuButtonList) ? new ArrayList<>() : menuButtonList;
    }

    /**
     * 通过菜单按钮主键ids获取列表
     *
     * @param buttonIds 菜单按钮ids
     * @return "id", "buttonTitle", "buttonCode", "url", "method"
     */
    public List<SysMenuButton> getMenuButtonByIds(List<Integer> buttonIds) {
        if (BeanUtil.isEmpty(buttonIds)) {
            return new ArrayList<>();
        }
        String[] fields = new String[]{"id", "button_title", "button_code", "url", "method"};
        QueryWrapper<SysMenuButton> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(fields);
        queryWrapper.eq("is_deleted", CommonConstant.IS_TRUE);
        queryWrapper.in("id", buttonIds);
        return super.list(queryWrapper);
    }
}