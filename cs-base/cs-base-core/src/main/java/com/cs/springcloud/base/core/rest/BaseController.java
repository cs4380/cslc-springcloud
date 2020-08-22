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

package com.cs.springcloud.base.core.rest;

import com.cs.springcloud.base.common.constant.HttpStatusConstant;
import com.cs.springcloud.base.common.constant.MessageConstant;
import com.cs.springcloud.base.common.util.BeanUtil;
import com.cs.springcloud.base.common.util.StringEscapeEditor;
import com.cs.springcloud.base.common.validate.AddField;
import com.cs.springcloud.base.common.validate.UpdateField;
import com.cs.springcloud.base.common.vo.ParamQuery;
import com.cs.springcloud.base.core.biz.BaseBusinessBiz;
import com.cs.springcloud.base.core.result.ObjectResult;
import com.cs.springcloud.base.core.result.TableResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * BaseController 前端控制器基类.
 * <p>
 * 单表操作基础接口，可重写biz层单表操作，满足业务需求
 * </p>
 *
 * @param <Biz>   业务逻辑层
 * @param <Model> 实体
 * @author cs
 * @version 1.0
 * @date 2020-01-14
 */
@RestController
public class BaseController<Biz extends BaseBusinessBiz, Model> {

    @Autowired
    protected Biz baseBiz;

    /**
     * 解决类型转换问题
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringEscapeEditor());
        binder.registerCustomEditor(String[].class, new StringEscapeEditor());
    }

    /**
     * 新增单个对象
     *
     * @param entity 对象实体
     */
    @PostMapping
    @ApiOperation("新增单个对象")
    public ObjectResult<Model> create(@RequestBody @Validated({AddField.class}) @ApiParam("实体对象") Model entity,
                                      BindingResult bindingResult) {
        ObjectResult<Model> result = new ObjectResult<>();

        // 判单验证
        if (bindingResult.hasErrors()) {
            result.setStatus(HttpStatusConstant.FAIL);
            result.setMessage(MessageConstant.MVC_VALIDATE_MSG);
            return result;
        }

        try {
            baseBiz.insertModel(entity);
            result.setData(entity);
        } catch (Exception e) {
            result.setStatus(HttpStatusConstant.FAIL);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 删除单个对象
     * <p>
     * 默认物理删除，如果需要可以重写removeById方法实现逻辑删除
     * </p>
     *
     * @param id 主键
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除单个对象")
    public ObjectResult<Model> remove(@PathVariable("id") @ApiParam("主键") Serializable id) {
        ObjectResult<Model> result = new ObjectResult<>();
        try {
            baseBiz.removeById(id);
        } catch (Exception e) {
            result.setStatus(HttpStatusConstant.FAIL);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 更新单个对象
     * <p>
     * 根据主键更新属性不为null的值
     * </p>
     *
     * @param id     主键
     * @param entity 实体对象
     */
    @PutMapping("/{id}")
    @ApiOperation("更新单个对象")
    public ObjectResult<Model> update(@PathVariable("id") @ApiParam("主键") Serializable id,
                                      @RequestBody @Validated(UpdateField.class) @ApiParam("实体对象") Model entity,
                                      BindingResult bindingResult) {
        ObjectResult<Model> result = new ObjectResult<>();
        if (null == id || StringUtils.isBlank(id.toString())) {
            result.setStatus(HttpStatusConstant.FAIL);
            result.setMessage("请选择待更新的数据！");
            return result;
        }
        // 判单验证
        if (bindingResult.hasErrors()) {
            result.setStatus(HttpStatusConstant.FAIL);
            result.setMessage(MessageConstant.MVC_VALIDATE_MSG);
            return result;
        }

        try {
            baseBiz.updateById(entity);
            result.setData(entity);
        } catch (Exception e) {
            result.setStatus(HttpStatusConstant.FAIL);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 通过主键获取对象信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    @ApiOperation("查询单个对象")
    public ObjectResult<Model> findModelById(@PathVariable("id") @ApiParam("主键") Serializable id) {
        ObjectResult<Model> result = new ObjectResult<>();
        try {
            Object obj = baseBiz.getById(id);
            result.setData((Model) obj);
        } catch (Exception e) {
            result.setStatus(HttpStatusConstant.FAIL);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 分页获取数据列表
     *
     * @param params 查询条件
     */
    @GetMapping
    @ApiOperation("分页获取数据列表")
    public TableResult<Model> list(@RequestParam @ApiParam("查询条件") Map<String, Object> params) {
        ParamQuery paramQuery = new ParamQuery(params);
        Page pageHelper = PageHelper.startPage(paramQuery.getPage(), paramQuery.getLimit());
        List<Model> result = baseBiz.selectTableByParamQuery(paramQuery);

        if (BeanUtil.isEmpty(result)) {
            return new TableResult<>();
        }
        return new TableResult<>(pageHelper.getTotal(), result);
    }
}