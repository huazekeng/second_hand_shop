<template>
    <div class="login_container">
        <div class="login_box">
            <div class="avator_box">
                <img src="../assets/logo.png"/>
            </div>
            <el-form label-width="0px" class="loginForm" :model="form" :rules="rules" ref="loginFormRef">
                <el-form-item prop="username">
                    <el-input v-model="form.username" prefix-icon="iconfont icon-user"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" v-model="form.password" prefix-icon="iconfont icon-3702mima"></el-input>
                </el-form-item>
                <el-form-item class="btns">
                    <el-button type="primary" @click="login()">登录</el-button>
                    <el-button type="info" @click="reset()">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    reset () {
      this.$refs.loginFormRef.resetFields()
    },
    login () {
      this.$refs.loginFormRef.validate(async valid => {
        if (valid) {
          const res = await this.$http.post('/login',
            this.$qs.stringify(this.form),
            {
              headers: { 'content-type': 'application/x-www-form-urlencoded' }
            })
          //   console.log(this.$qs.stringify(this.form))
          //   const options = {
          //     method: 'POST',
          //     headers: { 'content-type': 'application/x-www-form-urlencoded' },
          //     data: this.$qs.stringify(this.from), // 用 qs 将js对象转换为字符串 'name=edward&age=25'
          //     url: '/login'
          //   }
          //   const res = await this.$http(options)
          console.log(res)
          if (res.data.code === 20000) {
            this.$message({ 'message': '登录成功',
              'type': 'success'
            })
            // 将token存储到sessionStorage
            window.sessionStorage.setItem('token', res.data.data.token)
            window.sessionStorage.setItem('refresh_token', res.data.data.refresh_token)
            // 通过路由跳转到 /home
            this.$router.push('/home')
          } else {
            this.$message({ 'message': res.data.message,
              'type': 'warning'
            })
          }
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
    .login_container{
        height: 100%;
        width: 100%;
        background-color:#2b4b6b;
    }

    .login_box{
        width: 450px;
        height: 300px;
        background-color: white;
        border-radius: 3px;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%,-50%);
        .avator_box{
            width: 130px;
            height: 130px;
            border: 1px solid #eee;
            background-color: #fff;
            border-radius: 50%;
            position: absolute;
            left: 50%;
            top: -30%;
            padding: 10px;
            box-shadow: 0 0 10px #ddd;
            transform: translate(-50%,0);
            img{
               width: 100%;
               height: 100%;
               border-radius: 50%;
               background-color: #eee;
            }
        }
    }

    .btns{
        display: flex;
        justify-content: flex-end;
    }

    .loginForm{
        position: absolute;
        bottom: 0px;
        width:100%;
        padding:0 25px;
        box-sizing: border-box;
    }
</style>
