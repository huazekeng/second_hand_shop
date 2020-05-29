<template>
  <div class="header">
    <div class="nav-topbar">
      <div class="container">
        <div class="topbar-menu">
          <a href="javascript:;">商城</a>
          <a href="javascript:;">MUI</a>
          <a href="javascript:;">云服务</a>
          <a href="javascript:;">协议规则</a>
        </div>
        <div class="topbar-user">
          <a class="username_link" href="javascript:;" v-if="username">{{username}} <span class="user-icon"></span>
            <div class="children">
              <ul>
                <li><a href="/#/user/info">个人信息</a></li>
                <li><a href="/#/user/address">地址管理</a></li>
                <li><a href="/#/goods/list">发布管理</a></li>
                <li><a href="/#/require/list">求购管理</a></li>
                <a href="javascript:;" @click="logout">退出</a>
              </ul>
            </div>
          </a>
          <a href="javascript:;" v-if="!username" @click="login">登录</a>
          <a href="/#/chat" v-if="username">站内消息</a>
          <a href="/#/forum/list" >站内论坛</a>
          <a href="/#/order/list" v-if="username">我的订单</a>
          <a href="javascript:;" class="my-cart" @click="goToCart"><span class="icon-cart"></span>购物车({{cartCount}})</a>
        </div>
      </div>
    </div>
    <div class="nav-header">
      <div class="container">
        <div class="header-logo">
          <a href="/#/index"></a>
        </div>
        <div class="header-menu">
          <div class="item-menu">
            <span>小米</span>
            <div class="children">
              <ul>
                <li class="product" v-for="item in xiaomi" :key="item.goodsId">
                  <a :href="'/#/product/'+item.goodsId" target="_blank">
                    <img v-lazy="item.pics[0].picsSma">
                    <div class="pro-name">{{ item.goodsName }}</div>
                    <div class="pro-price">{{ item.goodsPrice | currenceFormat}}</div>
                  </a>
                </li>
                <li class="product" v-for="item in xiaomi" :key="item.goodsId">
                  <a :href="'/#/product/'+item.goodsId" target="_blank">
                    <img v-lazy="item.pics[0].picsSma">
                    <div class="pro-name">{{ item.goodsName }}</div>
                    <div class="pro-price">{{ item.goodsPrice | currenceFormat}}</div>
                  </a>
                </li>
                <li class="product" v-for="item in xiaomi" :key="item.goodsId">
                  <a :href="'/#/product/'+item.goodsId" target="_blank">
                    <img v-lazy="item.pics[0].picsSma">
                    <div class="pro-name">{{ item.goodsName }}</div>
                    <div class="pro-price">{{ item.goodsPrice | currenceFormat}}</div>
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div class="item-menu">
            <span>华为</span>
            <div class="children">
              <ul>
                <!-- <li class="product" v-for="item in xiaomi" :key="item.goodsId">
                  <a href="" target="_blank">
                    <img v-lazy="'http://localhost/images/goods/23212c93-59dc-4d9c-abfa-c4c81c3b5e96.3944219.jpg_200x200.jpg'">
                    <div class="pro-name">Redmi K30 5G</div>
                    <div class="pro-price">￥2899.00</div>
                  </a>
                </li> -->
                <li class="product" v-for="item in huawei" :key="item.goodsId">
                  <a :href="'/#/product/'+item.goodsId" target="_blank">
                    <img v-lazy="item.pics[0].picsSma">
                    <div class="pro-name">{{ item.goodsName }}</div>
                    <div class="pro-price">{{ item.goodsPrice | currenceFormat}}</div>
                  </a>
                </li>
                 <li class="product" v-for="item in huawei" :key="item.goodsId">
                  <a :href="'/#/product/'+item.goodsId" target="_blank">
                    <img v-lazy="item.pics[0].picsSma">
                    <div class="pro-name">{{ item.goodsName }}</div>
                    <div class="pro-price">{{ item.goodsPrice | currenceFormat}}</div>
                  </a>
                </li>
                 <li class="product" v-for="item in huawei" :key="item.goodsId">
                  <a :href="'/#/product/'+item.goodsId" target="_blank">
                    <img v-lazy="item.pics[0].picsSma">
                    <div class="pro-name">{{ item.goodsName }}</div>
                    <div class="pro-price">{{ item.goodsPrice | currenceFormat}}</div>
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div class="item-menu">
            <span>Redmi</span>
            <div class="children">
              <ul>
                <!-- <li class="product">
                  <a href="" target="_blank">
                    <div class="pro-img">
                      <img src="/imgs/nav-img/nav-3-1.jpg" alt="">
                    </div>
                    <div class="pro-name">小米壁画电视 65英寸</div>
                    <div class="pro-price">6999元</div>
                  </a>
                </li> -->
                <li class="product" v-for="item in redmi" :key="item.goodsId">
                  <a :href="'/#/product/'+item.goodsId" target="_blank">
                    <img v-lazy="item.pics[0].picsSma">
                    <div class="pro-name">{{ item.goodsName }}</div>
                    <div class="pro-price">{{ item.goodsPrice | currenceFormat}}</div>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="header-search">
          <div class="wrapper">
            <input type="text" name="keyword" v-model="keyword">
            <a href="javascript:;" @click="search"></a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'nav-header',
  created() {
    // this.getMenuProductItem()
  },
  filters: {
    currenceFormat(val) {
      if (!val) return 0.00
      return '￥' + val.toFixed(2)
    }
  },
  data() {
    return {
      // username: '',
      // cartCount: 2,
      xiaomi: [],
      huawei: [],
      redmi: [],
      keyword: ''
    }
  },
  computed: {
    // username() {
    //   return this.$store.state.username
    // },
    // cartCount() {
    //   return this.$store.state.cartCount
    // }
    ...mapState(['username', 'cartCount', 'socket'])
  },
  mounted() {
    this.getMenuProductItem()
    const params = this.$route.params
    if (params && params.from === 'login') {
      this.getCartCount()
    }
  },
  methods: {
    login() {
      this.$router.push('/login')
    },
    goToCart() {
      this.$router.push('/cart')
    },
    getMenuProductItem() {
      this.axios.get('goods/cate/1487').then(res => {
        this.xiaomi = res.list
        this.redmi = res.list
      })
      this.axios.get('goods/cate/188').then(res => {
        this.huawei = res.list
      })
    },
    getCartCount() {
      this.axios.get('/carts/products/sum').then((res = 0) => {
        this.$store.dispatch('saveCartCount', res.data)
      })
    },
    logout() {
      const refreshToken = window.sessionStorage.getItem('refresh_token')
      this.axios.post('/user/logout', { refreshToken }).then(() => {
        this.$message.success('退出成功')
        window.sessionStorage.clear()
        this.$store.dispatch('saveUserName', '')
        this.$store.dispatch('saveCartCount', '0')
        this.$store.state.socket.close()
      })
    },
    search() {
      // window.location.href = '/#/search?keyword=' + this.keyword
      // this.$router.push({
      //   path: '/search',
      //   query: {
      //     keyword: this.keyword
      //   }
      // })
      window.open('/#/search?keyword=' + this.keyword, '_self')
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/scss/base.scss';
@import '../assets/scss/mixin.scss';
@import '../assets/scss/config.scss';
.header {
  .nav-topbar {
    height: 39px;
    line-height:39px;
    background-color: #333333;
    color:#B0B0B0;
    padding: 0px;
    .container {
      @include flex();
      a {
          margin-top:1px;
          display:inline-block;
          color:#B0B0B0;
          margin-right:17px;
          &.username_link{
            width: 110px;
            position: relative;
            text-align: center;
            &:hover{
              color: #FF6600;
              background-color: #f5f5f5;
              box-shadow:  0px 2px 10px rgba(0,0,0,.15);
              .user-icon {
                border-top-color:#FF6600;
              }
              .children {
                display: block;
                a {
                  color: #424242;
                  z-index: 99;
                }
              }
            }
            .children {
              width: 100%;
              position: absolute;
              // top: 39px;
              display: none;
              z-index: 99;
              background-color: #f5f5f5;
              box-shadow:  0 2px 10px rgba(0,0,0,.15);
              a {
                &:hover{
                  color:#FF6600;
                }
              }
            }
          }
          .user-icon {
              display: inline-block;
              vertical-align: middle;
              width: 0;
              height: 0;
              border: 5px solid transparent;
              border-top-color:#fff;
              margin-top: 4px;
              margin-left: 4px;
          }
      }
      .my-cart{
          width:110px;
          background-color:#FF6600;
          text-align:center;
          color:#ffffff;
          margin-right:0;
          .icon-cart {
            @include bgImg(16px,14px,'/imgs/icon-cart-checked.png',contain);
            // display: inline-block;
            // background: url('/imgs/icon-cart-checked.png') no-repeat center;
            // width: 16px;
            // height: 14px;
            // background-size: contain;
            margin-right: 4px;
          }
        }
    }
  }
  .nav-header {
    .container {
      height: 110px;
      position: relative;
      @include flex();
      .header-menu {
        padding-left: 209px;
        width: 643px;
        display: inline-block;
        .item-menu {
          display: inline-block;
          font-weight: bold;
          font-size: 16px;
          color: #333333;
          line-height: 112px;
          margin-right: 20px;
          span {
            cursor: pointer;
          }
          &:hover{
            color:$colorA;
            .children{
              height:220px;
              opacity:1;
              z-index: 99;
            }
          }
          .children {
            position:absolute;
              top:112px;
              left:0;
              width:1226px;
              height:0;
              opacity:0;
              overflow:hidden;
              border-top:1px solid #E5E5E5;
              box-shadow:0px 7px 6px 0px rgba(0, 0, 0, 0.11);
              z-index: 10;
              transition:all .5s;
              background-color: #ffffff;
              .product{
                position:relative;
                float:left;
                width:16.6%;
                height:220px;
                font-size:12px;
                line-height:12px;
                text-align: center;
                a{
                  display:inline-block;
                }
                img{
                  width:auto;
                  height:111px;
                  margin-top:26px;
                }
                .pro-img{
                  height:137px;
                }
                .pro-name{
                  font-weight:bold;
                  margin-top:19px;
                  margin-bottom:8px;
                  color:$colorB;
                }
                .pro-price{
                  color:$colorA;
                }
                &:before{
                  content:' ';
                  position:absolute;
                  top:28px;
                  right:0;
                  border-left:1px solid $colorF;
                  height:100px;
                  width:1px;
                }
                &:last-child:before{
                  border-left:none;
                }
              }
          }
        }
      }
      .header-search {
        width: 319px;
        .wrapper {
          height: 50px;
          border: 1px solid #E0E0E0;
          display: flex;
          align-items: center;
          input {
            height: 50px;
            width: 264px;
            border: none;
            border-right: 1px solid #E0E0E0;
            padding-left: 14px;
            box-sizing: border-box;
            &:hover {
              border-bottom: 1px solid #c0c0c0;
              box-shadow: 5px 5px 5px #E3E3E3;
            }
          }
          a {
            // background: url('/imgs/icon-search.png') no-repeat center;
            // background-size: contain;
            // display: inline-block;
            // width: 18px;
            // height: 18px;
            @include bgImg(18px,18px,'/imgs/icon-search.png',contain);
            margin-left: 17px;
          }
        }
      }
    }
  }
}
</style>
