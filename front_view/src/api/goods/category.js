import axios from 'axios'

axios.defaults.baseURL = 'http://localhost/v1/api'

export default {
  list: function(page, limit, level) {
    const res = axios.get(`/goods/${page}/${limit}/${level}`)
    return res
  },
  add: function(data) {
    const res = axios.post('/backend-category', data)
    return res
  },
  edit: function(data) {
    const res = axios.put('/backend-category', data)
    return res
  },
  delete: function(id) {
    const res = axios.delete(`/backend-category/${id}`)
    return res
  },
  getInfo: function(id) {
    const res = axios.get(`/backend-category/${id}`)
    return res
  }
}
