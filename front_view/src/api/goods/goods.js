import axios from 'axios'

axios.defaults.baseURL = 'http://localhost/api'

export default {
  list: function(queryInfo) {
    const res = axios.get(`/backend-goods/${queryInfo.currPage}/${queryInfo.pageSize}`, { params: { query: queryInfo.search } })
    return res
  },
  add: function(data) {
    const res = axios.post('/goods', data)
    return res
  },
  edit: function(data) {
    const res = axios.put('/backend-goods', data)
    return res
  },
  delete: function(id) {
    const res = axios.delete(`/backend-goods/${id}`)
    return res
  },
  checkName: function(name) {
    const res = axios.get('/goods/checkName', { params: { name } })
    return res
  }
}
