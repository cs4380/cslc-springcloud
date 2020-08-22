import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  // 请求头部添加“/api”
  baseURL: process.env.VUE_APP_BASE_API,
  // 请求超时毫秒（停止请求）
  timeout: 5000
})

// request 拦截器请求添加token信息
service.interceptors.request.use(
  config => {
    const token = getToken()
    if (token) {
      // 让每个请求携带token--['X-Token']为自定义key 请根据实际情况自行修改
      config.headers.Authorization = 'Bearer ' + token
    } else {
      // 增加客户端认证
      config.headers.Authorization = 'Basic YXBwOmFwcA=='
    }
    return config
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

// response拦截器判断响应结果状态码
service.interceptors.response.use(
  response => {
    return response.data
    // http状态码200，判断业务status状态码
    // if (response.data.status !== 200) {
    //   Message({
    //     message: response.data.message || '业务请求异常，请联系管理员！',
    //     type: 'error',
    //     duration: 5 * 1000
    //   })
    // } else {
    //   return response.data
    // }
  },
  error => {
    // http 状态码非200时判断
    const response = error.response
    if (response.status === 500) {
      // 判断是否token过期或者授权过期
      const msg = response.data.message.split(':')
      if (msg.length === 2) {
        // 401001: 用户授权过期或者其他端已登出
        if (msg[0] === '401001') {
          MessageBox.confirm('用户授权过期或者其他端已登出,请重新登陆!', '授权过期', {
            confirmButtonText: '登陆',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            // 刷新页面
            location.reload()
          })
          return Promise.reject(error)
        }
      }
    }
    Message({
      message: response.data.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
