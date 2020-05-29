<template>
  <div class="detail">
    <product-param v-bind:title="product.goodsName"></product-param>
    <div class="wrapper">
      <div class="container clearfix">
        <div class="swiper">
          <swiper :options="swiperOption" v-if="id==940">
              <swiper-slide><img src="/imgs/detail/phone-1.jpg" alt=""></swiper-slide>
              <swiper-slide><img src="/imgs/detail/phone-2.jpg" alt=""></swiper-slide>
              <swiper-slide><img src="/imgs/detail/phone-3.jpg" alt=""></swiper-slide>
              <swiper-slide><img src="/imgs/detail/phone-4.jpg" alt=""></swiper-slide>
              <!-- Optional controls -->
              <div class="swiper-pagination"  slot="pagination"></div>
          </swiper>
          <swiper :options="swiperOption" v-else>
              <!-- <swiper-slide><img src="/imgs/detail/phone-1.jpg" alt=""></swiper-slide>
              <swiper-slide><img src="/imgs/detail/phone-2.jpg" alt=""></swiper-slide>
              <swiper-slide><img src="/imgs/detail/phone-3.jpg" alt=""></swiper-slide>
              <swiper-slide><img src="/imgs/detail/phone-4.jpg" alt=""></swiper-slide> -->
              <swiper-slide v-for="(item, i) in product.pics" :key="i"><img :src="item.picsSma" alt=""></swiper-slide>
              <!-- Optional controls -->
              <div class="swiper-pagination"  slot="pagination"></div>
          </swiper>
        </div>
        <div class="content">
          <h2 class="item-title">{{product.goodsName}}</h2>
          <p class="item-info">{{info}}</p>
          <div class="delivery">二手</div>
          <div class="item-price">{{product.goodsPrice}}元
            <!-- <span class="del">1999元</span> -->
            </div>
          <div class="line"></div>
          <div class="item-addr">
            <i class="icon-loc"></i>
            <div class="addr">{{ product.user ? product.user.address : '' }}</div>
            <div class="stock">{{ product.goodsNumber > 0 ? '有现货' : '无货' }}</div>
          </div>
          <div class="item-version clearfix">
            <h2>选择{{ manyAttr[0] ? manyAttr[0].attrName : '无属性'  }}</h2>
            <div class="phone fl" :class="{'checked':version==1}" @click="version=1">{{manyAttr[0] ? manyAttr[0].attrValue[0] : '无' }}</div>
            <!-- <div class="phone fr" :class="{'checked':version==2}" @click="version=2">4GB+64GB 移动4G</div> -->
          </div>
          <div class="item-color">
            <h2>选择{{ manyAttr[1] ? manyAttr[1].attrName : '无属性' }}</h2>
            <div class="phone checked">
              <span class="color"></span>
              {{manyAttr[1] ? manyAttr[1].attrValue[1] : '无'  }}
            </div>
          </div>
          <div class="item-total">
            <div class="phone-info clearfix">
              <div class="fl">{{product.name}} {{ manyAttr[0] ? manyAttr[0].attrValue[0] : ''  }} {{manyAttr[1] ? manyAttr[1].attrValue[1] : '' }}</div>
              <div class="fr">{{product.goodsPrice}}元</div>
            </div>
            <div class="phone-total">总计：{{product.goodsPrice}}元</div>
          </div>
          <div class="btn-group">
            <a href="javascript:;" class="btn btn-huge fl" @click="addCart">加入购物车</a>
            <a href="javascript:;" class="btn btn-huge fr" @click="dialogShow">联系商家</a>
          </div>
        </div>
      </div>
    </div>
    <div class="price-info">
      <div class="container clearfix">
        <h2>商品说明</h2>
        <el-card>
          <div class="desc clearfix" id="desc">
            <!-- <img src="/imgs/detail/item-price.jpeg" alt=""> -->
          </div>
        </el-card>
      </div>
    </div>

    <el-dialog
      :title="product.user.username"
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
            <!-- <div class='sendUser2'>
              <b>lzj</b>
            </div>
            <div class="botui-message-right">
              <div ondblclick="stopShake(this)" class="botui-message-content2 shake-constant shake-constant--hover'">
              消息内容2😃
              </div>
            </div> -->
        </div>
        <div  class="tool-bar" style="padding: 5px;">
          <VEmojiPicker @select="selectEmoji" v-if="emojiShow" class="emojiPicker"/>
          <img id="emoji" src="../assets/icon/emoji.png"  style="width: 26px;margin-left: 0px"  @click.stop="emojiShow = !emojiShow">
          <!-- <img @click="emojiShow = false" src="../assets/icon/picture.png" style="width: 23px;margin-left: 10px">
          <img @click="emojiShow = false" src="../assets/icon/shakeFalse.png" style="width: 22px;margin-left: 10px">
          <img @click="emojiShow = false" src="../assets/icon/clear.png" style="width: 23px;margin-left: 10px"> -->
          <span style="width:560px;display:inline-block;height:26px;border-sizing:border-box" @click="emojiShow = false"></span>
          <img src="../assets/icon/send.png" style="width: 24px;position: absolute;right: 20px" @click="sendMsg()">
        </div>
        <textarea @click="emojiShow = false" id="edit" class="edit" contenteditable="true" v-model="messageContent">
        </textarea>
      </div>
    </el-dialog>
    <service-bar></service-bar>
  </div>
</template>
<script>
import { swiper, swiperSlide } from 'vue-awesome-swiper'
import ProductParam from './../components/ProductParam'
import ServiceBar from './../components/ServiceBar'
import { mapState } from 'vuex'
import VEmojiPicker from 'v-emoji-picker'
// import $ from '../assets/js/jquery-vendor.js'
// import '../assets/js/jquery.emoji.js'

export default {
  name: 'detail',
  data() {
    return {
      id: this.$route.params.id, // 获取商品ID
      err: '',
      version: 1, // 商品版本切换
      product: {}, // 商品信息
      info: '',
      manyAttr: [],
      onlyAttr: [],
      emojiShow: false,
      swiperOption: {
        autoplay: true,
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        }
      },
      dialogVisible: false,
      messageContent: '',
      chatList: []
    }
  },
  computed: {
    ...mapState(['socket', 'username', 'socketMsg']),
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
  components: {
    swiper,
    swiperSlide,
    ProductParam,
    ServiceBar,
    VEmojiPicker
  },
  mounted() {
    this.getProductInfo()
    // this.socket.onmessage(data => {
    //   if (this.$route.path.indexOf('detail') > 0) {
    //     this.$message.success('datil' + data)
    //   } else {
    //     this.$message.success('login' + data)
    //     const rtn = JSON.parse(data)
    //     this.$notify({
    //       title: rtn.talkId,
    //       message: rtn.content,
    //       duration: 3000,
    //       position: 'bottom-right'
    //     })
    //   }
    // })
    // this.loadEmoji()
  },
  // watch: {
  //   socketMsg: {
  //     handler: function(val, oldval) {
  //       console.log('socketMsg')
  //     },
  //     deep: true, // 对象内部的属性监听，也叫深度监听
  //     immediate: true
  //   }
  // },
  methods: {
    getProductInfo() {
      this.axios.get(`/goods/${this.id}`).then((res) => {
        this.product = res.data
        if (res.data.goodsAttrList) {
          res.data.goodsAttrList.forEach(element => {
            if (element.type === 'only') {
              this.info = this.info + ' / ' + element.attrValue
              this.onlyAttr.push(element)
            } else {
              element.attrValue = element ? element.attrValue.split(',') : []
              this.manyAttr.push(element)
            }
          })
          this.info = this.info.substring(3, this.info.length)
        }
        this.$nextTick(() => {
          document.getElementById('desc').innerHTML = res.data.goodsIntroduce
        })
      })
    },
    addCart() {
      if (!this.username) { this.$router.push('/login'); return }
      this.axios.post('/carts', {
        productId: this.id,
        selected: true
      }).then((res = { cartProductVoList: 0 }) => {
        this.$store.dispatch('saveCartCount', res.cartTotalQuantity)
        this.$router.push('/cart')
      })
    },
    sendMsg() {
      // this.$message.success(this.messageContent)
      if (!this.messageContent) { this.$message.info('请输入内容'); return }
      const data = { talkId: this.username, sendId: this.product.user.username, content: this.messageContent, gmtCreate: this.now, guserId: this.product.user.username, puchaserId: this.username }
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
      this.axios.get('/chat/-1/-1', { params: { username: this.username, receiver: this.product.user.username } }).then(res => {
        this.chatList = res.list
        this.$nextTick(() => {
          const div = document.getElementById('chat-content')
          div.scrollTop = div.scrollHeight
        })
      })
    },
    dialogShow() {
      if (!this.username) { this.$router.push('/login'); return }
      this.dialogVisible = true
      this.getChatList()
      // this.$nextTick(() => {
      //   const div = document.getElementById('chat-content')
      //   div.scrollTop = div.scrollHeight
      // })
    }
    // loadEmoji() {
    //   $('#edit').emoji({
    //     button: '#emoji',
    //     showTab: true,
    //     animation: 'slide',
    //     icons: [{
    //       name: 'QQ表情',
    //       path: '../assets/img/qq/',
    //       maxNum: 154,
    //       file: '.gif'

    //     }, {
    //       name: '贴吧表情',
    //       path: '../assets/img/tieba/',
    //       maxNum: 50,
    //       file: '.jpg'
    //     }, {
    //       name: 'dog',
    //       path: '../assets/img/dog/',
    //       maxNum: 37,
    //       file: '.png'
    //     }, {
    //       name: '坏坏',
    //       path: '../assets/img/huai/',
    //       maxNum: 189,
    //       file: '.png'
    //     }, {
    //       name: '坏坏GIF',
    //       path: '../assets/img/huaiGif/',
    //       maxNum: 26,
    //       file: '.gif'
    //     }, {
    //       name: '微博',
    //       path: '../assets/img/weibo/',
    //       maxNum: 93,
    //       file: '.png'
    //     }, {
    //       name: '猥琐萌',
    //       path: '../assets/img/xiaoren/',
    //       maxNum: 186,
    //       file: '.gif'
    //     }]
    //   })
    // }
  },
  watch: {
    username() {
      this.getChatList()
    },
    socketMsg() {
      this.getChatList()
    }
  }
}
</script>
<style lang="scss">
  @import './../assets/scss/config.scss';
  @import './../assets/scss/mixin.scss';
  .detail{
    .wrapper{
      .swiper{
        float:left;
        width:642px;
        height:617px;
        margin-top:5px;
        img{
          width:642px;
          height:100%;
        }
      }
      .content{
        float:right;
        width:584px;
        margin-bottom: 25px;
        // height:870px;
        .item-title{
          font-size:28px;
          padding-top:30px;
          padding-bottom:16px;
          // height: 26px;
          // overflow:hidden
        }
        .btn-huge{
          width: 280px;
        }
        .item-info{
          font-size:14px;
          height: 36px;
        }
        .delivery{
          font-size:16px;
          color:#FF6700;
          margin-top:26px;
          margin-bottom:14px;
          height: 15px;
        }
        .item-price{
          font-size:20px;
          color:#FF6700;
          height: 19px;
          .del{
            font-size:16px;
            color:#999999;
            margin-left:10px;
            text-decoration:line-through;
          }
        }
        .line{
          height:0;
          margin-top:25px;
          margin-bottom:28px;
          border-top:1px solid #E5E5E5;
        }
        .item-addr{
          height:108px;
          background-color:#FAFAFA;
          border:1px solid #E5E5E5;
          box-sizing:border-box;
          padding-top:31px;
          padding-left:64px;
          font-size:14px;
          line-height:14px;
          position:relative;
          .icon-loc{
            position: absolute;
            top: 27px;
            left: 34px;
            @include bgImg(20px,20px,'/imgs/detail/icon-loc.png');
          }
          .addr{
            color:#666666;
          }
          .stock{
            margin-top:15px;
            color:#FF6700;
          }
        }
        .item-version,.item-color{
          margin-top:30px;
          h2{
            font-size:18px;
            margin-bottom:20px;
          }
        }
        .item-version,.item-color{
          .phone{
            width:287px;
            height:50px;
            line-height:50px;
            font-size:16px;
            color:#333333;
            border:1px solid #E5E5E5;
            box-sizing: border-box;
            text-align:center;
            cursor:pointer;
            span{
              color:#666666;
              margin-left:15px;
            }
            .color{
              display:inline-block;
              width:14px;
              height:14px;
              background-color:#666666;
            }
            &.checked{
              border:1px solid #FF6600;
              color:#FF6600;
            }
          }
        }
        .item-total{
          height: 108px;
          background: #FAFAFA;
          padding: 24px 33px 29px 30px;
          font-size: 14px;
          margin-top:50px;
          margin-bottom:30px;
          box-sizing: border-box;
          .phone-total{
            font-size: 24px;
            color: #FF6600;
            margin-top: 18px;
          }
        }
      }
    }
    .price-info{
      background-color:#F3F3F3;
      height:340px;
      h2{
        font-size:24px;
        color:#333333;
        padding-top:38px;
        margin-bottom:30px;
      }
    }
  }
  .el-dialog {
    .tool-bar{
      .emojiPicker{
        position: absolute;
        top: 75%;
        left: 2%;
      }
    }
    .chat-content {
      overflow-y: auto;
      background-color: rgb(230, 230, 230);
       background-image: none;
       max-height: 300px;
       height: 300px;
       margin-top: -30px;
       padding-top: 10px;
       .sendUser{
          margin-left: 15px;
        }

        .sendUser2{
          margin-right: 20px;
          text-align: right;
        }
       .botui-message-left{
            min-height: 45px;
            margin: 5px 0;
        }
        .botui-message-right{
            min-height: 45px;
            margin: 5px 0;
            text-align: right;
        }
        .botui-message-content{
            line-height: 1.5;
            padding: 10px 20px;
            border-radius: 15px;
            color: #595a5a;
            background-color: #ffffff;
            width: auto;
            max-width: 80%;
            display: inline-block;
            margin-left: 15px;
        }
        .botui-message-content-img{
            line-height: 1.5;
            padding: 10px 20px;
            border-radius: 15px;
            color: #595a5a;
            backgroimgund-color: #ebebeb;
            width: auto;
            max-width: 20%;
            display: inline-block;
            margin-left: 15px;
        }
        .botui-message-content2{
            line-height: 1.5;
            padding: 10px 20px;
            border-radius: 15px;
            color: #f7f8f8;
            background-color: #00a5ff;
            width: auto;
            max-width: 80%;
            display: inline-block;
            text-align: start;
            margin-right: 15px;
            /*float: right;*/
        }
        .botui-message-content2-img{
            line-height: 1.5;
            padding: 10px 20px;
            border-radius: 15px;
            color: #f7f8f8;
            width: auto;
            max-width: 20%;
            display: inline-block;
            text-align: start;
            margin-right: 15px;
            /*float: right;*/
        }
    }
    .edit{
      max-height: 100px;
      height: 100px;
      padding-top: 0px;
      padding-bottom: 0px;
      padding-left: 10px;
      padding-right: 10px;
      border-radius: 5px;
      border: 2px solid #e6e6e6;
      overflow-y: auto;
      width: 97%;
      resize:none;
    }
  }
</style>
