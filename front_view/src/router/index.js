import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Index from '../views/Index.vue'
import Product from '../views/Product.vue'
import Detail from '../views/Detail.vue'
import Cart from '../views/Cart.vue'
import Order from '../views/Order.vue'
import OrderList from '../views/OrderList.vue'
import OrderPay from '../views/OrderPay.vue'
import OrderConfirm from '../views/OrderConfirm.vue'
import Alipay from '../views/Alipay.vue'
import Login from '../views/Login.vue'
import User from '../views/User.vue'
import UserInfo from '../views/UserInfo.vue'
import UserAddress from '../views/UserAddress.vue'
import Register from '../views/Register.vue'
import Goods from '../views/Goods.vue'
import GoodsPublish from '../views/GoodsPublish.vue'
import GoodsList from '../views/GoodsList.vue'
import Chat from '../views/Chat.vue'
import Search from '../views/Search.vue'
import Require from '../views/Require.vue'
import RequireList from '../views/RequireList.vue'
import Forum from '../views/Forum.vue'
import ForumList from '../views/ForumList.vue'
import ForumDetail from '../views/ForumDetail.vue'
import ForumPublish from '../views/ForumPublish.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    redirect: '/index',
    children: [{
      path: '/index',
      name: 'index',
      component: Index
    }, {
      path: '/product/:id',
      name: 'product',
      component: Product
    }, {
      path: '/detail/:id',
      name: 'detail',
      component: Detail
    }, {
      path: '/search',
      name: 'search',
      component: Search
    }]
  }, {
    path: '/cart',
    name: 'Cart',
    component: Cart
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    // component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }, {
    path: '/order',
    name: 'order',
    component: Order,
    children: [{
      path: 'list',
      name: 'order-list',
      component: OrderList
    }, {
      path: 'confirm',
      name: 'order-confirm',
      component: OrderConfirm
    }, {
      path: 'pay',
      name: 'order-pay',
      component: OrderPay
    }, {
      path: 'alipay',
      name: 'alipay',
      component: Alipay
    }]
  },
  {
    path: '/user',
    name: 'user',
    component: User,
    children: [{
      path: 'info',
      name: 'user-info',
      component: UserInfo
    }, {
      path: 'address',
      name: 'user-address',
      component: UserAddress
    }]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    // component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/goods',
    name: 'Goods',
    component: Goods,
    children: [{
      path: 'list',
      name: 'goods-list',
      component: GoodsList
    }, {
      path: 'publish',
      name: 'goods-publish',
      component: GoodsPublish
    }]
  },
  {
    path: '/chat',
    name: 'Chat',
    component: Chat
  },
  {
    path: '/require',
    name: 'Require',
    component: Require,
    children: [{
      path: 'list',
      name: 'require-list',
      component: RequireList
    }]
  },
  {
    path: '/forum',
    name: 'Forum',
    component: Forum,
    children: [{
      path: 'list',
      name: 'forum-list',
      component: ForumList
    }, {
      path: 'publish',
      name: 'forum-publish',
      component: ForumPublish
    }, {
      path: 'detail',
      name: 'forum-detail',
      component: ForumDetail
    }]
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 来源链接
  // next 表示放行
  if (to.path === '/login' || to.path === '/register' || to.path === '/index' || to.path === '/forum/list' || to.path.indexOf('detail') > 0 || to.path === '/search') return next()
  const token = window.sessionStorage.getItem('token')
  if (!token) return next('login')
  next()
})

export default router
