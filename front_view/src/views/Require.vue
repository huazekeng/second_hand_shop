<template>
  <div class="goods">
    <order-header title="求购管理">
      <template v-slot:tip>
        <span>发布求购、求购列表，只能存在5条有效求购！！!</span>
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
  name: 'require',
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
