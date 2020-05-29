<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import Ws from './util/websocket'

export default {
  name: 'app',
  components: {

  },
  data() {
    return {
    }
  },
  mounted() {
    if (window.sessionStorage.getItem('token')) {
      this.getUser()
      this.getCartCount()
    }
  },
  methods: {
    getUser() {
      this.axios.get('/user').then((res = {}) => {
        // console.log(res.data)
        this.$store.dispatch('saveUserName', res.data.username)
        if (this.$store.state.socket == null) {
          const socket = new Ws({
            url: 'ws://127.0.0.1:80/chat/' + res.data.username,
            reconnectTimes: 0
          })
          this.$store.dispatch('saveSocket', socket)
          socket.onmessage(data => {
            const rtn = JSON.parse(data)
            this.$store.dispatch('saveSocketMsg', rtn.content)
            if (this.$route.path.indexOf('detail') > 0) {
              this.$message.success('datil' + data)
            } else {
              this.$notify({
                title: rtn.talkId + '（点击跳转至聊天列表）',
                message: rtn.content,
                duration: 3000,
                position: 'bottom-right',
                onClick: this.toChat
              })
            }
          })
        }
      })
    },
    getCartCount() {
      this.axios.get('/carts/products/sum').then((res = 0) => {
        this.$store.dispatch('saveCartCount', res.data)
      })
    },
    toChat() {
      this.$router.push('/chat')
    }
  }
}
</script>

<style lang="scss">
@import './assets/scss/reset.scss';
@import './assets/scss/config.scss';
@import './assets/scss/button.scss';
@import './assets/scss/base.scss';
</style>
