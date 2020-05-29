<template>
  <div>
    <router-view></router-view>
    <service-bar></service-bar>
    <nav-footer></nav-footer>
  </div>
</template>
<script>
import ServiceBar from './../components/ServiceBar'
import NavFooter from './../components/NavFooter'
export default {
  name: 'user',
  data() {
    return {
      title: '',
      tip: ''
    }
  },
  components: {
    ServiceBar,
    NavFooter
  }
}
</script>
