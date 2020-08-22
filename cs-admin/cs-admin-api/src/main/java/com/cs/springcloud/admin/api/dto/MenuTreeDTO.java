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

package com.cs.springcloud.admin.api.dto;

import com.cs.springcloud.admin.api.model.SysMenu;
import com.cs.springcloud.base.common.vo.TreeNodeVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * MenuTreeDTO 系统菜单树.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-19 11:40
 */
@Data
public class MenuTreeDTO extends TreeNodeVO<SysMenu> {

    private static final long serialVersionUID = -3495969830542184702L;
    /**
     * 菜单类型(0:目录|1:菜单)
     */
    @ApiModelProperty("菜单类型(0:目录|1:菜单)")
    private Integer type;
    /**
     * 菜单编码
     */
    @ApiModelProperty("菜单编码")
    private String menuCode;
    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;
    /**
     * 菜单路径（路径别名）
     */
    @ApiModelProperty("菜单路径（路径别名）")
    private String path;
    /**
     * 菜单组件地址(菜单url地址)
     */
    @ApiModelProperty("菜单组件地址(菜单url地址)")
    private String component;
    /**
     * 页面组件(页面目录地址)
     */
    @ApiModelProperty("页面组件(页面目录地址)")
    private String redirect;
    /**
     * 菜单Meta信息
     */
    @ApiModelProperty("菜单Meta信息")
    private MenuMetaDTO meta;

    /**
     * 是否隐藏(true:隐藏|false:未隐藏)
     */
    @ApiModelProperty("是否隐藏(true:隐藏|false:未隐藏)")
    private Boolean hidden;

    /**
     * 构建菜单树
     *
     * @param sysMenu 菜单基本信息
     */
    public MenuTreeDTO(SysMenu sysMenu) {
        this.id = sysMenu.getId();
        this.parentId = sysMenu.getParentId();
        this.type = sysMenu.getMenuType();
        this.path = sysMenu.getMenuPath();
        this.name = sysMenu.getMenuPath();
        this.menuCode = sysMenu.getMenuCode();
        this.redirect = sysMenu.getRedirect();
        this.component = sysMenu.getComponent();
        // true:隐藏|false:未隐藏
        this.hidden = sysMenu.getHidden() == 1 ? true : false;
        meta = new MenuMetaDTO(sysMenu.getMenuTitle(), sysMenu.getMenuIcon());
    }
}