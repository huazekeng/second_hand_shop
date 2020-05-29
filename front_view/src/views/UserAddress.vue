<template>
  <div class="user-address">
    <order-header title="邮寄地址">
      <template v-slot:tip>
        <span>邮寄地址查看与修改</span>
      </template>
    </order-header>
    <svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" style="position: absolute; width: 0px; height: 0px; overflow: hidden;">
      <defs>
        <symbol id="icon-add" viewBox="0 0 31 32">
          <title>add</title>
          <path d="M30.745 15.152h-14.382v-14.596c0-0.308-0.243-0.557-0.543-0.557s-0.543 0.249-0.543 0.557v14.596h-14.665c-0.3 0-0.543 0.249-0.543 0.557s0.243 0.557 0.543 0.557h14.665v15.177c0 0.307 0.243 0.557 0.543 0.557s0.543-0.249 0.543-0.557v-15.177h14.382c0.3 0 0.543-0.249 0.543-0.557s-0.243-0.557-0.543-0.557z" class="path1"></path>
        </symbol>
        <symbol id="icon-edit" viewBox="0 0 32 32">
          <title>edit</title>
          <path d="M28.287 8.51l-4.805-4.806 0.831-0.831c0.472-0.472 1.086-0.777 1.564-0.777 0.248 0 0.452 0.082 0.622 0.253l3.143 3.144c0.539 0.54 0.133 1.529-0.524 2.186l-0.831 0.831zM26.805 9.992l-1.138 1.138-4.805-4.806 1.138-1.138 4.805 4.806zM24.186 12.612l-14.758 14.762-4.805-4.806 14.758-14.762 4.805 4.806zM7.379 28.288l-4.892 1.224 1.223-4.894 3.669 3.67zM31.123 4.011l-3.143-3.144c-0.567-0.567-1.294-0.867-2.103-0.867-1.036 0-2.174 0.52-3.045 1.391l-20.429 20.436c-0.135 0.134-0.23 0.302-0.276 0.487l-2.095 8.385c-0.089 0.355 0.017 0.736 0.276 0.995 0.198 0.198 0.461 0.307 0.741 0.307 0.085 0 0.171-0.010 0.254-0.031l8.381-2.096c0.185-0.047 0.354-0.142 0.487-0.276l20.43-20.436c1.409-1.41 2.042-3.632 0.524-5.15v0z" class="path1"></path>
        </symbol>
        <symbol id="icon-del" viewBox="0 0 32 32">
          <title>delete</title>
          <path d="M11.355 4.129v-2.065h9.29v2.065h-9.29zM6.194 29.935v-23.742h19.613v23.742h-19.613zM30.968 4.129h-8.258v-3.097c0-0.569-0.463-1.032-1.032-1.032h-11.355c-0.569 0-1.032 0.463-1.032 1.032v3.097h-8.258c-0.569 0-1.032 0.463-1.032 1.032s0.463 1.032 1.032 1.032h3.097v24.774c0 0.569 0.463 1.032 1.032 1.032h21.677c0.569 0 1.032-0.463 1.032-1.032v-24.774h3.097c0.569 0 1.032-0.463 1.032-1.032s-0.463-1.032-1.032-1.032v0z" class="path1"></path>
          <path d="M10.323 9.806c-0.569 0-1.032 0.463-1.032 1.032v14.452c0 0.569 0.463 1.032 1.032 1.032s1.032-0.463 1.032-1.032v-14.452c0-0.569-0.463-1.032-1.032-1.032z" class="path2"></path>
          <path d="M16 9.806c-0.569 0-1.032 0.463-1.032 1.032v14.452c0 0.569 0.463 1.032 1.032 1.032s1.032-0.463 1.032-1.032v-14.452c0-0.569-0.463-1.032-1.032-1.032z" class="path3"></path>
          <path d="M21.677 9.806c-0.569 0-1.032 0.463-1.032 1.032v14.452c0 0.569 0.463 1.032 1.032 1.032s1.032-0.463 1.032-1.032v-14.452c0-0.569-0.463-1.032-1.032-1.032z" class="path4"></path>
        </symbol>
      </defs>
    </svg>
    <div class="container">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>地址管理</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="item-address">
            <h2 class="addr-title">收货地址</h2>
            <div class="addr-list clearfix">
              <div class="addr-info" :class="{'checked':index == checkIndex}" @click="checkIndex=index" v-for="(item,index) in list" :key="index">
                <h2>{{item.receiverName}}</h2>
                <div class="phone">{{item.receiverMobile}}</div>
                <div class="street">{{item.detailAddress}}</div>
                <div class="action">
                  <a href="javascript:;" class="fl" @click="delAddress(item)">
                    <svg class="icon icon-del">
                      <use xlink:href="#icon-del"></use>
                    </svg>
                  </a>
                  <a href="javascript:;" class="fr" @click="editAddressModal(item)">
                    <svg class="icon icon-edit">
                      <use xlink:href="#icon-edit"></use>
                    </svg>
                  </a>
                </div>
              </div>
              <div class="addr-add" @click="openAddressModal">
                <div class="icon-add"></div>
                <div>添加新地址</div>
              </div>
            </div>
          </div>
    </div>
    <modal
      title="新增地址"
      btnType="1"
      :showModal="showEditModal"
      @cancel="showEditModal=false"
      @submit="submitAddress"
    >
      <template v-slot:body>
        <div class="edit-wrap">
          <div class="item">
            <input type="text" class="input" placeholder="姓名" v-model="checkedItem.receiverName">
            <input type="text" class="input" placeholder="手机号" v-model="checkedItem.receivePhone">
          </div>
          <div class="item">
            <el-cascader
              class="province"
              v-model="provice"
              :options="options"
              :props="{ expandTrigger: 'hover', label: 'label', value: 'value'}"
              clearable
              filterable></el-cascader>
          </div>
          <div class="item">
            <textarea name="street"  v-model="checkedItem.receiverAddress" placeholder="详细地址"></textarea>
          </div>
          <div class="item">
            <input type="text" class="input" placeholder="邮编" v-model="checkedItem.receiverZip">
          </div>
        </div>
      </template>
    </modal>
    <modal
      title="删除确认"
      btnType="1"
      :showModal="showDelModal"
      @cancel="showDelModal=false"
      @submit="submitAddress"
    >
      <template v-slot:body>
        <p>您确认要删除此地址吗？</p>
      </template>
    </modal>
  </div>
</template>
<script>
import OrderHeader from './../components/OrderHeader'
import Modal from './../components/Modal'
import City from './citydata.js'

export default {
  name: 'userinfo',
  data() {
    return {
      list: [], // 收货地址列表
      userAction: '', // 用户行为 0：新增 1：编辑 2：删除
      showDelModal: false, // 是否显示删除弹框
      showEditModal: false, // 是否显示新增或者编辑弹框
      checkIndex: 0, // 当前收货地址选中索引
      checkedItem: {}, // 选中的地址对象
      provice: [], // 省市区
      options: City
    }
  },
  components: {
    OrderHeader,
    Modal
  },
  mounted() {
    // this.getUser()
    this.getAddressList()
  },
  methods: {
    getAddressList() {
      this.axios.get('/user/address').then((res) => {
        this.list = res.list
      })
    },
    // 打开新增地址弹框
    openAddressModal() {
      this.userAction = 0
      this.checkedItem = {}
      this.showEditModal = true
    },
    // 打开新增地址弹框
    editAddressModal(item) {
      this.userAction = 1
      this.checkedItem = item
      console.log(item)
      this.provice = item.receiverProvince.split(' ')
      this.showEditModal = true
    },
    delAddress(item) {
      this.checkedItem = item
      this.userAction = 2
      this.showDelModal = true
    },
    // 地址删除、编辑、新增功能
    submitAddress() {
      const { checkedItem, userAction } = this
      let method; let url; let params = {}
      if (userAction === 0) {
        method = 'post'
        url = '/user/address'
      } else if (userAction === 1) {
        method = 'put'
        url = '/user/address'
      } else {
        method = 'delete'
        url = `/user/address/${checkedItem.cgnId}`
      }
      if (userAction === 0 || userAction === 1) {
        const { receiverName, receivePhone, receiverAddress, receiverZip, userId, cgnId } = checkedItem
        let errMsg = ''
        if (!receiverName) {
          errMsg = '请输入收货人名称'
        } else if (!receivePhone || !/\d{11}/.test(receivePhone)) {
          errMsg = '请输入正确格式的手机号'
        } else if (this.provice.length === 0) {
          errMsg = '请选择省份'
        } else if (!receiverAddress) {
          errMsg = '请输入收货地址'
        } else if (!/\d{6}/.test(receiverZip)) {
          errMsg = '请输入六位邮编'
        }
        if (errMsg) {
          this.$message.error(errMsg)
          return
        }
        console.log(this.provice)
        // console.log(this.checkedItem)
        const receiverProvince = this.provice.join(' ')
        console.log(receiverProvince)
        params = {
          receiverName,
          receivePhone,
          receiverProvince,
          receiverAddress,
          receiverZip,
          userId,
          cgnId
        }
      }
      console.log(params)
      this.axios[method](url, params).then(() => {
        this.closeModal()
        this.getAddressList()
        this.$message.success('操作成功')
      })
    },
    closeModal() {
      this.checkedItem = {}
      this.provice = []
      this.userAction = ''
      this.showDelModal = false
      this.showEditModal = false
    }
  }
}
</script>
<style lang="scss">
.user-address {
  .container{
    .item-address{
      padding-top: 38px;
      .addr-title{
        margin-bottom: 10px;
      }
      .addr-list{
        .addr-info,.addr-add{
          box-sizing:border-box;
          float: left;
          width:271px;
          height:180px;
          border:1px solid #E5E5E5;
          margin-right: 15px;
          padding: 15px 24px;
          font-size: 14px;
          color:#757575;
        }
        .addr-info{
          cursor:pointer;
          h2{
            height:27px;
            font-size:18px;
            font-weight: 300;
            color:#333;
            margin-bottom:10px;
          }
          .street{
            height:50px;
          }
          .action{
            height:50px;
            line-height:50px;
            .icon{
              width: 20px;
              height: 20px;
              fill: #666666;
              vertical-align: middle;
              &:hover{
                fill: #FF6700;
              }
            }
          }
          &.checked{
            border:1px solid #ff6700;
          }
        }
        .addr-add{
          text-align:center;
          color: #999999;
          cursor:pointer;
          .icon-add{
            width:30px;
            height:30px;
            border-radius:50%;
            background:url('/imgs/icon-add.png') #E0E0E0 no-repeat center;
            background-size:14px;
            margin: 0 auto;
            margin-top: 45px;
            margin-bottom: 10px;
          }
        }
      }
    }
  }
  .edit-wrap{
      font-size:14px;
      .item{
        margin-bottom:15px;
        .input{
          display:inline-block;
          width:283px;
          height:40px;
          line-height:40px;
          padding-left:15px;
          border:1px solid #E5E5E5;
          &:focus{
            border-color: #409EFF;
            border-radius: 4px;
          }
          &+.input{
            margin-left:14px;
          }
        }
        select{
          height:40px;
          line-height:40px;
          border:1px solid #E5E5E5;
          margin-right:15px;
        }
        textarea{
          height:62px;
          width:100%;
          padding:13px 15px;
          box-sizing:border-box;
          border:1px solid #E5E5E5;
          &:focus{
            border-color: #409EFF;
            border-radius: 4px;
          }
        }
      }
    }
}
</style>
