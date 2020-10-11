import request from '@/utils/request'

/**
 * 查询列表
 * @param {*} query 查询条件
 */
export function baseUserList(query) {
  return request({
    url: 'admin/baseUsers',
    method: 'get',
    params: query
  })
}
/**
 * 添加新实体
 * @param {*} obj 实体信息
 */
export function addBaseUser(obj) {
  return request({
    url: 'admin/baseUsers',
    method: 'post',
    data: obj
  })
}
/**
 * 通过获取用户基本信息
 */
export function getBaseUser() {
  return request({
    url: 'admin/baseUsers/info',
    method: 'get'
  })
}
/**
 * 通过主键删除实体
 * @param {*} id 主键
 */
export function delBaseUser(id) {
  return request({
    url: 'admin/baseUsers/' + id,
    method: 'delete'
  })
}
/**
 * 通过id更新实体
 * @param {*} id 主键
 * @param {*} obj 实体信息
 */
export function putBaseUser(id, obj) {
  return request({
    url: 'admin/baseUsers/' + id,
    method: 'put',
    data: obj
  })
}
/**
 * 修改用户密码
 * @param {JSON} obj 用户密码
 */
export function changePassword(obj) {
  return request({
    url: 'admin/baseUsers/password',
    method: 'put',
    data: obj
  })
}

/**
 * 通过部门编码获取用户列表
 *
 * @param {String} deptCode 部门编码
 * @param {*} query 查询条件
  *
 */
export function getDeptUsersByDeptCode(deptCode, query) {
  return request({
    url: `admin/baseUsers/deptCode/${deptCode}`,
    method: 'get',
    params: query
  })
}
/**
 * 通过用户主键获取用户信息
 */
export function getBaseUserByUserId(userId) {
  return request({
    url: 'admin/baseUsers/' + userId,
    method: 'get'
  })
}
/**
 * 获取用户角色编码列表
 * @param {String} userId 用户id
 */
export function getUserRolesByUserId(userId) {
  return request({
    url: `admin/baseUsers/${userId}/role`,
    method: 'get'
  })
}

/**
 * 获取用户角色编码列表
 * @param {String} userId 用户id
 * @param {Array} roleCodes 角色编码集
 */
export function setUserRole(userId, roleCodes) {
  return request({
    url: `admin/baseUsers/${userId}/role`,
    method: 'put',
    data: roleCodes
  })
}
/**
 * 排除指定部门的用户列表
 *
 * @param {String} excludeDeptCode 排除部门
 * @param {*} query 查询条件
  *
 */
export function getUsersExcludeDept(excludeDeptCode, query) {
  return request({
    url: `admin/baseUsers/exclude/${excludeDeptCode}`,
    method: 'get',
    params: query
  })
}

