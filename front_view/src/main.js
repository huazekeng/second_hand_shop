import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import vueaxios from 'vue-axios'
import env from './env.js'
import VueLazyLoad from 'vue-lazyload'
import VueCookie from 'vue-cookie'
import store from './store'
// import {
//   Message, Cascader, Form, FormItem, Table, TableColumn, Row, Col,
//   Button, Input, Pagination
// } from 'element-ui'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Nprogress from 'nprogress'
import 'nprogress/nprogress.css'
import VueQuillEditor from 'vue-quill-editor'
import Quill from 'quill'

import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

import './assets/scss/element-variables.scss'

Vue.use(VueQuillEditor)
Vue.prototype.$quill = Quill
axios.defaults.baseURL = env.baseURL
console.log(env.baseURL)
axios.defaults.timeout = 8000

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
// 接口拦截
axios.interceptors.response.use(function(response) {
  const res = response.data
  const path = location.hash
  Nprogress.done()
  if (res.code === 20000) {
    return res.data
  } else if (res.code === 40002) {
    if (path !== '#/index') {
      window.location.href = '/#/login'
    }
    return Promise.reject(res)
  } else if (res.code === 40007) {
    if (isRefreshing) {
      refreshTokenRequst()
    }
    // console.log('token 過期')
    isRefreshing = false
    // 这个Promise函数很关键
    const retryOriginalRequest = new Promise((resolve) => {
      addSubscriber(() => {
        resolve(request(response.config))
      })
    })
    return retryOriginalRequest
  } else {
    ElementUI.Message.error(res.message)
    return Promise.reject(res)
  }
}, (error) => {
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
  ElementUI.Message.error(error.message)
  return Promise.reject(error)
})
Vue.config.productionTip = false
Vue.prototype.$message = ElementUI.Message

Vue.use(vueaxios, axios)
Vue.use(VueLazyLoad, {
  loading: '/imgs/loading-svg/loading-bars.svg'
})
Vue.use(VueCookie)
Vue.use(ElementUI)
// Vue.use(Cascader)
// Vue.component(Form.name, Form)
// Vue.component(FormItem.name, FormItem)
// Vue.component(Table.name, Table)
// Vue.component(TableColumn.name, TableColumn)
// Vue.component(Row.name, Row)
// Vue.component(Col.name, Col)
// Vue.component(Input.name, Input)
// Vue.component(Button.name, Button)
// Vue.component(Pagination.name, Pagination)
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
  store,
  router,
  render: h => h(App)
}).$mount('#app')
