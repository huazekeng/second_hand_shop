<template>
  <div class="list">
    <div class="container" style="padding-top:25px;">
      <!-- <div class="clearfix" v-if="username">
        <el-button type="primary" icon="el-icon-plus" size="small" class="push-ini fr" @click="push">发帖</el-button>
      </div> -->
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom:15px;">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>站内论坛</el-breadcrumb-item>
      </el-breadcrumb>
      <el-row :gutter="20">
            <el-col :span="10" class="table-tool">
                <el-input placeholder="请输入搜索内容" v-model="queryInfo.search" clearable @clear="getList">
                    <el-button slot="append" icon="el-icon-search" @click="getList"></el-button>
                </el-input>
            </el-col>
            <el-col :span="2">
                <el-button v-if="username" type="primary" icon="el-icon-plus" @click="push">发帖</el-button>
            </el-col>
        </el-row>
      <div>
        <div class="items">
          <div class="item"  v-for="item in list" :key="item.id">
            <el-card class="box-card">
              <div slot="header">
                <el-tag size="mini">{{item.type}}</el-tag>
                <span style="font-size:12px;line-height:12px;display:inline-block;color:#999999"> <i><strong style="color:black;font-size:14px;">{{item.title}}</strong></i></span>
                <!-- <el-button style="float: right; padding: 3px 0" type="text" v-if="username!=null">回复</el-button> -->
                <el-button style="float: right; padding: 3px 0" type="text" @click="datail(item.id)">查 看</el-button>
              </div>
              <div class="text item">
                <strong>最新回复(hzk：2020-10-02 02:02:03)</strong>：楼主说得好
              </div>
            </el-card>
            <el-divider></el-divider>
          </div>
        </div>
        <!-- 分頁組件 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.currPage"
          :page-sizes="[5, 10, 30, 40]"
          :page-size="queryInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          background
          :total="rows">
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'
export default {
  name: 'forum-list',
  data() {
    return {
      queryInfo: {
        currPage: 1, // 當前頁碼
        pageSize: 10, // 每頁條目數
        search: ''
      },
      rows: 0,
      list: []
    }
  },
  components: {
  },
  computed: {
    ...mapState(['username', 'socket', 'socketMsg'])
  },
  watch: {
  },
  mounted() {
    this.getList()
  },
  methods: {
    push() {
      this.$router.push('/forum/publish')
    },
    getList() {
      this.axios.get(`forum/${this.queryInfo.currPage}/${this.queryInfo.pageSize}`, { params: { query: this.queryInfo.search } }).then(res => {
        this.rows = res.total
        this.list = res.rows
      })
    },
    // 每頁條目數切換
    handleSizeChange(newPageSize) {
      this.queryInfo.pageSize = newPageSize
      this.queryInfo.currPage = 1
      this.getList()
    },
    // 當前頁切換
    handleCurrentChange(newCurrPage) {
      this.queryInfo.currPage = newCurrPage
      this.getList()
    },
    datail(id) {
      this.$router.push({
        path: '/forum/detail',
        query: {
          id: id
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.el-tag{
  margin-right: 10px;
}
.push-ini{
  // float: right;
  margin-bottom: 15px;
}
.items{
  margin-top: 15px;
}
</style>
