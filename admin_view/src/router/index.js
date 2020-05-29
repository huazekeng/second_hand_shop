import Vue from 'vue'
import VueRouter from 'vue-router'
// import Login from '../components/login.vue'
const Login = () => import(/* webpackChunkName: "login_home_welcome" */ '../components/login.vue')
const Home = () => import(/* webpackChunkName: "login_home_welcome" */ '../components/home.vue')
const Welcome = () => import(/* webpackChunkName: "login_home_welcome" */ '../components/welcome.vue')

// import Home from '../components/home.vue'
// import Welcome from '../components/welcome.vue'
// import User from '../components/user/list.vue'
// import Rights from '../components/power/list.vue'
// import Roles from '../components/role/list.vue'
const User = () => import(/* webpackChunkName: "user_right_role" */ '../components/user/list.vue')
const Rights = () => import(/* webpackChunkName: "user_right_role" */ '../components/power/list.vue')
const Roles = () => import(/* webpackChunkName: "user_right_role" */ '../components/role/list.vue')
const FUser = () => import(/* webpackChunkName: "user_right_role" */ '../components/user/flist.vue')

// import Category from '../components/goods/category.vue'
// import Param from '../components/goods/param.vue'
const Category = () => import(/* webpackChunkName: "category_param" */ '../components/goods/category.vue')
const Param = () => import(/* webpackChunkName: "category_param" */ '../components/goods/param.vue')

// import Goods from '../components/goods/goodList.vue'
// import GoodsAdd from '../components/goods/goodsAdd.vue'
const Goods = () => import(/* webpackChunkName: "good_add" */ '../components/goods/goodList.vue')
const GoodsAdd = () => import(/* webpackChunkName: "good_add" */ '../components/goods/goodsAdd.vue')

// import Order from '../components/order/list.vue'
// import Report from '../components/report/report.vue'
const Order = () => import(/* webpackChunkName: "order_report" */ '../components/order/list.vue')
const Report = () => import(/* webpackChunkName: "order_report" */ '../components/report/report.vue')

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  { path: '/home',
    component: Home,
    redirect: '/welcome',
    children: [
      { path: '/welcome', component: Welcome },
      { path: '/users', component: User },
      { path: '/rights', component: Rights },
      { path: '/roles', component: Roles },
      { path: '/categorys', component: Category },
      { path: '/params', component: Param },
      { path: '/goods', component: Goods },
      { path: '/goods/add', component: GoodsAdd },
      { path: '/orders', component: Order },
      { path: '/reports', component: Report },
      { path: '/fusers', component: FUser }
    ] }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 来源链接
  // next 表示放行
  if (to.path === '/login') return next()
  const token = window.sessionStorage.getItem('token')
  if (!token) return next('login')
  next()
})

export default router
