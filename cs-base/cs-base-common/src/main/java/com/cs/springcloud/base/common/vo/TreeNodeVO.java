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

package com.cs.springcloud.base.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * TreeNodeVO 构造树VO的基类.
 *
 * @author cs
 * @version 1.0
 * @date 2020-01-14
 */
@Getter
@Setter
@ToString
public class TreeNodeVO<T> implements Serializable {

    private static final long serialVersionUID = -3844932113013781822L;
    /**
     * 节点id
     */
    protected Object id;

    /**
     * 节点标签
     */
    protected String label;

    /**
     * 父节点id
     */
    protected Object parentId;

    /**
     * 子节点集
     */
    protected List<T> children;


    public TreeNodeVO() {
        children = new ArrayList<>();
    }

    /**
     * 添加节点
     */
    public void add(T node) {
        children.add(node);
    }
}