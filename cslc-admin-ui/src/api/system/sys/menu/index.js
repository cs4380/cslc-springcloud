import request from '@/utils/request'

/**
 * 查询列表
 * @param {*} query 查询条件
 */
export function sysMenuList(query) {
  return request({
    url: '/admin/sysMenus/',
    method: 'get',
    params: query
  })
}
/**
 * 添加新实体
 * @param {*} obj 实体信息
 */
export function addSysMenu(obj) {
  return request({
    url: '/admin/sysMenus',
    method: 'post',
    data: obj
  })
}
/**
 * 通过主键获取实体详情
 * @param {*} id 主键
 */
export function getSysMenu(id) {
  return request({
    url: '/admin/sysMenus/' + id,
    method: 'get'
  })
}
/**
 * 通过主键删除实体
 * @param {*} id 主键
 */
export function delSysMenu(id) {
  return request({
    url: '/admin/sysMenus/' + id,
    method: 'delete'
  })
}
/**
 * 通过id更新实体
 * @param {*} id 主键
 * @param {*} obj 实体信息
 */
export function putSysMenu(id, obj) {
  return request({
    url: '/admin/sysMenus/' + id,
    method: 'put',
    data: obj
  })
}
/**
 * 获取菜单树
 */
export function getMenuTrees() {
  return request({
    url: '/admin/sysMenus/tree',
    method: 'get'
  })
}
