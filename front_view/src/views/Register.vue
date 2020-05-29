<template>
  <div class="register">
    <div class="container">
      <!-- <a href="/#/index"><img src="/imgs/login-logo.png" alt=""></a> -->
      <h1>校内二手商城</h1>
    </div>
    <div class="wrapper">
      <div class="container">
        <div class="login-form">
          <h3>
            <span >帐号注册</span>
            <!-- class="checked" -->
            <!-- <span class="sep-line">|</span><span>扫码登录</span> -->
          </h3>
          <div class="input">
            <input type="text" placeholder="请输入帐号" v-model="username" @blur="nameValid">
            <span style="font-size: 12px;color:red;margin-left:15px;" v-if="nameIsExist">{{ nameError }}</span>
          </div>
          <div class="input">
            <input type="text" placeholder="请输入邮箱" v-model="email" @blur="emailValid">
            <span style="font-size: 12px;color:red;margin-left:15px;" v-if="emailExist" >{{ emailError }}</span>
          </div>
          <div class="input">
            <input type="password" placeholder="请输入密码" v-model="password" @blur="passwordValid">
            <span style="font-size: 12px;color:red;margin-left:15px;" v-if="passwordExist" >{{ passwordError }}</span>
          </div>
          <div class="btn-box">
            <a href="javascript:;" class="btn" @click="register">立即注册  </a>
          </div>
          <!-- <el-form label-position="top" label-width="80px" :model="userForm">
            <el-form-item label="名称">
              <el-input v-model="userForm.name"></el-input>
            </el-form-item>
          </el-form> -->
          <div class="tips">
            <!-- <div class="sms" @click="register">手机短信登录/注册</div> -->
            <!-- <div class="reg">立即登录</div> -->
            <a href="/#/login"><div class="reg">立即登录</div></a>
            <!-- <span>|</span>忘记密码？ -->
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <div class="footer-link">
        <a href="https://www.imooc.com/u/1343480" target="_blank">河畔一角主页</a><span>|</span>
        <a href="https://coding.imooc.com/class/113.html" target="_blank">Vue全栈课程</a><span>|</span>
        <a href="https://coding.imooc.com/class/236.html" target="_blank">React全家桶课程</a><span>|</span>
        <a href="https://coding.imooc.com/class/343.html" target="_blank">微信支付专项课程（H5+小程序/云+Node+MongoDB）</a>
      </div>
      <p class="copyright">Copyright ©2019 mi.futurefe.com All Rights Reserved.</p>
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  name: 'register',
  data() {
    return {
      username: '',
      password: '',
      email: '',
      userId: '',
      userForm: {},
      nameIsExist: false,
      nameError: '',
      emailExist: false,
      emailError: '',
      passwordExist: false,
      passwordError: ''
    }
  },
  methods: {
    login() {
      const { username, password } = this
      this.axios.post('/user/login', {
        username,
        password
      }).then((res) => {
        // this.$cookie.set('userId', res.id, { expires: 'Session' })
        // console.log(res)
        window.sessionStorage.setItem('token', res.token)
        window.sessionStorage.setItem('refresh_token', res.refresh_token)
        // this.$store.dispatch('saveUserName',res.username);
        this.saveUserName(res.username)
        this.$router.push({
          name: 'index',
          params: {
            from: 'login'
          }
        })
      })
    },
    ...mapActions(['saveUserName']),
    async register() {
      const flag = await this.nameValid()
      if (flag & this.emailValid() & this.passwordValid()) {
        console.log(this.nameValid())
        this.axios.post('/user/register', {
          username: this.username,
          password: this.password,
          email: this.email
        }).then(() => {
          this.$message.success('注册成功')
        })
      }
    },
    async nameValid() {
      if (!this.username) {
        this.nameError = '用户名不能为空'
        this.nameIsExist = true
        return false
      }
      const res = await this.axios.get('/user/name_exist', { params: { username: this.username } })
      if (res.flag) {
        this.nameError = '用户名已经存在'
        this.nameIsExist = true
        return false
      } else {
        this.nameIsExist = false
        return true
      }
    },
    emailValid() {
      if (!this.email) {
        this.emailError = '邮箱不能为空'
        this.emailExist = true
        return false
      }
      const re = /^\w+@[a-z0-9]+\.[a-z]+$/
      if (!re.test(this.email)) {
        this.emailError = '请填入正确的邮箱格式'
        this.emailExist = true
        return false
      }
      this.emailExist = false
      return true
    },
    passwordValid() {
      if (!this.password) {
        this.passwordError = '密码不能为空'
        this.passwordExist = true
        return false
      }
      var pwdRegex = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z]).{6,30}')

      if (!pwdRegex.test(this.password)) {
        this.passwordError = '您的密码复杂度太低（密码中必须包含字母、数字），请及时修改密码！'
        this.passwordExist = true
        return false
      }
      this.passwordExist = false
      return true
    }
  }
}
</script>
<style lang="scss">
.register{
  &>.container{
    height:113px;
    h1{
      line-height: 113px;
      color:#FF6600;
    }
    img{
      width:auto;
      height:100%;
    }
  }
  .wrapper{
    // background:url('/imgs/login-bg.jpg') no-repeat center;
    // background-size: contain;
    .container{
      height:576px;
      .login-form{
        box-sizing: border-box;
        padding-left: 31px;
        padding-right: 31px;
        width:410px;
        height:510px;
        background-color:#ffffff;
        position:absolute;
        bottom:29px;
        right:30%;
        h3{
          line-height:23px;
          font-size:18px;
          text-align:center;
          margin:40px auto 49px;
          .checked{
            color:#FF6600;
          }
          .sep-line{
            margin:0 32px;
          }
        }
        .input{
          display:inline-block;
          width:348px;
          height:50px;
          border:1px solid #E5E5E5;
          margin-bottom:20px;
          input{
            width: 100%;
            height: 100%;
            border: none;
            padding: 18px;
          }
        }
        .btn{
          width:100%;
          line-height:50px;
          margin-top:10px;
          font-size:16px;
        }
        .tips{
          margin-top:14px;
          display:flex;
          justify-content:space-between;
          font-size:14px;
          cursor:pointer;
          .sms{
            color:#FF6600;
          }
          .reg{
            color:#999999;
            span{
              margin:0 7px;
            }
          }
        }
      }
    }
  }
  .footer{
    height:100px;
    padding-top:60px;
    color:#999999;
    font-size:16px;
    text-align:center;
    .footer-link{
      a{
        color:#999999;
        display:inline-block;
      }
      span{
        margin:0 10px;
      }
    }
    .copyright{
      margin-top:13px;
    }
  }
}
</style>
