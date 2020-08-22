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

import com.cs.springcloud.admin.api.model.BaseUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * BaseUserVO 用户信息vo.
 *
 * @author cs
 * @version 1.0
 * @date 2019-12-13 13:22
 */
@Setter
@Getter
@ToString
@ApiModel("用户信息vo")
public class BaseUserVO extends BaseUser {

    private static final long serialVersionUID = 1328369755045511388L;
    /**
     * 新密码
     */
    @ApiModelProperty("新密码")
    private String newPassword;

    /**
     * 角色编码集合
     */
    @ApiModelProperty("角色编码集合")
    private List<String> roleCodes;
}