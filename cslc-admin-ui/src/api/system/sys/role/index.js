import request from '@/utils/request'

/**
 * 查询列表
 * @param {*} query 查询条件
 */
export function sysRoleList(query) {
  return request({
    url: '/admin/sysRoles/',
    method: 'get',
    params: query
  })
}
/**
 * 添加新实体
 * @param {*} obj 实体信息
 */
export function addSysRole(obj) {
  return request({
    url: '/admin/sysRoles',
    method: 'post',
    data: obj
  })
}
/**
 * 通过主键获取实体详情
 * @param {*} id 主键
 */
export function getSysRole(id) {
  return request({
    url: '/admin/sysRoles/' + id,
    method: 'get'
  })
}
/**
 * 通过主键删除实体
 * @param {*} id 主键
 */
export function delSysRole(id) {
  return request({
    url: '/admin/sysRoles/' + id,
    method: 'delete'
  })
}
/**
 * 通过id更新实体
 * @param {*} id 主键
 * @param {*} obj 实体信息
 */
export function putSysRole(id, obj) {
  return request({
    url: '/admin/sysRoles/' + id,
    method: 'put',
    data: obj
  })
}

/**
 * 获取角色树
 * @param {*} query 查询条件
 */
export function getSysRoleTree() {
  return request({
    url: '/admin/sysRoles/tree',
    method: 'get'
  })
}
