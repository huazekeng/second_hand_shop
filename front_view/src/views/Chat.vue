<template>
  <div class="goods">
    <order-header title="消息管理">
      <template v-slot:tip>
        <span>站内消息列表</span>
      </template>
    </order-header>
    <div class="container" style="padding-top:25px;">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom:15px;">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>消息管理</el-breadcrumb-item>
      </el-breadcrumb>
      <div v-for="item in lchatList" :key="item.id">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-size:12px;line-height:12px;display:inline-block;color:#999999">与 <i><strong style="color:black;font-size:14px;">{{item.puchaserId == username ? item.guserId : item.puchaserId }}</strong></i> 的最新消息  {{ gmtCreate }}</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="dialogShow(item.puchaserId == username ? item.guserId : item.puchaserId, item.puchaserId)">回复</el-button>
          </div>
          <div class="text item">
            {{item.content}}
          </div>
        </el-card>
        <el-divider></el-divider>
      </div>
    </div>

    <el-dialog
      :title="receiver"
      :visible.sync="dialogVisible"
      width="50%" @close="emojiShow = false">
      <div>
        <div id="chat-content" class="chat-content" @click="this.emojiShow = false">
            <div class="chat-item" v-for="(item, index) in chatList" :key="index">
              <div :class="[  item.talkId==username ? 'sendUser' : 'sendUser2']">
                <b> {{ item.talkId + "(" + item.gmtCreate + ")"}} </b>
              </div>
              <div :class="[  item.talkId==username ? 'botui-message-left' : 'botui-message-right']">
                <div :class="[  item.talkId==username ? 'botui-message-content' : 'botui-message-content2','shake-constant','shake-constant--hover']">
                {{item.content}}
                </div>
              </div>
            </div>
        </div>
        <div  class="tool-bar" style="padding: 5px;">
          <VEmojiPicker @select="selectEmoji" v-if="emojiShow" class="emojiPicker"/>
          <img id="emoji" src="../assets/icon/emoji.png"  style="width: 26px;margin-left: 0px"  @click.stop="emojiShow = !emojiShow">
          <span style="width:560px;display:inline-block;height:26px;border-sizing:border-box" @click="emojiShow = false"></span>
          <img src="../assets/icon/send.png" style="width: 24px;position: absolute;right: 20px" @click="sendMsg()">
        </div>
        <textarea @click="emojiShow = false" id="edit" class="edit" contenteditable="true" v-model="messageContent">
        </textarea>
      </div>
    </el-dialog>
    <service-bar></service-bar>
    <nav-footer></nav-footer>
  </div>
</template>
<script>
import ServiceBar from './../components/ServiceBar'
import NavFooter from './../components/NavFooter'
import OrderHeader from './../components/OrderHeader'
import { mapState } from 'vuex'
import VEmojiPicker from 'v-emoji-picker'
export default {
  name: 'chat',
  data() {
    return {
      title: '',
      tip: '',
      lchatList: [],
      dialogVisible: false,
      messageContent: '',
      chatList: [],
      receiver: '',
      emojiShow: false,
      puId: ''
    }
  },
  components: {
    ServiceBar,
    NavFooter,
    OrderHeader,
    VEmojiPicker
  },
  computed: {
    ...mapState(['username', 'socket', 'socketMsg']),
    now() {
      const date = new Date()
      const Y = date.getFullYear()
      const M = ((date.getMonth() + 1) + '').padStart(2, 0)
      const d = (date.getDay() + '').padStart(2, 0)
      const h = (date.getHours() + '').padStart(2, 0)
      const mi = (date.getMinutes() + '').padStart(2, 0)
      const s = (date.getSeconds() + '').padStart(2, 0)
      return `${Y}-${M}-${d} ${h}:${mi}:${s}`
    }
  },
  watch: {
    username() {
      this.getLChatList()
    },
    socketMsg() {
      this.getChatList()
    }
  },
  mounted() {
    this.getLChatList()
  },
  methods: {
    getLChatList() {
      this.axios.get('/chat/-1/-1', { params: { username: this.username } }).then(res => {
        this.lchatList = res.list
      })
    },
    sendMsg() {
      // this.$message.success(this.messageContent)
      if (!this.messageContent) { this.$message.info('请输入内容'); return }
      const data = { talkId: this.username, sendId: this.receiver, content: this.messageContent, gmtCreate: this.now, guserId: this.username === this.puId ? this.receiver : this.username, puchaserId: this.username === this.puId ? this.username : this.receiver }
      this.socket.send(data)
      this.chatList.push(data)
      this.messageContent = ''
      this.$nextTick(() => {
        const div = document.getElementById('chat-content')
        div.scrollTop = div.scrollHeight
      })
    },
    selectEmoji(emoji) {
      console.log(emoji)
      this.emojiShow = false
      this.messageContent = this.messageContent + emoji.data
    },
    getChatList() {
      this.axios.get('/chat/-1/-1', { params: { username: this.username, receiver: this.receiver } }).then(res => {
        this.chatList = res.list
        this.$nextTick(() => {
          const div = document.getElementById('chat-content')
          div.scrollTop = div.scrollHeight
        })
      })
    },
    dialogShow(puchaserId, puId) {
      this.dialogVisible = true
      this.receiver = puchaserId
      this.puId = puId
      this.getChatList(puchaserId)
      // this.$nextTick(() => {
      //   const div = document.getElementById('chat-content')
      //   div.scrollTop = div.scrollHeight
      // })
    }
  }
}
</script>
