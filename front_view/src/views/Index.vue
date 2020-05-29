<template>
    <div class="index">
      <div class="container">
        <div class="swiper-box">
          <div class="nav-menu">
            <ul class="menu-wrap">
              <li class="menu-item" v-for="item1 in menuList" :key="item1.catId">
                <a href="javascript:;">{{ item1.catName }}</a>
                <div class="children">
                  <ul v-for="(item,i) in item1.goods" v-bind:key="i">
                    <li v-for="(sub,j) in item" v-bind:key="j">
                      <a v-bind:href="sub?'/#/detail/'+sub.goodsId:''">
                        <img v-bind:src="sub?sub.pics[0].picsMid:'/imgs/item-box-1.png'" alt="">
                        {{sub?sub.name:'小米9'}}
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
              <!-- <li class="menu-item">
                <a href="javascript:;">电视 盒子</a>
              </li>
              <li class="menu-item">
                <a href="javascript:;">笔记本 平板</a>
              </li>
              <li class="menu-item">
                <a href="javascript:;">家电 插线板</a>
              </li>
              <li class="menu-item">
                <a href="javascript:;">出行 穿戴</a>
              </li>
              <li class="menu-item">
                <a href="javascript:;">智能 路由器</a>
              </li>
              <li class="menu-item">
                <a href="javascript:;">电源 配件</a>
              </li>
              <li class="menu-item">
                <a href="javascript:;">生活 箱包</a>
              </li> -->
            </ul>
          </div>
          <swiper v-bind:options="swiperOption">
            <swiper-slide v-for="(item,index) in slideList" v-bind:key="index">
              <a v-bind:href="'/#/detail/'+item.goodsId"><img v-bind:src="item.slidePic"></a>
            </swiper-slide>
            <!-- Optional controls -->
            <div class="swiper-pagination"  slot="pagination"></div>
            <div class="swiper-button-prev" slot="button-prev"></div>
            <div class="swiper-button-next" slot="button-next"></div>
          </swiper>
        </div>
        <div class="ads-box">
          <a v-bind:href="'/#/detail/'+item.goodsId" v-for="(item,index) in adsList" v-bind:key="index">
            <img v-lazy="item.adsPic" alt="">
          </a>
          <a v-bind:href="'/#/detail/'+item.goodsId" v-for="(item,index) in adsList" v-bind:key="index">
            <img v-lazy="item.adsPic" alt="">
          </a>
        </div>
        <div class="banner">
          <a :href="'/#/detail/' + banner.goodsId">
            <img v-lazy="banner.bannerPic" alt="">
          </a>
        </div>
      </div>
      <div class="product-box">
          <div class="container">
            <h2>促销</h2>
            <div class="wrapper">
              <div class="banner-left">
                <a href="/#/detail/940"><img v-lazy="'/imgs/mix-alpha.jpg'" alt=""></a>
              </div>
              <div class="list-box">
                <div class="list" v-for="(arr,i) in phoneList" :key="i">
                  <div class="item" v-for="(item,j) in arr" v-bind:key="j + i">
                    <span class="new-pro">促销</span>
                    <div class="item-img">
                      <img v-lazy="item.pics ? item.pics[0].picsSma : ''" alt="">
                    </div>
                    <div class="item-info">
                      <h3>{{item.goodsName}}</h3>
                      <p style="width:150px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">{{item.subTitle ? item.subTitle: item.goodsName}}</p>
                      <p class="price" @click="addCart(item.goodsId)">{{item.goodsPrice}}元</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="require-box">
          <div class="container">
            <h2>求购</h2>
            <div class="wrapper">
              <div class="list-box">
                <div class="list" v-for="(arr,i) in requireList" :key="i">
                  <div class="item" v-for="(item,j) in arr" v-bind:key="j + i">
                    <!-- <span class="new-pro">促销</span> -->
                    <div class="item-img">
                      <img v-lazy="item.img ? item.img : ''" alt="">
                    </div>
                    <div class="item-info">
                      <h3>{{item.title}}</h3>
                      <p style="width:150px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">{{item.description ? item.description: item.title}}</p>
                      <!-- <p class="price" @click="addCart(item.id)">{{item.id}}元</p> -->
                      <!-- <p class="price">查看详情</p> -->
                      <el-popover
                        class="require-popver"
                        placement="top"
                        width="300"
                        @show="showRequirePopver(item)"
                        trigger="hover">
                        <p>发布者：{{item.username}}</p>
                        <p>接受价格：{{item.min + "-" + item.max}}</p>
                        <p>联系方式：{{item.telephone}}</p>
                        <p>详细描述：{{item.description}}</p>
                        <div style="text-align: right; margin: 0">
                          <el-button size="mini" type="text">取消</el-button>
                          <el-button type="primary" size="mini" @click="dialogShow">联系买家</el-button>
                        </div>
                         <p class="price" slot="reference">查看详情</p>
                        <!-- <el-button slot="reference">删除</el-button> -->
                      </el-popover>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <el-dialog
          :title="currRequire? currRequire.username : ''"
          :visible.sync="chatDialog"
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
      <modal
        title="提示"
        sureText="查看购物车"
        btnType="1"
        modalType="middle"
        v-bind:showModal="showModal"
        v-on:submit="goToCart"
        v-on:cancel="showModal=false"
        >
        <template v-slot:body>
          <p>商品添加成功！</p>
        </template>
      </modal>
    </div>
</template>

<script>
import ServiceBar from '../components/ServiceBar.vue'
import { swiper, swiperSlide } from 'vue-awesome-swiper'
import Modal from './../components/Modal'
import 'swiper/dist/css/swiper.css'
import { mapState } from 'vuex'
import VEmojiPicker from 'v-emoji-picker'
export default {
  name: 'index',
  components: {
    ServiceBar,
    swiper,
    swiperSlide,
    Modal,
    VEmojiPicker
  },
  created() {
    this.getSlideList()
    this.getMenuList()
    this.getAds()
    this.getBanner()
    this.getPromote()
    this.getRequire()
  },
  data() {
    return {
      swiperOption: {
        autoplay: true,
        loop: true,
        effect: 'cube',
        cubeEffect: {
          shadowOffset: 100,
          shadowScale: 0.6
        },
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        }
      },
      slideList: [
        {
          goodsId: '42',
          slidePic: '/imgs/slider/slide-1.jpg'
        },
        {
          goodsId: '45',
          slidePic: '/imgs/slider/slide-2.jpg'
        },
        {
          goodsId: '46',
          slidePic: '/imgs/slider/slide-3.jpg'
        },
        {
          goodsId: '',
          slidePic: '/imgs/slider/slide-4.jpg'
        },
        {
          goodsId: '',
          slidePic: '/imgs/slider/slide-1.jpg'
        }
      ],
      menuList: [
        [
          {
            id: 30,
            img: '/imgs/item-box-1.png',
            name: '小米CC9'
          }, {
            id: 31,
            img: '/imgs/item-box-2.png',
            name: '小米8青春版'
          }, {
            id: 32,
            img: '/imgs/item-box-3.jpg',
            name: 'Redmi K20 Pro'
          }, {
            id: 33,
            img: '/imgs/item-box-4.jpg',
            name: '移动4G专区'
          }
        ],
        [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]
      ],
      adsList: [
        {
          id: 33,
          img: '/imgs/ads/ads-1.png'
        }, {
          id: 48,
          img: '/imgs/ads/ads-2.jpg'
        }, {
          id: 45,
          img: '/imgs/ads/ads-3.png'
        }, {
          id: 47,
          img: '/imgs/ads/ads-4.jpg'
        }
      ],
      phoneList: [
        [{ name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 },
          { name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 },
          { name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 },
          { name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 },
          { name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 },
          { name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 }],
        [{ name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 },
          { name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 },
          { name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 },
          { name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 },
          { name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 },
          { name: '小米9', subtitle: '最优性价', price: 2999.00, mainImage: '/imgs/nav-img/nav-4.png', id: 30 }]
      ],
      showModal: false,
      banner: {},
      requireList: [],
      currRequire: {},
      chatDialog: false,
      emojiShow: false,
      messageContent: '',
      chatList: []
    }
  },
  methods: {
    addCart(id) {
      if (!this.username) {
        this.$router.push('/cart')
        return
      }
      this.axios.post('/carts', {
        productId: id,
        selected: true
      }).then((res) => {
        this.showModal = true
        console.log(res)
        this.$store.dispatch('saveCartCount', res.data.cartTotalQuantity)
      })
    },
    goToCart() {
      this.$router.push('/cart')
    },
    getSlideList () {
      this.axios.get('goods/slide').then(res => {
        this.slideList = res.list
      })
    },
    getMenuList() {
      this.axios.get('goods/menu_list').then(res => {
        // this.slideList = res.list
        res.list.forEach(element => {
          element.goods = this.arrTrans(4, element.goods)
        })
        // console.log(res.list)
        this.menuList = res.list
      })
    },
    arrTrans(num, arr) {
      const newArr = []
      while (arr.length > 0) {
        newArr.push(arr.splice(0, num))
      }
      return newArr
    },
    getAds() {
      this.axios.get('goods/ads').then(res => {
        this.adsList = res.list
      })
    },
    getBanner() {
      this.axios.get('goods/banner').then(res => {
        this.banner = res.list
      })
    },
    getPromote() {
      this.axios.get('goods/promote').then(res => {
        this.phoneList = this.arrTrans(6, res.list)
      })
    },
    getRequire() {
      this.axios.get('require/meunList').then(res => {
        this.requireList = this.arrTrans(6, res.list)
      })
    },
    showRequirePopver(row) {
      this.currRequire = row
    },
    sendMsg() {
      // this.$message.success(this.messageContent)
      if (!this.messageContent) { this.$message.info('请输入内容'); return }
      const data = { talkId: this.username, sendId: this.currRequire.username, content: this.messageContent, gmtCreate: this.now, guserId: this.currRequire.username, puchaserId: this.username }
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
      this.axios.get('/chat/-1/-1', { params: { username: this.username, receiver: this.currRequire.username } }).then(res => {
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
  .index{
    .swiper-box{
      .nav-menu{
        position:absolute;
        width:264px;
        height:451px;
        z-index:9;
        padding:26px 0;
        background-color:#55585a7a;
        box-sizing:border-box;
        .menu-wrap{
          .menu-item{
            height:50px;
            line-height:50px;
            a{
              position:relative;
              display:block;
              font-size:16px;
              color:#ffffff;
              padding-left:30px;
              &:after{
                position:absolute;
                right:30px;
                top:17.5px;
                content:' ';
                @include bgImg(10px,15px,'/imgs/icon-arrow.png');
              }
            }
            &:hover{
              background-color:$colorA;
              .children{
                display:block;
              }
            }
            .children{
              display:none;
              width:962px;
              height:451px;
              background-color:$colorG;
              position:absolute;
              top:0;
              left:264px;
              border:1px solid $colorH;
              ul{
                display:flex;
                justify-content:space-between;
                height:75px;
                li{
                  height:75px;
                  line-height:75px;
                  flex:1;
                  padding-left:23px;
                }
                a{
                  color:$colorB;
                  font-size:14px;
                }
                img{
                  width:42px;
                  height:35px;
                  vertical-align:middle;
                  margin-right:15px;
                }
              }
            }
          }
        }
      }
      .swiper-container {
        height: 451px;
        .swiper-button-prev{
          left:274px;
        }
        img{
          width:100%;
          height:100%;
        }
      }
    }
    .ads-box{
      @include flex();
      margin-top:14px;
      margin-bottom:31px;
      a{
        width:296px;
        height:167px;
      }
    }
    .banner{
      margin-bottom:50px;
    }
    .product-box{
      background-color:$colorJ;
      padding:30px 0 50px;
      h2{
        font-size:$fontF;
        height:21px;
        line-height:21px;
        color:$colorB;
        margin-bottom:20px;
      }
      .wrapper{
        display:flex;
        .banner-left{
          margin-right:16px;
          img{
            width:224px;
            height:619px;
          }
        }
        .list-box{
          .list{
            @include flex();
            width:986px;
            margin-bottom:14px;
            &:last-child{
              margin-bottom:0;
            }
            .item{
              width:236px;
              height:302px;
              background-color:$colorG;
              text-align:center;
              span{
                display:inline-block;
                width:67px;
                height:24px;
                font-size:14px;
                line-height:24px;
                color:$colorG;
                &.new-pro{
                  background-color:#7ECF68;
                }
                &.kill-pro{
                  background-color:#E82626;
                }
              }
              .item-img{
                img{
                  width:100%;
                  height:195px;
                }
              }
              .item-info{
                h3{
                  font-size:$fontJ;
                  color:$colorB;
                  line-height:$fontJ;
                  font-weight:bold;
                }
                p{
                  color:$colorD;
                  line-height:13px;
                  margin:6px auto 13px;
                }
                .price{
                  color:#F20A0A;
                  font-size:$fontJ;
                  font-weight:bold;
                  cursor:pointer;
                  &:after{
                    @include bgImg(22px,22px,'/imgs/icon-cart-hover.png');
                    content:' ';
                    margin-left:5px;
                    vertical-align: middle;
                  }
                }
              }
            }
          }
        }
      }
    }
    .require-box{
      background-color:$colorJ;
      padding:30px 0 50px;
      h2{
        font-size:$fontF;
        height:21px;
        line-height:21px;
        color:$colorB;
        margin-bottom:20px;
      }
      .wrapper{
        display:flex;
        .banner-left{
          margin-right:16px;
          img{
            width:224px;
            height:619px;
          }
        }
        .list-box{
          .list{
            @include flex();
            width:1226px;
            margin-bottom:14px;
            &:last-child{
              margin-bottom:0;
            }
            .item{
              width:236px;
              height:302px;
              background-color:$colorG;
              text-align:center;
              span{
                display:inline-block;
                width:67px;
                height:24px;
                font-size:14px;
                line-height:24px;
                color:$colorG;
                &.new-pro{
                  background-color:#7ECF68;
                }
                &.kill-pro{
                  background-color:#E82626;
                }
              }
              .item-img{
                img{
                  width:100%;
                  height:220px;
                }
              }
              .item-info{
                margin-top: 5px;
                h3{
                  font-size:$fontJ;
                  color:$colorB;
                  line-height:$fontJ;
                  font-weight:bold;
                }
                p{
                  color:$colorD;
                  line-height:13px;
                  margin:6px auto 13px;
                }
                .price{
                  color:#F20A0A;
                  font-size:$fontJ;
                  font-weight:bold;
                  cursor:pointer;
                  &:after{
                    // @include bgImg(22px,22px,'/imgs/icon-cart-hover.png');
                    content:' ';
                    margin-left:5px;
                    vertical-align: middle;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  .el-popover{
      p{
        margin: 10px;
        color: #999999
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
