/**
 * 商城Vuex-actions
 */
export default {
  saveUserName(context, username) {
    context.commit('saveUserName', username)
  },
  saveCartCount(context, count) {
    context.commit('saveCartCount', count)
  },
  saveSocket(context, socket) {
    context.commit('saveSocket', socket)
  },
  saveSocketMsg(context, socketMsg) {
    context.commit('saveSocketMsg', socketMsg)
  },
  saveRequireMsg(context, requireMsg) {
    context.commit('saveRequireMsg', requireMsg)
  },
  saveShowModal(context, showModal) {
    context.commit('saveShowModal', showModal)
  },
  saveRequireDialog(context, requireDialog) {
    context.commit('saveRequireDialog', requireDialog)
  }
}
