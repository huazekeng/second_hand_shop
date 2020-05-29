import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/css/global.css'
import './assets/fonts/iconfont.css'
import axios from 'axios'
import qs from 'qs'
import { Message } from 'element-ui'
import TreeTable from 'vue-table-with-tree-grid'
import Nprogress from 'nprogress'
import 'nprogress/nprogress.css'
import VueQuillEditor from 'vue-quill-editor'
import Quill from 'quill'

import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

Vue.use(VueQuillEditor)
Vue.prototype.$quill = Quill
Vue.component('tree-table', TreeTable)
Vue.config.productionTip = false
axios.defaults.baseURL = 'http://localhost/v1/admin'
Vue.prototype.$http = axios
Vue.prototype.$qs = qs
let isRefreshing = true
axios.interceptors.request.use(config => {
  config.headers.Authorization = window.sessionStorage.getItem('token')
  Nprogress.start()
  return config
})

function request(options) {
  return axios.request(options)
}

function refreshTokenRequst() {
  const refreshToken = window.sessionStorage.getItem('refresh_token')
  const token = window.sessionStorage.getItem('token')
  const url = '/token/refresh?token=' + token + '&refresh_token=' + refreshToken
  axios.get(url).then(res => {
    isRefreshing = true
    if (res.data.code === 20000) {
      window.sessionStorage.setItem('token', res.data.data.token)
      onAccessTokenFetched()
    } else {
      console.log('refreshToken 過期')
      // 通知服务器清楚token
      axios.get('/logout?refresh_token=' + refreshToken).then(tokenRes => {
        window.sessionStorage.clear()
        router.push('/login')
      })
    }
  })
}

let subscribers = []

function onAccessTokenFetched() {
  subscribers.forEach((callback) => {
    callback()
  })
  subscribers = []
}

function addSubscriber(callback) {
  subscribers.push(callback)
}

axios.interceptors.response.use(response => {
  // console.log(response)
  if (response.data.code === 40007) { // token 過期處理，刷新token
    // 刷新token的函数,这需要添加一个开关，防止重复请求
    if (isRefreshing) {
      refreshTokenRequst()
    }
    console.log('token 過期')
    isRefreshing = false
    // 这个Promise函数很关键
    const retryOriginalRequest = new Promise((resolve) => {
      addSubscriber(() => {
        resolve(request(response.config))
      })
    })
    return retryOriginalRequest
    // console.log('token 過期')
    // const refreshToken = window.sessionStorage.getItem('refresh_token')
    // const token = window.sessionStorage.getItem('token')
    // const url = '/token/refresh?token=' + token + '&refresh_token=' + refreshToken
    // let promisefresh = new Promise(function(resolve, reject) {
    //   window.sessionStorage.setItem('refresh_token', true)
    //   // 刷新token
    //   axios.get(url).then(res => {
    //     if (res.data.code === 20000) {
    //       response.headers.Authorization = `Bearer ${res.data.data.token}`
    //       window.sessionStorage.setItem('token', res.data.data.token)
    //       axios.request(response.config).then(newRes => { // 重新发起资源请求
    //         resolve(newRes) // 重新解析资源返回
    //       })
    //     } else {
    //       // 通知服务器清楚token
    //       axios.get('/logout?refresh_token=' + refreshToken).then(tokenRes => {
    //         window.sessionStorage.clear()
    //         router.push('/login')
    //       })
    //     }
    //   })
    // })
    // return promisefresh
  }
  // console.log(response)
  Nprogress.done()
  return response
}, error => {
  if (error && error.response) {
    switch (error.response.status) {
      case 400: error.message = '请求错误(400)'; break
      case 401: error.message = '未授权，请重新登录(401)'; break
      case 403: error.message = '拒绝访问(403)'; break
      case 404: error.message = '请求出错(404)'; break
      case 408: error.message = '请求超时(408)'; break
      case 500: error.message = '服务器错误(500)'; break
      case 501: error.message = '服务未实现(501)'; break
      case 502: error.message = '网络错误(502)'; break
      case 503: error.message = '服务不可用(503)'; break
      case 504: error.message = '网络超时(504)'; break
      case 505: error.message = 'HTTP版本不受支持(505)'; break
      default: error.message = `连接出错(${error.response.status})!`
    }
  } else {
    error.message = '连接服务器失败!'
  }
  Message({
    'message': error.message,
    'type': 'error'
  })
  return Promise.reject(error)
})

Vue.filter('dateFormat', function(dateStr) {
  const date = new Date(dateStr)
  const Y = date.getFullYear()
  const M = ((date.getMonth() + 1) + '').padStart(2, 0)
  const d = (date.getDay() + '').padStart(2, 0)
  const h = (date.getHours() + '').padStart(2, 0)
  const mi = (date.getMinutes() + '').padStart(2, 0)
  const s = (date.getSeconds() + '').padStart(2, 0)
  return `${Y}-${M}-${d} ${h}:${mi}:${s}`
})

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
