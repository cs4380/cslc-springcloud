import request from '@/utils/request'

/**
 * 获取字典类型树
 */
export function getDictTypeTree() {
  return request({
    url: '/admin/sysDictTypes/tree',
    method: 'get'
  })
}

/**
 * 通过主键id获取信息
 * @param {String} id 主键
 */
export function getDictTypeById(id) {
  return request({
    url: `/admin/sysDictTypes/${id}`,
    method: 'get'
  })
}

/**
 * 通过id更新实体
 * @param {*} id 主键
 * @param {*} obj 实体信息
 */
export function putSysDictType(id, obj) {
  return request({
    url: '/admin/sysDictTypes/' + id,
    method: 'put',
    data: obj
  })
}

/**
 * 通过主键删除实体
 * @param {*} id 主键
 */
export function delSysDictType(id) {
  return request({
    url: '/admin/sysDictTypes/' + id,
    method: 'delete'
  })
}

/**
 * 添加新实体
 * @param {*} obj 实体信息
 */
export function addSysDictType(obj) {
  return request({
    url: '/admin/sysDictTypes',
    method: 'post',
    data: obj
  })
}
