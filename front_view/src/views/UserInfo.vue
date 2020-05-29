<template>
  <div class="user-info">
    <order-header title="个人信息">
      <template v-slot:tip>
        <span>个人信息查看与修改</span>
      </template>
    </order-header>
    <div class="container">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>个人信息</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="info">
        <div class="info-header">
          <h3>基础资料<i class="el-icon-edit" @click="showDelModal=true">编辑</i></h3>
        </div>
        <div class="info-basic">
          <div class="username">
            <span class="label">专业班级：</span>
            <span>{{ this.userInfo.school || '请填写专业班级'}}</span>
          </div>
          <div class="email">
            <span class="label">邮箱：</span>
            <span>{{ this.userInfo.userEmail || '请填写邮箱'}}</span>
          </div>
          <div class="nickname">
            <span class="label">昵称：</span>
            <span>{{ this.userInfo.nickName || '请填写昵称'}}</span>
          </div>
          <div class="phone">
            <span class="label">电话：</span>
            <span>{{ this.userInfo.userTel || '请填写电话'}}</span>
          </div>
          <div class="qq">
            <span class="label">QQ：</span>
            <span>{{ this.userInfo.userQq || '请填写QQ'}}</span>
          </div>
          <div class="sex">
            <span  class="label">性别：</span>
            <span>{{ this.userInfo.userSex || '请填写性别'}}</span>
          </div>
          <div class="introduct">
            <span class="label">学历：</span>
            <span>{{ this.userInfo.userXueli || '请填写个人介绍'}}</span>
          </div>
          <div class="address">
            <span class="label">地址：</span>
            <span>{{ this.userInfo.address}}</span>
          </div>
          <div class="confidence">
            <span class="label">信用度：</span>
            <span>{{ this.userInfo.confidence}}</span>
          </div>
        </div>
      </div>
      <modal
        title="编辑个人资料"
        btnType="1"
        :showModal="showDelModal"
        @cancel="showDelModal=false"
        @submit="submitEdit"
      >
        <template v-slot:body>
          <div class="edit-wrap">
            <div class="item">
              <span>专业班级：</span>
              <input type="text" class="input" placeholder="专业班级" v-model="userInfo.school">
            </div>
            <div class="item">
              <span>邮箱：</span>
              <input type="text" class="input" placeholder="邮箱" v-model="userInfo.userEmail">
            </div>
            <div class="item">
              <span>昵称：</span>
              <input type="text" class="input" placeholder="邮箱" v-model="userInfo.nickName">
            </div>
            <div class="item">
              <span>电话：</span>
              <input type="text" class="input" placeholder="电话" v-model="userInfo.userTel">
            </div>
            <div class="item">
              <span>QQ：</span>
              <input type="text" class="input" placeholder="邮编" v-model="userInfo.userQq">
            </div>
            <div class="item">
              <span>性别：</span>
              <input type="text" class="input" placeholder="性别" v-model="userInfo.userSex">
            </div>
            <div class="item">
              <span>学历：</span>
              <input type="text" class="input" placeholder="学历" v-model="userInfo.userXueli">
            </div>
             <div class="item">
              <span>地址：</span>
              <input type="text" class="input" placeholder="地址" v-model="userInfo.address">
            </div>
          </div>
        </template>
      </modal>
    </div>
  </div>
</template>
<script>
import OrderHeader from './../components/OrderHeader'
import Modal from './../components/Modal'
export default {
  name: 'userinfo',
  data() {
    return {
      userInfo: {},
      showDelModal: false
    }
  },
  components: {
    OrderHeader,
    Modal
  },
  mounted() {
    this.getUser()
  },
  methods: {
    getUser() {
      this.axios.get('/user').then((res = {}) => {
        this.userInfo = res.data
        // console.log(this.userInfo)
      })
    },
    submitEdit() {
      this.axios.post('/user', this.userInfo).then((res = {}) => {
        this.$message.success('编辑成功')
        this.showDelModal = false
      }).catch(res => {
        this.getUser()
      })
    }
  }
}
</script>
<style lang="scss">
.user-info{
  height: 700px;
  .container{
    .info{
      div{
        color: #666;
        margin-top: 15px;
        font-size: 14px;
        height: 40px;
        line-height: 40px;
        span{
          margin-left: 10px;
        }
        .label{
          // display: inline-block;
          // width: 8%;
        }
      }
      .info-header{
        color: #000000;
        margin-top: 15px;
      }
      i{
        margin-left: 45px;
        color: #4a90e3;
        cursor: pointer;
        font-size: 16px;
      }
      h3{
        font-size: 16px;
      }
    }
  }
  .modal-dialog{
    top: 48%;
  }
  .edit-wrap{
      font-size:14px;
      .item{
        margin-bottom:15px;
        span{
          display: inline-block;
          width:20%;
        }
        .input{
          display:inline-block;
          width:283px;
          height:40px;
          line-height:40px;
          padding-left:15px;
          border:1px solid #E5E5E5;
          &+.input{
            margin-left:14px;
          }
          &:focus{
            border-color: #409EFF;
            border-radius: 4px;
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
.el-breadcrumb{
  margin-top: 15px;
  background-color: #f7f7f7;
  padding: 10px;
  .el-breadcrumb__inner{
      font-size: 12px;
  color: #999;
  }
}
</style>
