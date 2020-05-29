import axios from 'axios'

axios.defaults.baseURL = 'http://localhost/v1/admin'

export default {
  list: function(page, limit, level) {
    let res = axios.get(`/backend-category/${page}/${limit}/${level}`)
    return res
  },
  add: function(data) {
    let res = axios.post('/backend-category', data)
    return res
  },
  edit: function(data) {
    let res = axios.put('/backend-category', data)
    return res
  },
  delete: function(id) {
    let res = axios.delete(`/backend-category/${id}`)
    return res
  },
  getInfo: function(id) {
    let res = axios.get(`/backend-category/${id}`)
    return res
  }
}
