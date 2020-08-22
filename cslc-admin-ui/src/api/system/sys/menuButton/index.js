import request from '@/utils/request'

/**
 * 查询列表
 * @param {*} query 查询条件
 */
export function sysMenuButtonList(query) {
  return request({
    url: '/admin/sysMenuButtons',
    method: 'get',
    params: query
  })
}
/**
 * 添加新实体
 * @param {*} obj 实体信息
 */
export function addSysMenuButton(obj) {
  return request({
    url: '/admin/sysMenuButtons',
    method: 'post',
    data: obj
  })
}
/**
 * 通过主键获取实体详情
 * @param {*} id 主键
 */
export function getSysMenuButton(id) {
  return request({
    url: '/admin/sysMenuButtons/' + id,
    method: 'get'
  })
}
/**
 * 通过主键删除实体
 * @param {*} id 主键
 */
export function delSysMenuButton(id) {
  return request({
    url: '/admin/sysMenuButtons/' + id,
    method: 'delete'
  })
}
/**
 * 通过id更新实体
 * @param {*} id 主键
 * @param {*} obj 实体信息
 */
export function putSysMenuButton(id, obj) {
  return request({
    url: '/admin/sysMenuButtons/' + id,
    method: 'put',
    data: obj
  })
}
