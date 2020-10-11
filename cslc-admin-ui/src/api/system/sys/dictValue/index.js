import request from '@/utils/request'

/**
 * 查询列表
 * @param {*} query 查询条件
 */
export function sysDictValueList(query) {
  return request({
    url: '/admin/sysDictValues',
    method: 'get',
    params: query
  })
}
/**
 * 添加新实体
 * @param {*} obj 实体信息
 */
export function addSysDictValue(obj) {
  return request({
    url: '/admin/sysDictValues',
    method: 'post',
    data: obj
  })
}
/**
 * 通过主键获取实体详情
 * @param {*} id 主键
 */
export function getSysDictValue(id) {
  return request({
    url: '/admin/sysDictValues/' + id,
    method: 'get'
  })
}
/**
 * 通过主键删除实体
 * @param {*} id 主键
 */
export function delSysDictValue(id) {
  return request({
    url: '/admin/sysDictValues/' + id,
    method: 'delete'
  })
}
/**
 * 通过id更新实体
 * @param {*} id 主键
 * @param {*} obj 实体信息
 */
export function putSysDictValue(id, obj) {
  return request({
    url: '/admin/sysDictValues/' + id,
    method: 'put',
    data: obj
  })
}
