import axios from 'axios'
// import qs from 'qs'

axios.defaults.baseURL = 'http://localhost/v1/admin'

export default {
  list: function() {
    const res = axios.get('/backend-role')
    return res
  },
  addRole: function(res) {
    const res2 = axios.post('/backend-role', res)
    return res2
  },
  editRole: function(res) {
    const res2 = axios.put('/backend-role', res)
    return res2
  },
  delete: function(id) {
    const res2 = axios.delete('/backend-role/' + id)
    return res2
  },
  deletePermissionByRole: function(roleId, psId) {
    const data = { roleId, psId }
    const res2 = axios.delete('/backend-role/cancel_right', { params: data })
    return res2
  },
  allotRight: function(currentRoId, pkeys) {
    // const keys = pkeys.join(',')
    const res2 = axios.put(`/backend-role/allot_right/${currentRoId}`, { keys: pkeys })
    return res2
  }
}
