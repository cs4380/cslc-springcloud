import request from '@/utils/request'

/**
 * 登陆接口
 * @param {*} data
 */
export function login(data) {
  const params = new URLSearchParams()
  params.append('username', data.username)
  params.append('password', data.password)
  params.append('grant_type', data.grant_type)
  return request({
    url: '/auth/oauth/token',
    method: 'post',
    params,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
/**
 * 登出
 */
export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

/**
 * 获取用户信息
 */
export function getUserInfo() {
  return request({
    url: '/admin/baseUsers/info',
    method: 'get'
  })
}

/**
 * 获取用户权限
 */
export function getUserPermission() {
  return request({
    url: '/admin/baseUsers/permission',
    method: 'get'
  })
}
