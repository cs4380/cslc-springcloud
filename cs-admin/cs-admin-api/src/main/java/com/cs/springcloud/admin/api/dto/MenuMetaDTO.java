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

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * MenuMetaDTO  菜单Meta信息.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-17 20:40
 */
@Data
public class MenuMetaDTO implements Serializable {

    private static final long serialVersionUID = 4905155582692427492L;
    /**
     * 菜单标题
     */
    @ApiModelProperty("菜单标题")
    private String title;
    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String icon;

    public MenuMetaDTO() {
    }

    public MenuMetaDTO(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }
}