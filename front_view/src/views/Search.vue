<template>
  <div class="goods container">
    <div class="nav-subs">
      <div class="nav-sub-bgs"></div>
      <div class="nav-sub-wrappers">
        <div class="w">
          <ul class="nav-lists">
            <li>
              <router-link to="/">
                <a>首页</a>
              </router-link>
            </li>
            <li>
              <a class="active">搜索结果</a>
            </li>
            <li>
              <a v-if="searching === true">拼命搜索中...</a>
              <a v-if="searching === false">共为您找到 {{total}} 款商品信息</a>
            </li>
          </ul>
          <div></div>
        </div>
      </div>
    </div>

    <div class="nav">
      <div class="w">
        <a href="javascript:;" :class="{active:sortType===1}" @click="reset()">综合排序</a>
        <a href="javascript:;" @click="sortByPrice(1)" :class="{active:sortType===2}">价格从低到高</a>
        <a href="javascript:;" @click="sortByPrice(-1)" :class="{active:sortType===3}">价格从高到低</a>
        <a href="javascript:;" @click="requireList(4)" :class="{active:queryType===4}">查看发布</a>
        <a href="javascript:;" @click="requireList(5)" :class="{active:queryType===5}">查看求购</a>
        <div class="price-interval">
          <input type="number" class="input" placeholder="价格" v-model="min">
          <span style="margin: 0 5px"> - </span>
          <input type="number" placeholder="价格" v-model="max">
          <y-button text="确定" classStyle="main-btn" @btnClick="reset" style="margin-left: 10px;"></y-button>
        </div>
      </div>
    </div>

    <div v-loading="loading" element-loading-text="加载中..." style="min-height: 35vw;">
      <div  class="img-item" v-if="!noResult" >
        <!--商品-->
        <div class="goods-box w" v-if="queryType!=5">
          <mall-goods v-for="(item,i) in goods" :key="i" :msg="item"></mall-goods>
        </div>
        <div class="goods-box w" v-else>
          <require-item v-for="(item,i) in goods" :key="i" :msg="item"></require-item>
        </div>

        <el-pagination
          v-if="!noResult&&!error"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[8, 20, 40, 80]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
      <div class="no-info" v-if="noResult" >
        <div class="no-data">
          <img src="/imgs/no-search.png">
          <br> 抱歉！没有为您找到相关的商品
        </div>
        <section class="section">
          <y-shelf :title="recommendPanel.name">
            <div slot="content" class="recommend">
              <mall-goods :msg="item" v-for="(item,i) in recommendPanel.panelContents" :key="i"></mall-goods>
            </div>
          </y-shelf>
        </section>
      </div>
      <div class="no-info" v-if="error">
        <div class="no-data">
          <img src="/imgs/error.png">
          <br> 抱歉！出错了...
        </div>
        <section class="section">
          <y-shelf :title="recommendPanel.name">
            <div slot="content" class="recommend">
              <mall-goods :msg="item" v-for="(item,i) in recommendPanel.panelContents" :key="i"></mall-goods>
            </div>
          </y-shelf>
        </section>
      </div>
    </div>

    <el-dialog
      :title="requireMsg? requireMsg.username : ''"
      :visible.sync="requireDialog"
      width="50%" @close="emojiShow = false;$store.dispatch('saveRequireDialog', false)">
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

    <modal
    title="提示"
    sureText="查看购物车"
    btnType="1"
    modalType="middle"
    v-bind:showModal="showModal"
    v-on:submit="goToCart"
    v-on:cancel="dialogClose"
    >
    <template v-slot:body>
      <p>商品添加成功！</p>
    </template>
  </modal>
  </div>
</template>

<script>
import mallGoods from '../components/mallGoods'
import YButton from '../components/YButton'
import YShelf from '../components/shelf'
import requireItem from '../components/requireItem'
import VEmojiPicker from 'v-emoji-picker'
import { mapState } from 'vuex'
import Modal from './../components/Modal'
export default {
  data() {
    return {
      goods: [],
      noResult: false,
      error: false,
      min: '',
      max: '',
      loading: true,
      searching: true,
      timer: null,
      sortType: 1,
      windowHeight: null,
      windowWidth: null,
      sort: '',
      recommendPanel: [],
      currentPage: 1,
      pageSize: 20,
      total: 0,
      queryType: 4,
      chatDialog: false,
      emojiShow: false,
      messageContent: '',
      chatList: []
    }
  },
  computed: {
    key() { return this.$route.query.keyword },
    ...mapState(['socket', 'username', 'socketMsg', 'requireMsg', 'showModal', 'requireDialog'])
  },
  watch: {
    key() {
      this._getSearch()
    }
    // ,
    // requireMsg() {
    //   this.dialogShow()
    // }
  },
  methods: {
    handleSizeChange (val) {
      this.pageSize = val
      this._getSearch()
      this.loading = true
    },
    handleCurrentChange (val) {
      this.currentPage = val
      this._getSearch()
      this.loading = true
    },
    _getSearch () {
      console.log(this.key)
      const params = {
        key: this.key,
        size: this.pageSize,
        page: this.currentPage,
        sort: this.sortType,
        priceGt: this.min,
        priceLte: this.max
      }
      const url = this.queryType === 5 ? '/require/search' : '/goods/search'
      this.axios.post(url, params).then(res => {
        this.goods = res.list
        this.total = res.total
        this.noResult = false
        if (this.total === 0) {
          this.noResult = true
        }
        this.error = false
        this.loading = false
        this.searching = false
      }).catch(() => {
        this.error = true
        this.loading = false
        this.searching = false
      })
    },
    // 默认排序
    reset () {
      this.sortType = 1
      this.sort = ''
      this.currentPage = 1
      this.loading = true
      this._getSearch()
    },
    // 价格排序
    sortByPrice (v) {
      v === 1 ? this.sortType = 2 : this.sortType = 3
      this.sort = v
      this.currentPage = 1
      this.loading = true
      this._getSearch()
    },
    // 查看求购
    requireList(v) {
      this.queryType = v
      this._getSearch()
    },
    sendMsg() {
      // this.$message.success(this.messageContent)
      if (!this.messageContent) { this.$message.info('请输入内容'); return }
      const data = { talkId: this.username, sendId: this.requireMsg.username, content: this.messageContent, gmtCreate: this.now, guserId: this.requireMsg.username, puchaserId: this.username }
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
      this.axios.get('/chat/-1/-1', { params: { username: this.username, receiver: this.requireMsg.username } }).then(res => {
        this.chatList = res.list
        this.$nextTick(() => {
          const div = document.getElementById('chat-content')
          div.scrollTop = div.scrollHeight
        })
      })
    },
    dialogShow() {
      if (!this.username) { this.$router.push('/login'); return }
      this.chatDialog = true
      this.getChatList()
      // this.$nextTick(() => {
      //   const div = document.getElementById('chat-content')
      //   div.scrollTop = div.scrollHeight
      // })
    },
    goToCart() {
      this.$store.dispatch('saveShowModal', false)
      this.$router.push('/cart')
    },
    dialogClose() {
      this.$store.dispatch('saveShowModal', false)
    }
  },
  created () {
  },
  mounted () {
    this.windowHeight = window.innerHeight
    this.windowWidth = window.innerWidth
    this.key = this.$route.query.key
    this._getSearch()
    // this.axios().then(res => {
    //   const data = res.result
    //   this.recommendPanel = data[0]
    // })
  },
  components: {
    mallGoods,
    YButton,
    YShelf,
    requireItem,
    VEmojiPicker,
    Modal
  }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../assets/scss/mixin";
  @import "../assets/scss/theme";

   .nav {
    height: 60px;
    line-height: 60px;
    > div {
      display: flex;
      align-items: center;
      a {
        padding: 0 15px;
        height: 100%;
        @extend %block-center;
        font-size: 12px;
        color: #999;
        &.active {
          color: #5683EA;
        }
        &:hover {
          color: #5683EA;
        }
      }
      input {
        @include wh(80px, 30px);
        border: 1px solid #ccc;
      }
      input + input {
        margin-left: 10px;
      }
    }
    .price-interval {
      padding: 0 15px;
      @extend %block-center;
      input[type=number] {
        border: 1px solid #ccc;
        text-align: center;
        background: none;
        border-radius: 5px;
      }
    }
  }

  .goods-box {
    > div {
      float: left;
      border: 1px solid #efefef;
    }
  }

  .nav-subs {
    position: relative;
    margin-top: 0px;
    z-index: 10;
    height: 90px;
    background: #f7f7f7;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .04);
    .nav-sub-wrappers {
      padding: 31px 0;
      height: 90px;
      position: relative;
      box-sizing: border-box;
    }
    .w {
      display: flex;
      justify-content: space-between;
    }
    .nav-lists {
      height: 28px;
      line-height: 28px;
      display: flex;
      align-items: center;
      height: 100%;
      li:first-child {
        padding-left: 0;
        a {
          padding-left: 10px;
        }
      }
      li {
        position: relative;
        float: left;
        padding-left: 2px;
        a {
          display: block;
          // cursor: default;
          padding: 0 10px;
          color: #666;
          &.active {
            font-weight: bold;
          }
        }
        a:hover {
          color: #5683EA;
        }
      }
      li:before {
        content: ' ';
        position: absolute;
        left: 0;
        top: 13px;
        width: 2px;
        height: 2px;
        background: #bdbdbd;
      }
    }
  }

  .no-info {
    padding: 100px 0;
    text-align: center;
    font-size: 30px;
    display: flex;
    flex-direction: column;
    .no-data{
      align-self: center;
    }
  }

  .section {
    padding-top: 8vw;
    margin-bottom: -5vw;
    width: 1218px;
    align-self: center;
  }

  @media (min-width: 1px) {
    .nav-subs .nav-sub-wrappers:after {
      display: block;
    }
  }

  .recommend {
    display: flex;
    > div {
      flex: 1;
      width: 25%;
    }
  }

  .img-item{
    display: flex;
    flex-direction: column;
  }

  .el-pagination{
    align-self: flex-end;
    margin: 3vw 10vw 2vw;
  }

</style>
