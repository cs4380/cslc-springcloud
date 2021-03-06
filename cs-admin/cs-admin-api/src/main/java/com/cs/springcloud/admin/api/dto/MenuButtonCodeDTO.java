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
 * MenuButtonCodeDTO 菜单按钮编码对应关系.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-15 17:06
 */
@Data
public class MenuButtonCodeDTO implements Serializable {

    private static final long serialVersionUID = -3653332667008246004L;
    /**
     * 菜单编码
     */
    @ApiModelProperty("菜单编码")
    private String menuCode;
    /**
     * 按钮编码
     */
    @ApiModelProperty("按钮编码")
    private String buttonCode;
}