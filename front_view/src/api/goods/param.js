import axios from 'axios'

axios.defaults.baseURL = 'http://localhost/api'

export default {
  list: function(catId, type) {
    const res = axios.get('/goods/attr', { params: { catId, type } })
    return res
  },
  add: function(data) {
    const res = axios.post('/backend-attr', data)
    return res
  },
  edit: function(data) {
    const res = axios.put('/backend-attr', data)
    return res
  },
  delete: function(id) {
    const res = axios.delete(`/backend-attr/${id}`)
    return res
  }
}
