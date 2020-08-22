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

package com.cs.springcloud.admin.api.vo;

import com.cs.springcloud.admin.api.model.SysRoleAuthorization;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * RoleAuthorizationVO 角色授权vo.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-04 15:06
 */
@Getter
@Setter
@ToString
@ApiModel("角色授权vo")
public class RoleAuthorizationVO extends SysRoleAuthorization {

    private static final long serialVersionUID = 3989082080759444669L;

    /**
     * 菜单按钮对应的菜单编码
     */
    @ApiModelProperty("菜单按钮对应的菜单编码")
    private String menuCode;

    /**
     * 关联资源集合
     */
    @ApiModelProperty("关联资源集合")
    private List<String> resourceIds;
}