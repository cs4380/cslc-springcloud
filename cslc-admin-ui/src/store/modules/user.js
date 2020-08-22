import { login, logout, getUserInfo, getUserPermission } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import generateMenus from '@/utils/menus'

const state = {
  token: getToken(),
  // 用户姓名
  name: '',
  // 用户头像
  portrait: '',
  // 用户权限菜单
  menus: [],
  // 用户权限菜单按钮
  menuButton: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_PORTRAIT: (state, portrait) => {
    state.portrait = portrait
  },
  SET_MENUS: (state, menus) => {
    state.menus = menus
  },
  SET_MENU_BUTTON: (state, menuButton) => {
    state.menuButton = menuButton
  }
}

const actions = {
  // 用户登陆
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password, grant_type: 'password' }).then(response => {
        commit('SET_TOKEN', response.access_token)
        setToken(response.access_token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 获取用户信息
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getUserInfo(state.token).then(response => {
        if (!response) {
          reject('获取用户信息失败，请重新登陆！')
        }
        commit('SET_NAME', response.data.baseUser.name)
        commit('SET_PORTRAIT', response.data.baseUser.portrait)
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // 获取用户权限
  getPermission({ commit, state }) {
    return new Promise((resolve, reject) => {
      getUserPermission(state.token).then(response => {
        if (!response) {
          reject('获取用户权限异常，请重新登陆！')
        }
        // 缓存后台原始菜单权限数据
        const menuPermission = [...response.data.menuTree]
        localStorage.setItem('menuPermission', JSON.stringify(menuPermission))
        // 给vuex设置可识别的菜单json数据
        commit('SET_MENUS', generateMenus(response.data.menuTree))
        // 缓存按钮权限数据
        const buttonPermission = { ...response.data.menuButton }
        localStorage.setItem('buttonPermission', JSON.stringify(buttonPermission))
        commit('SET_MENU_BUTTON', response.data.menuButton)
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // 用户登出
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        // 清楚用户权限缓存
        localStorage.removeItem('menuPermission')
        localStorage.removeItem('buttonPermission')
        removeToken()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      // commit('SET_TOKEN', '')
      removeToken()
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

