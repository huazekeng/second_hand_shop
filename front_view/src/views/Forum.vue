<template>
  <div class="forum">
    <order-header title="站内论坛">
      <template v-slot:tip>
        <span>发帖、回帖（需要登录，游客只能看帖）</span>
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
  name: 'forum',
  data() {
    return {
    }
  },
  components: {
    ServiceBar,
    NavFooter,
    OrderHeader
  },
  computed: {
  },
  watch: {
  },
  mounted() {
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>
</style>
