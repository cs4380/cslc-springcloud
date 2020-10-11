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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * ObjectRestResponse 实体响应类.
 *
 * @author cs
 * @version 1.0
 * @date 2020-01-14
 */
@Getter
@Setter
@ApiModel("响应单个对象")
public class ObjectResult<T> extends Result {

    @ApiModelProperty("响应对象")
    private T data;

    public ObjectResult() {
    }

    public ObjectResult(String message) {
        super(message);
    }
}