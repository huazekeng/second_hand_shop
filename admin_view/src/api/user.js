import axios from 'axios'

axios.defaults.baseURL = 'http://localhost/v1/admin'

export default {
  deleteUser: function(id) {
    let res = axios.delete('/backend-manager/' + id)
    return res
  },
  userRole: function(id) {
    let res = axios.get(`/backend-manager/role/${id}`)
    return res
  },
  allotRoles: function(id, roleIds) {
    let res = axios.put(`/backend-manager/allot_role/${id}`, { keys: roleIds })
    return res
  }
}
