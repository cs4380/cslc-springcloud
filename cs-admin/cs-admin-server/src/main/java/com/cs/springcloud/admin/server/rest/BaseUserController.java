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

import com.cs.springcloud.admin.api.dto.UserInfoDTO;
import com.cs.springcloud.admin.api.model.BaseUser;
import com.cs.springcloud.admin.api.vo.BaseUserVO;
import com.cs.springcloud.admin.server.biz.BaseUserBiz;
import com.cs.springcloud.auth.client.annotation.CheckAuthToken;
import com.cs.springcloud.auth.client.annotation.IgnoreAuthToken;
import com.cs.springcloud.base.common.constant.HttpStatusConstant;
import com.cs.springcloud.base.common.constant.MessageConstant;
import com.cs.springcloud.base.core.result.ObjectResult;
import com.cs.springcloud.base.core.result.TableResult;
import com.cs.springcloud.base.core.rest.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * BaseUserController 基础用户接口实现类.
 *
 * @author cs
 * @version 1.0
 * @date 2019-09-30 15:23:58
 */
@CheckAuthToken
@RestController
@RequestMapping("/baseUsers")
@Api(tags = "基础用户模块")
public class BaseUserController extends BaseController<BaseUserBiz, BaseUser> {

    @GetMapping("/info")
    @ApiOperation("获取用户信息：用户基本信息")
    public ObjectResult<UserInfoDTO> getUserInfo() {
        ObjectResult<UserInfoDTO> res = new ObjectResult<>();
        res.setData(this.baseBiz.getUserInfo());
        return res;
    }

    @IgnoreAuthToken
    @GetMapping("/info/{username}")
    @ApiOperation("获取用户基本信息：用户基本信息、角色集，排除禁用和删除")
    public ObjectResult<UserInfoDTO> getUserInfoByUsername(@PathVariable("username") String username) {
        ObjectResult<UserInfoDTO> res = new ObjectResult<>();
        res.setData(this.baseBiz.getUserInfoByUsername(username));
        return res;
    }

    @GetMapping("/permission")
    @ApiOperation("获取用户权限：菜单权限列表")
    public ObjectResult<UserInfoDTO> getUserPermission() {
        ObjectResult<UserInfoDTO> res = new ObjectResult<>();
        res.setData(this.baseBiz.getUserPermission());
        return res;
    }

    /**
     * 修改密码
     *
     * @param baseUserVO 用户信息
     */
    @PutMapping("/password")
    @ApiOperation("修改密码")
    public ObjectResult changePassword(@RequestBody BaseUserVO baseUserVO) {
        String password = baseUserVO.getPassword();
        String newPassword = baseUserVO.getNewPassword();

        // 验证密码是否为空
        if (StringUtils.isBlank(password) || StringUtils.isBlank(newPassword)) {
            ObjectResult result = new ObjectResult();
            result.setStatus(HttpStatusConstant.FAIL);
            result.setMessage(MessageConstant.MVC_VALIDATE_MSG);
            return result;
        }

        return this.baseBiz.changePassword(password, newPassword);
    }

    /**
     * 通过部门编码获取用户列表
     *
     * @param deptCode 部门编码
     * @return 指定部门的用户列表
     */
    @GetMapping("/deptCode/{deptCode}")
    @ApiOperation("指定部门的用户列表")
    public TableResult<BaseUser> getDeptUsersByDeptCode(
            @PathVariable("deptCode") @ApiParam("部门编码") String deptCode,
            @RequestParam(value = "page", defaultValue = "") @ApiParam("是否排除当前部门的") String isExcludeDeptUser,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return this.baseBiz.getDeptUsersByDeptCode(deptCode, page, limit);
    }

    /**
     * 排除指定部门的用户列表
     *
     * @param excludeDeptCode 排除指定部门
     * @param page            页码
     * @param limit           页容量
     * @return 排除指定部门的用户列表
     */
    @GetMapping("/exclude/{excludeDeptCode}")
    @ApiOperation("排除指定部门的用户列表")
    public TableResult<BaseUser> getUsersExcludeDept(
            @PathVariable("excludeDeptCode") @ApiParam("排除指定部门") String excludeDeptCode,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return this.baseBiz.getUsersExcludeDept(excludeDeptCode, page, limit);
    }

    /**
     * 获取用户角色编码列表
     *
     * @return 获取用户角色编码列表
     */
    @GetMapping("/{userId}/role")
    @ApiOperation("获取用户角色编码列表")
    public ObjectResult<List<String>> getUserRolesByUserId(
            @PathVariable("userId") String userId) {
        ObjectResult<List<String>> result = new ObjectResult<>();
        result.setData(this.baseBiz.getUserRolesByUserId(userId));
        return result;
    }

    /**
     * 更新用户的角色
     *
     * @return 更新用户的角色
     */
    @PutMapping("/{userId}/role")
    @ApiOperation("获取用户角色编码列表")
    public ObjectResult<Void> setUserRole(
            @PathVariable("userId") String userId,
            @RequestBody BaseUserVO user) {
        ObjectResult<Void> result = new ObjectResult<>();
        this.baseBiz.setUserRole(userId, user.getRoleCodes());
        return result;
    }
}