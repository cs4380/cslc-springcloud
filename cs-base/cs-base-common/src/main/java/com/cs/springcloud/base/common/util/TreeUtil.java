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

package com.cs.springcloud.base.common.util;

import com.cs.springcloud.base.common.vo.TreeNodeVO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * TreeUtil 树工具类.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-02 21:47
 */
public class TreeUtil<T extends TreeNodeVO> {
    /**
     * 两层循环实现建树
     *
     * @param treeNodes  传入的树节点列表
     * @param root       父节点id
     * @param comparator 比较器
     */
    public static <T extends TreeNodeVO> List<T> bulid(List<T> treeNodes, Object root, Comparator comparator) {
        List<T> trees = new ArrayList<>();
        //集合为null时 返回时返回空对象
        if (BeanUtil.isEmpty(treeNodes)) {
            return trees;
        }
        for (T treeNode : treeNodes) {

            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }

            for (T it : treeNodes) {
                if (it.getParentId().equals(treeNode.getId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
            if (comparator != null) {
                treeNode.getChildren().sort(comparator);
            }
        }
        if (comparator != null && trees.size() > 1) {
            trees.sort(comparator);
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes 节点集合
     * @param root      父节点id
     */
    public static <T extends TreeNodeVO> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<>();
        if (BeanUtil.isNotEmpty(treeNodes)) {
            for (T treeNode : treeNodes) {
                if (root.equals(treeNode.getParentId())) {
                    trees.add(findChildren(treeNode, treeNodes));
                }
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNode  当前节点
     * @param treeNodes 节点集合
     */
    public static <T extends TreeNodeVO> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<TreeNodeVO>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}