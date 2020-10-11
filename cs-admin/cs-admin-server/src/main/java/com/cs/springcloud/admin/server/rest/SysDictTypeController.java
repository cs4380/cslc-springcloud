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

package com.cs.springcloud.admin.server.rest;

import com.cs.springcloud.admin.api.model.SysDictType;
import com.cs.springcloud.admin.api.vo.SysDictTypeTreeVO;
import com.cs.springcloud.admin.server.biz.SysDictTypeBiz;
import com.cs.springcloud.auth.client.annotation.CheckAuthToken;
import com.cs.springcloud.base.core.result.ObjectResult;
import com.cs.springcloud.base.core.rest.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SysDictType 数据字典类型接口实现类.
 *
 * @author cs
 * @version 1.0
 * @date 2019-10-07 21:41
 */
@CheckAuthToken
@RestController
@RequestMapping("/sysDictTypes")
@Api(tags = "数据字典类型")
public class SysDictTypeController extends BaseController<SysDictTypeBiz, SysDictType> {

    @GetMapping("/tree")
    @ApiOperation(value = "获取数据字典类型树")
    public ObjectResult<List<SysDictTypeTreeVO>> getDictTypeTree() {
        ObjectResult<List<SysDictTypeTreeVO>> result = new ObjectResult<>();
        result.setData(this.baseBiz.getDictTypeTree());
        return result;
    }

}