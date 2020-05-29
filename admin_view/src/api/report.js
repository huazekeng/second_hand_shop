import axios from 'axios'
// import qs from 'qs'

axios.defaults.baseURL = 'http://localhost/v1/admin'

export default {
  report: function(type) {
    const res = axios.get(`/backend-report/${type}`)
    return res
  }
}
