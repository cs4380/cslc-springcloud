const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  portrait: state => state.user.portrait,
  name: state => state.user.name,
  menus: state => state.user.menus
}
export default getters
