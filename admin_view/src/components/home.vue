<template>
    <el-container>
        <el-header>
            <div>
                <img src="../assets/heima.png"/>
                <span>二手商城后台管理系统</span>
            </div>
            <el-button type='info' @click="logout">退出</el-button>
        </el-header>
        <el-container>
            <el-aside :width="collapseWidth">
               <div class="toggle-button" @click="toggleCollapse">{{collapseText}}</div>
               <el-menu background-color="#333744" text-color="#fff" active-text-color="#409eff"
                unique-opened :collapse="isCollapse" :collapse-transition="false" router
                :default-active="navStatus" >
                <!-- 一级菜单 index只接受字符串 -->
                <el-submenu :index="item.psId + ''" v-for="item in menuList" :key="item.psId">
                     <!-- 一级菜单模板 -->
                    <template slot="title">
                        <!-- 一级菜单图标 -->
                        <i :class="iconList[item.psId]"></i>
                        <!-- 一级菜单文案 -->
                        <span slot="title">{{item.psName}}</span>
                    </template>
                    <!-- 二级菜单 -->
                    <el-menu-item :index="menuItem.frontUrl" v-for="menuItem in item.children" :key="menuItem.psId"
                        @click="saveNavStatus">
                         <!-- 二级菜单模板 -->
                        <template slot="title">
                            <!-- 二级菜单图标 -->
                            <i class="el-icon-menu"></i>
                            <!-- 二级菜单文案 -->
                            <span slot="title">{{menuItem.psName}}</span>
                        </template>
                    </el-menu-item>
                </el-submenu>
                </el-menu>
            </el-aside>
            <el-main>
              <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
export default {
  data() {
    return {
      // 左侧菜单列表
      menuList: [],
      iconList: {
        '125': 'iconfont icon-user',
        '103': 'iconfont icon-tijikongjian',
        '101': 'iconfont icon-shangpin',
        '102': 'iconfont icon-danju',
        '145': 'iconfont icon-baobiao'
      },
      // 菜单是否折叠
      isCollapse: false,
      // 折叠块文字
      collapseText: '<<<',
      // 折叠块大小
      collapseWidth: '200px',
      // 默认链接
      navStatus: ''
    }
  },
  created () {
    // 获取菜单
    this.getMenuList()
    // 设置初始链接
    this.navStatus = window.sessionStorage.getItem('navStatus')
  },
  methods: {
    // 获取菜单
    async getMenuList() {
      const res = await this.$http.get('/backend-permission/menu')
      if (res.data.code === 20000) {
        this.menuList = res.data.data.data
        // console.log(this.menuList)
      } else {
        this.$message({ 'message': res.data.message,
          'type': 'info'
        })
      }
    },
    async logout() {
      let refreshToken = window.sessionStorage.getItem('refresh_token')
      await this.$http.get('/logout?refresh_token=' + refreshToken)
      window.sessionStorage.clear()
      this.$router.push('/login')
    },
    toggleCollapse() {
      this.isCollapse = !(this.isCollapse)
      // 动态切换折叠块文字
      this.collapseText = (this.isCollapse ? '>>>' : '<<<')
      // 动态切换折叠块大小
      this.collapseWidth = (this.isCollapse ? '64px' : '200px')
    },
    saveNavStatus() {
      // 设置二级菜单高亮
      this.navStatus = this.$route.path
      // 保持当前页面链接到sessionStorage
      window.sessionStorage.setItem('navStatus', this.$route.path)
    }
  }
}
</script>

<style lang="less" scoped>
.el-container {
  height: 100%;
}

.el-header {
  background-color: #373d41;
  // div 分开两边 1
  display: flex;
  // div 分开两边 2
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: white;
  font-size: 20px;
  >div {
    display: flex;
    align-items: center;
    >span {
      margin-left: 15px;
    }
  }
}

.el-submenu .iconfont {
  margin-right: 10px;
}

.el-aside {
  background-color: #333744;
}

.el-main{
  background-color: #eaedf1;
}

// 去除子菜单右边框导致对不齐问题
.el-menu{
  border-right: 0;
}

.toggle-button{
  background-color: #4a5064;
  font-size: 16px;
  line-height: 26px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.1em;
  cursor: pointer;
}

.toggle-button:hover{
    background-color:#677397;
  }
</style>
