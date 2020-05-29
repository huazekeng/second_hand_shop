/**
 * 商城Vuex-mutations
 */
export default {
  saveUserName(state, username) {
    state.username = username
  },
  saveCartCount(state, count) {
    state.cartCount = count
  },
  saveSocket(state, socket) {
    state.socket = socket
  },
  saveSocketMsg(state, socketMsg) {
    state.socketMsg = socketMsg
  },
  saveRequireMsg(state, requireMsg) {
    state.requireMsg = requireMsg
  },
  saveShowModal(state, showModal) {
    state.showModal = showModal
  },
  saveRequireDialog(state, requireDialog) {
    state.requireDialog = requireDialog
  }
}
