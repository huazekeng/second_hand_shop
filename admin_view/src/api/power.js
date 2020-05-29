import axios from 'axios'

axios.defaults.baseURL = 'http://localhost/v1/admin'

export default {
  list: function() {
    let res = axios.get('/backend-permission')
    return res
  }
}
