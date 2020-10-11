import request from '@/utils/request'

/**
 * 查询列表
 * @param {*} query 查询条件
 */
export function sysRoleAuthorizationList(query) {
  return request({
    url: '/admin/sysRoleAuthorizations',
    method: 'get',
    params: query
  })
}
/**
 * 添加新实体
 * @param {*} obj 实体信息
 */
export function addSysRoleAuthorization(obj) {
  return request({
    url: '/admin/sysRoleAuthorizations',
    method: 'post',
    data: obj
  })
}
/**
 * 通过主键删除实体
 * @param {*} id 主键
 */
export function delSysRoleAuthorization(id) {
  return request({
    url: '/admin/sysRoleAuthorizations/' + id,
    method: 'delete'
  })
}
/**
 * 角色授权接口，角色和菜单做关联
 * @param {String} roleCode 角色编码
 * @param {*} menuList 菜单集合
 */
export function setRoleAuth(roleCode, menuList) {
  return request({
    url: `/admin/sysRoleAuthorizations/${roleCode}`,
    method: 'post',
    data: menuList
  })
}
/**
 * 获取指定角色的菜单权限
 * @param {String} roleCode 角色编码
 * @param {int} resourceType 资源类型
 */
export function getAuthByRoleCode(roleCode, resourceType) {
  return request({
    url: `/admin/sysRoleAuthorizations/${roleCode}/resourceType/${resourceType}`,
    method: 'get'
  })
}
/**
 * 获取角色对应的菜单权限集合
 * @param {String} roleCode 角色编码
 */
export function getMenuAuthByRoleCode(roleCode) {
  return request({
    url: `/admin/sysRoleAuthorizations/roleCode/${roleCode}/menus`,
    method: 'get'
  })
}

