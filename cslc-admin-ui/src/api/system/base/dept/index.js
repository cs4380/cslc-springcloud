import request from '@/utils/request'

/**
 * 查询列表
 * @param {*} query 查询条件
 */
export function baseDeptTree(query) {
  return request({
    url: '/admin/baseDepts/tree',
    method: 'get',
    params: query
  })
}
/**
 * 添加新实体
 * @param {*} obj 实体信息
 */
export function addBaseDept(obj) {
  return request({
    url: '/admin/baseDepts',
    method: 'post',
    data: obj
  })
}
/**
 * 通过主键获取实体详情
 * @param {*} id 主键
 */
export function getBaseDept(id) {
  return request({
    url: '/admin/baseDepts/' + id,
    method: 'get'
  })
}
/**
 * 通过主键删除实体
 * @param {*} id 主键
 */
export function delBaseDept(id) {
  return request({
    url: '/admin/baseDepts/' + id,
    method: 'delete'
  })
}
/**
 * 通过id更新实体
 * @param {*} id 主键
 * @param {*} obj 实体信息
 */
export function putBaseDept(id, obj) {
  return request({
    url: '/admin/baseDepts/' + id,
    method: 'put',
    data: obj
  })
}
/**
 * 添加部门用户关系
 *
 * @param {String} deptCode 部门编码
 * @param {*} data 查询条件
  *
 */
export function addDeptUser(deptCode, data) {
  return request({
    url: `admin/baseDepts/deptUsers/${deptCode}`,
    method: 'put',
    data: data
  })
}
/**
 * 删除部门的用户关系
 *
 * @param {String} deptCode 部门编码
 * @param {String} userId 用户主键
  *
 */
export function delDeptUserByUserId(deptCode, userId) {
  return request({
    url: `admin/baseDepts/deptUsers/${deptCode}/userId/${userId}`,
    method: 'delete'
  })
}

