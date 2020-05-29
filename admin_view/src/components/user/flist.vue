
<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>用户管理</el-breadcrumb-item>
            <el-breadcrumb-item>用户列表(前台)</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card class="box-card">

          <!-- 搜索框 -->
            <el-row :gutter="20">
                <el-col :span="10" class="table-tool">
                    <el-input placeholder="请输入内容" v-model="query" clearable>
                        <el-button slot="append" icon="el-icon-search" @click="getUsers"></el-button>
                    </el-input>
                </el-col>
                <!-- <el-col :span="2">
                    <el-button type="primary" @click="addDialogVisible = true; nameDisabled = false;
                      userDialogTitle = '添加用户'">添加用户</el-button>
                </el-col> -->
                 <el-col :span="2">
                    <el-button type="primary">导入用户</el-button>
                </el-col>
            </el-row>

            <!-- 用戶列表 -->
            <el-table
                :data="userList"
                style="width: 100%" stripe border>
                <el-table-column label="#" type="index"></el-table-column>
                <el-table-column label="账号" prop="username"></el-table-column>
                <el-table-column label="昵称" prop="nickName"></el-table-column>
                <el-table-column label="专业班级" prop="schoole"></el-table-column>
                <el-table-column label="邮箱" prop="userEmail"></el-table-column>
                <el-table-column label="电话" prop="userTel"></el-table-column>
                <el-table-column label="QQ" prop="userQq"></el-table-column>
                <el-table-column label="信用度" prop="confidence"></el-table-column>
            </el-table>

            <!-- 分頁組件 -->
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currPage"
              :page-sizes="[5, 10, 30, 40]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="rows">
            </el-pagination>
        </el-card>
    </div>
</template>

<script>

export default {
  data() {
    return {
      currPage: 1, // 當前頁碼
      pageSize: 5, // 每頁條目數
      rows: 0, // 縂條目數
      userList: [], // 用戶列表
      query: '', // 查詢參數
      active_value: 0,
      inactive_value: 1, // 为开关设置不开启的值
      dialogVisible: false, // 添加用户框是否可见
      currUser: {},
      addFormRules: {
        mgName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '用户名长度3-10', trigger: 'blur' } ]
      },
      apiBase: 'http://localhost/api'
    }
  },
  // 頁面初始化
  created() {
    this.getUsers()
  },
  methods: {
    // 獲取用戶列表
    async getUsers() {
      const res = await this.$http.get(this.apiBase + '/user/' + this.currPage + '/' + this.pageSize, { params: { query: this.query } })
      console.log(res.data)
      if (res.data.code === 20000) {
        this.userList = res.data.data.rows
        this.rows = res.data.data.total
      } else {
        this.$message({ 'message': res.data.message,
          'type': 'info'
        })
      }
    },
    // 每頁條目數切換
    handleSizeChange(newPageSize) {
      this.pageSize = newPageSize
      this.currPage = 1
      this.getUsers()
    },
    // 當前頁切換
    handleCurrentChange(newCurrPage) {
      this.currPage = newCurrPage
      this.getUsers()
    }
  }
}
</script>

<style lang="less" scoped>

</style>
