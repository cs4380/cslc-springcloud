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

package com.cs.springcloud.base.core.result;

import com.cs.springcloud.base.common.constant.HttpStatusConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * BaseResponse 响应基类.
 *
 * @author cs
 * @version 1.0
 * @date 2020-01-14
 */
@Getter
@Setter
@ApiModel("响应基类")
public class Result {
    /**
     * 响应码
     */
    @ApiModelProperty("响应状态码")
    private int status = HttpStatusConstant.SUCCESS;
    /**
     * 响应信息
     */
    @ApiModelProperty("响应信息")
    private String message;

    /**
     * 返回结果附属信息
     */
    @ApiModelProperty("返回结果附属信息")
    private Map<String, Object> metadata;

    public Result() {
    }

    public Result(String message) {
        this.message = message;
    }

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }
}