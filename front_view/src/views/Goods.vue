<template>
  <div class="goods">
    <order-header title="商品管理">
      <template v-slot:tip>
        <span>商品发布、商品发布列表</span>
      </template>
    </order-header>
    <router-view></router-view>
    <service-bar></service-bar>
    <nav-footer></nav-footer>
  </div>
</template>
<script>
import ServiceBar from './../components/ServiceBar'
import NavFooter from './../components/NavFooter'
import OrderHeader from './../components/OrderHeader'
export default {
  name: 'goods',
  data() {
    return {
      title: '',
      tip: ''
    }
  },
  components: {
    ServiceBar,
    NavFooter,
    OrderHeader
  }
}
</script>
