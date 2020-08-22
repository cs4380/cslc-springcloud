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

package com.cs.springcloud.base.core.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.springcloud.base.common.constant.CommonConstant;
import com.cs.springcloud.base.common.util.BeanUtil;
import com.cs.springcloud.base.common.vo.ParamQuery;
import com.cs.springcloud.base.core.util.EntityUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * BaseBusinessBiz 基础业务类.
 * <p>
 * 新增和修改时自动添加: 创建时间、创建人、更新时间、更新人.
 * 泛型：M 是 mapper 对象，Model 是实体.
 * 说明：
 * 使用mybatis-plus，单表操作时通过Wrapper构造生成查询对象，
 * 此类提供常用的方法，通过Model构造生成查询对象。
 * </p>
 *
 * @author cs
 * @version 1.0
 * @date 2019-09-28 21:25
 */
public abstract class BaseBusinessBiz<M extends BaseMapper<Model>, Model> extends ServiceImpl<M, Model> {

    /**
     * 通过实体，新增单条数据
     * <p>
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * </p>
     *
     * @param entity 实体
     */
    public boolean insertModel(Model entity) {
        BeanUtil.beanAttributeValueTrim(entity);
        EntityUtils.setCreatAndUpdatInfo(entity);
        return super.save(entity);
    }

    /**
     * 通过实体，更新单条数据
     * <p>
     * 根据主键更新实体全部字段，null值不会更新.
     * 需要更新时，添加在实体类添加
     * TableField(strategy = FieldStrategy.IGNORED)
     * </p>
     *
     * @param entity 实体，包含id
     */
    @Override
    public boolean updateById(Model entity) {
        BeanUtil.beanAttributeValueTrim(entity);
        EntityUtils.setUpdatedInfo(entity);
        return super.updateById(entity);
    }

    /**
     * 通过主键，获取单条数据
     * <p>
     * 根据查询条件中的属性进行查询，只能有一个返回值，有多个结果会是抛出异常，查询条件使用等号。
     * 注意：如果当前实体中存在isDeleted字段，默认添加未删除的条件
     * </p>
     *
     * @param model 实体
     */
    public Model selectModel(Model model) {
        QueryWrapper<Model> queryWrapper = this.modelBuildQueryWrapper(model);
        return super.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 通过主键，获取单条数据
     * <p>
     * 根据查询条件中的属性进行查询，只能有一个返回值，有多个结果会是抛出异常，查询条件使用等号。
     * 注意：如果当前实体中存在isDeleted字段，默认添加未删除的条件
     * </p>
     *
     * @param model  实体
     * @param fields 查询字段
     */
    public Model selectModel(Model model, String... fields) {
        QueryWrapper<Model> queryWrapper = this.modelBuildQueryWrapper(model);
        queryWrapper.select(fields);
        return super.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 获取全部数据
     * <p>
     * 注意：如果当前实体中存在isDeleted字段，默认添加未删除的条件
     * </P>
     *
     * @param model 查询条件,为 null 则查询所有
     */
    public List<Model> selectModelList(Model model) {
        QueryWrapper<Model> queryWrapper = this.modelBuildQueryWrapper(model);
        return super.baseMapper.selectList(queryWrapper);
    }

    /**
     * 通过实体条件，统计数量
     * <p>
     * 根据实体中的属性查询总数，查询条件使用等号
     * </p>
     *
     * @param model 查询实体
     * @return 匹配总数
     */
    public int selectCount(Model model) {
        QueryWrapper<Model> queryWrapper = this.modelBuildQueryWrapper(model);
        return super.baseMapper.selectCount(queryWrapper);
    }

    /**
     * 通过实体，删除单条数据
     *
     * @param model 删除条件
     */
    public int deleteModel(Model model) {
        QueryWrapper<Model> queryWrapper = this.modelBuildQueryWrapper(model);
        return super.baseMapper.delete(queryWrapper);
    }

    /**
     * 通过查询参数，获取翻页数据
     *
     * @param query 查询参数对象
     */
    public List<Model> selectTableByParamQuery(ParamQuery query) {
        return this.selectListAll(query);
    }

    /**
     * 获取全部数据
     * <p>
     * 注意：如果当前实体中存在isDeleted字段，默认添加未删除的条件
     * </P>
     *
     * @param params 查询条件,为 null 则查询所有
     */
    public List<Model> selectListAll(ParamQuery params) {
        return this.selectFieldListAll(params, null);
    }

    /**
     * 获取全部数据
     * <p>
     * 注意：如果当前实体中存在isDeleted字段，默认添加未删除的条件
     * </P>
     *
     * @param params 查询条件,为 null 则查询所有
     * @param fields 实体属性名数组，为null则查询所有
     */
    public List<Model> selectListAll(ParamQuery params, String... fields) {
        return this.selectFieldListAll(params, null);
    }

    /**
     * 获取指定字段结果集多条数据
     * <p>
     * 注意：如果当前实体中存在isDeleted字段，默认添加未删除的条件
     * </p>
     *
     * @param fields 实体属性名数组，为null则查询所有
     */
    public List<Model> selectFieldListAll(String[] fields) {
        return this.selectFieldListAll(null, fields);
    }

    /**
     * 获取指定字段结果集多条数据
     * <p>
     * 注意：如果当前实体中存在isDeleted字段，默认添加未删除的条件
     * </p>
     *
     * @param query  查询条件,为 null 则查询所有非逻辑删除的数据
     * @param fields 实体属性名数组，为null则查询所有
     */
    public List<Model> selectFieldListAll(ParamQuery query, String[] fields) {
        QueryWrapper<Model> queryWrapper = new QueryWrapper<>();
        if (BeanUtil.isNotEmpty(fields)) {
            queryWrapper.select(fields);
        }
        this.queryParam(queryWrapper, query);
        return super.list(queryWrapper);
    }

    /**
     * 通过ParamQuery，设置给queryWrapper查询条件
     * <p>
     * 查询条件都是模糊查询
     * </p>
     *
     * @param queryWrapper 查询对象
     * @param params       查询条件
     */
    public void queryParam(QueryWrapper<Model> queryWrapper, ParamQuery params) {
        Class<Model> clazz = this.getClassByModel();
        // 设置删除参数
        try {
            // 判断是否存在删除字段
            Field field = clazz.getDeclaredField("isDeleted");
            // 存在删除字段，默认添加未删除的条件
            if (field != null) {
                if (BeanUtil.isEmpty(params)) {
                    params = new ParamQuery();
                }
                params.put("is_deleted", CommonConstant.IS_TRUE);
            }
        } catch (NoSuchFieldException e) {
            // 没有逻辑删除字段，则不做逻辑删除处理
        }
        // 设置查询参数
        if (BeanUtil.isNotEmpty(params)) {
            Object value;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                value = entry.getValue();
                String key = this.toLowerCase(entry.getKey());
                // 字符串则做模糊匹配
                if (value instanceof String) {
                    queryWrapper.like(key, entry.getValue().toString() + "%");
                } else {
                    queryWrapper.eq(key, entry.getValue().toString());
                }
            }
        }
    }

    /**
     * 获取当前实体的class对象
     */
    private Class<Model> getClassByModel() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        return (Class<Model>) types[1];
    }

    /**
     * 驼峰字段名转换为数据库字段
     *
     * @param fieldName 驼峰字段名
     * @return 数据库字段
     */
    private String toLowerCase(String fieldName) {
        StringBuilder str = new StringBuilder();
        char[] fieldNames = fieldName.toCharArray();
        for (char field : fieldNames) {
            if (Character.isUpperCase(field)) {
                str.append("_");
            }
            str.append(field);
        }
        return str.toString().toLowerCase();
    }

    /**
     * 通过实体对象构建查询对象
     *
     * @param model 实体对象
     * @return QueryWrapper 查询对象
     */
    private QueryWrapper<Model> modelBuildQueryWrapper(Model model) {
        QueryWrapper<Model> queryWrapper = new QueryWrapper<>();
        try {
            Class clazz = model.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                // 获取属性的名字
                String name = field.getName();
                if ("isDeleted".equals(name)) {
                    queryWrapper.eq("is_deleted", CommonConstant.IS_TRUE);
                    continue;
                } else if (Modifier.isStatic(field.getModifiers())) {
                    // 跳过所有静态属性
                    continue;
                }
                // 私有属性必须设置访问权限
                field.setAccessible(true);
                // 将属性名字的首字母大写
                String filedName = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
                // 获取属性 get 方法
                Method method = clazz.getMethod("get" + filedName);
                // 调用get方法获取属性值
                Object resultValue = method.invoke(model);
                if (null != resultValue) {
                    queryWrapper.eq(this.toLowerCase(name), resultValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryWrapper;
    }
}