import axios from 'axios'

axios.defaults.baseURL = 'http://localhost/v1/admin'

export default {
  list: function(catId, type) {
    let res = axios.get(`/backend-attr`, { params: { catId, type } })
    return res
  },
  add: function(data) {
    let res = axios.post(`/backend-attr`, data)
    return res
  },
  edit: function(data) {
    let res = axios.put(`/backend-attr`, data)
    return res
  },
  delete: function(id) {
    let res = axios.delete(`/backend-attr/${id}`)
    return res
  }
}
