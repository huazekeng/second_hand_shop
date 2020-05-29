import Vue from 'vue'
import Vuex from 'vuex'
import mutations from './mutations'
import actions from './action'
Vue.use(Vuex)

const state = {
  username: '', // 登录用0
  cartCount: 0, // 购物车商品数量
  socket: null, // socket
  socketMsg: '',
  requireMsg: '',
  showModal: false,
  requireDialog: false
}
export default new Vuex.Store({
  state,
  mutations,
  actions
})
