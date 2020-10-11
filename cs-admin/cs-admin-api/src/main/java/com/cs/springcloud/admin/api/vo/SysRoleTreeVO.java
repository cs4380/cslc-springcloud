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

import com.cs.springcloud.admin.api.model.SysRole;
import com.cs.springcloud.base.common.vo.TreeNodeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SysRoleTreeVO  角色树VO.
 *
 * @author cs
 * @version 1.0
 * @date 2019-11-07 18:15
 */
@Getter
@Setter
@ToString
@ApiModel("角色树VO")
public class SysRoleTreeVO extends TreeNodeVO<SysRole> {

    private static final long serialVersionUID = -2709582128741679872L;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String roleCode;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    private String description;

    /**
     * 构造角色树
     *
     * @param id          角色id
     * @param parentId    角色父级id
     * @param roleCode    角色编码
     * @param roleName    角色名称
     * @param description 角色说明
     */
    public SysRoleTreeVO(Object id, Object parentId, String roleCode, String roleName, String description) {
        this.id = id;
        this.parentId = parentId;
        this.roleCode = roleCode;
        this.label = roleName;
        this.description = description;
    }
}