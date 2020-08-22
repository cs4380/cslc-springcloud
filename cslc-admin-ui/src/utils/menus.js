/**
 * menus 菜单工具
 */

import Layout from '@/layout'

/**
 * 后台返回动态菜单数据, 转换为路由（vue-router）可识别的json数据
 * * 字符串路径转为组件对象
 * @param {JSON} menus
 */
export default function generateMenus(menus) {
  // vue-router的菜单配置数据
  for (const menu of menus) {
    // 菜单类型(0:目录|1:菜单)，目录则转换为布局组件
    if (menu.type === 0) {
      menu.component = Layout
      tree(menu.children)
    } else {
      const componentPath = menu.component
      menu.component = (resolve) => require([`@/views${componentPath}`], resolve)
    }
  }
  menus.push({ path: '*', redirect: '/404', hidden: true })
  return menus
}
/**
 * 菜单树
 * @param {*} childrens
 */
function tree(childrens) {
  for (const menu of childrens) {
    // 菜单类型(0:目录|1:菜单)，目录则转换为布局组件
    if (menu.type === 0) {
      menu.component = Layout
      tree(menu.children)
    } else {
      const componentPath = menu.component
      menu.component = (resolve) => require([`@/views${componentPath}`], resolve)
    }
  }
}
