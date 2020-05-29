<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>权限管理</el-breadcrumb-item>
            <el-breadcrumb-item>权限列表</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card>
            <!-- 权限列表 -->
            <el-table
                :data="rightList"
                row-key="psId"
                style="width: 100%" stripe border>
                <el-table-column label="#" type="index"></el-table-column>
                <el-table-column label="名称" prop="psName"></el-table-column>
                <el-table-column label="前端URL" prop="frontUrl"></el-table-column>
                <el-table-column label="后端URL" prop="psUrl"></el-table-column>
                <el-table-column label="请求Method" prop="method"></el-table-column>
                <el-table-column label="权限等级">
                  <template  slot-scope="scope">
                    <el-tag  v-if="scope.row.psLevel == 0" type="primary">一级</el-tag>
                    <el-tag  v-if="scope.row.psLevel == 1" type="success">二级</el-tag>
                    <el-tag  v-if="scope.row.psLevel == 2" type="warning">三级</el-tag>
                  </template>
                </el-table-column>
            </el-table>
        </el-card>
    </div>
</template>

<script>
import Right from '../../api/power.js'

export default {
  // 页面初始化方法
  created() {
    this.getRights()
  },
  data() {
    return {
      rightList: []
    }
  },
  methods: {
    getRights: async function() {
      let res = await Right.list()
      if (res.data.code === 20000) {
        this.rightList = res.data.data.data
      } else {
        this.$message.error(res.data.message)
      }
    }
  }
}
</script>

<style lang="less" scoped>

</style>
