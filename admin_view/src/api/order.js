import axios from 'axios'

axios.defaults.baseURL = 'http://localhost/v1/admin'

export default {
  list: function(queryInfo) {
    console.log(queryInfo)
    let res = axios.get(`/backend-order/${queryInfo.page}/${queryInfo.limit}`, { params: { query: queryInfo.search } })
    return res
  }
}
