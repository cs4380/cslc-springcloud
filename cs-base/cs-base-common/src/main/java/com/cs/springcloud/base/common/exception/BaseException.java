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

package com.cs.springcloud.base.common.exception;

import com.cs.springcloud.base.common.constant.HttpStatusConstant;

/**
 * BaseException 基异常.
 *
 * @author cs
 * @version 1.0
 * @date 2019-09-30 16:13
 */
public class BaseException extends RuntimeException {
    private int status = HttpStatusConstant.FAIL;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}