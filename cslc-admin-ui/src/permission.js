import router from './router'
import store from './store'
import generateMenus from '@/utils/menus'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist
// 路由之前拦截
router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()
  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      // TODO 授权过期后重复打开登陆弹窗
      const hasGetUserInfo = store.getters.name
      if (hasGetUserInfo) {
        next()
      } else {
        try {
          // 拉取用户信息
          await store.dispatch('user/getInfo')
          // 用户缓存菜单权限
          const menusPermission = localStorage.getItem('menuPermission')
          // 用户缓存按钮权限
          const buttonPermission = localStorage.getItem('buttonPermission')
          // 判断是否需要重新获取数据
          if (menusPermission === null) {
            // 初始化用户权限
            await store.dispatch('user/getPermission')
          } else {
            store.state.user.menus = generateMenus(JSON.parse(menusPermission))
            store.state.user.menuButton = JSON.parse(buttonPermission)
          }
          // 动态添加可访问路由表
          router.addRoutes(store.state.user.menus)
          next({ ...to, replace: true })
        } catch (error) {
          console.log('router.beforeEach() error: ' + error)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
