<template>
  <div class="good-item">
    <div style="">
      <div class="good-img">
        <a @click="openProduct(msg.id)">
          <img v-lazy="msg.img" :alt="msg.title" :key="msg.img">
        </a>
      </div>
      <h6 class="good-title" v-html="msg.title">{{msg.title}}</h6>
      <h3 class="sub-title ellipsis">{{msg.description}}</h3>
      <div class="good-price pr">
        <div class="ds pa">
          <!-- <a @click="openProduct(msg.id)">
            <y-button text="查看详情" style="margin: 0 5px"></y-button>
          </a> -->
          <el-popover
            class="require-popver"
            placement="top"
            width="300"
            trigger="hover">
            <p>发布者：{{msg.username}}</p>
            <p>接受价格：{{msg.min + "-" + msg.max}}</p>
            <p>联系方式：{{msg.telephone}}</p>
            <p>详细描述：{{msg.description}}</p>
            <!-- <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text">取消</el-button>
              <el-button type="primary" size="mini" @click="dialogShow">联系买家</el-button>
            </div> -->
            <a slot="reference">
              <y-button text="查看详情" style="margin: 0 5px"></y-button>
            </a>
            <!-- <el-button slot="reference">删除</el-button> -->
          </el-popover>
          <y-button text="联系买家"
                    style="margin: 0 5px"
                    @btnClick="dialogShow"
                    classStyle="main-btn"
          ></y-button>
        </div>
        <p><span style="font-size:14px">￥</span>{{Number(msg.min).toFixed(2)}}</p>
      </div>
    </div>
  </div>
</template>
<script>
import YButton from './YButton'
export default {
  props: {
    msg: {
      min: 0
    }
  },
  data () {
    return {
    }
  },
  methods: {
    dialogShow() {
      this.$store.dispatch('saveRequireMsg', this.msg)
      this.$store.dispatch('saveRequireDialog', true)
    }
  },
  computed: {
  },
  mounted () {
  },
  components: {
    YButton
  }
}
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../assets/scss/mixin";
  @import "../assets/scss/theme";

  .good-item {
    background: #fff;
    width: 24.6%;
    transition: all .5s;
    height: 430px;
    &:hover {
      transform: translateY(-3px);
      box-shadow: 1px 1px 20px #999;
      .good-price p {
        display: none;
      }
      .ds {
        display: flex;
      }
    }
    .ds {
      @extend %block-center;
      width: 100%;
      display: none;
    }

    .good-img {
      img {
        margin: 50px auto 10px;
        @include wh(206px);
        display: block;
      }
    }
    .good-price {
      margin: 15px 0;
      height: 30px;
      text-align: center;
      line-height: 30px;
      color: #d44d44;
      font-family: Arial;
      font-size: 18px;
      font-weight: 700;
    }
    .good-title {
      line-height: 1.2;
      font-size: 16px;
      color: #424242;
      margin: 0 auto;
      padding: 0 14px;
      text-align: center;
      overflow: hidden;
    }
    h3 {
      text-align: center;
      line-height: 1.2;
      font-size: 12px;
      color: #d0d0d0;
      padding: 10px;
    }

  }
</style>
