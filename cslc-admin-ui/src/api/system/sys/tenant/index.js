import request from '@/utils/request'
/**
 * 查询列表
 * @param {*} query 查询条件
 */
export function page(query) {
  return request({
    url: '/admin/sysTenants',
    method: 'get',
    params: query
  })
}
/**
 * 添加新实体
 * @param {*} obj 实体信息
 */
export function addSysTenant(obj) {
  return request({
    url: '/admin/sysTenants',
    method: 'post',
    data: obj
  })
}
/**
 * 通过主键获取实体详情
 * @param {*} id 主键
 */
export function getSysTenant(id) {
  return request({
    url: '/admin/sysTenants/' + id,
    method: 'get'
  })
}
/**
 * 通过主键删除实体
 * @param {*} id 主键
 */
export function delSysTenant(id) {
  return request({
    url: '/admin/sysTenants/' + id,
    method: 'delete'
  })
}
/**
 * 通过id更新实体
 * @param {*} id 主键
 * @param {*} obj 实体信息
 */
export function putSysTenant(id, obj) {
  return request({
    url: '/admin/sysTenants/' + id,
    method: 'put',
    data: obj
  })
}
