import axios from 'axios'

axios.defaults.baseURL = 'http://localhost/v1/admin'

export default {
  list: function(queryInfo) {
    let res = axios.get(`/backend-goods/${queryInfo.currPage}/${queryInfo.pageSize}`, { params: { query: queryInfo.search } })
    return res
  },
  add: function(data) {
    let res = axios.post(`/backend-goods`, data)
    return res
  },
  edit: function(data) {
    let res = axios.put(`/backend-goods`, data)
    return res
  },
  delete: function(id) {
    let res = axios.delete(`/backend-goods/${id}`)
    return res
  },
  checkName: function(name) {
    let res = axios.get(`/backend-goods/checkName`, { params: { name } })
    return res
  }
}
