<template>
  <div class="detail">
    <div class="container" style="padding-top:25px;">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom:15px;">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/forum/list' }">站内论坛</el-breadcrumb-item>
        <el-breadcrumb-item>{{ forum.title }}</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>
        <div slot="header" class="header">
            {{forum.title}} <span> 回复数：<font style="color:#FF6600">{{rows}}</font></span>
        </div>

        <div class="forum-item clearfix">
          <div class="item" v-for="item in list" :key="item.id">
            <el-row>
              <el-col :span="3">
                <img src="/imgs/headImg.png" />
                <p class="name">{{item.username}}</p>
              </el-col>
              <el-col :span="16">
                <div class="content">
                  <p v-html="item.content"><p><br></p>
                </div>
              </el-col>
            </el-row>
            <p class="info fr">{{item.floor}} 楼 {{item.gmtCreate}}</p>
            <el-divider></el-divider>
          </div>
          <!-- <div class="item">
            <el-row>
              <el-col :span="3">
                <img src="/imgs/headImg.png" />
                <p class="name">hzk</p>
              </el-col>
              <el-col :span="16">
                <div class="content">
                  <p>分布式全局ID生成器作为分布式架构中重要的组成部分，在高并发场景下承载着分担数据库写瓶颈的压力。</p><p>之前实现过PHP+Swoole版，性能和稳定性在生产环境下运行良好。这次使用Java进行重写，目前测试情况表现还不错，下面我简要介绍下java版的项目情况。</p><p>&nbsp;</p><p><strong>技术架构：</strong>Netty + Zookeeper + Redis Protocol</p><ul><li>Netty：是一个基于NIO的客户、服务端编程框架(类似于swoole)。使用netty作为服务端应用接收客户端请求，对Redis协议数据进行编解码，响应redis客户端请求。</li><li>Zookeeper：主要两个作用。一是生成器服务每个节点需预先定义唯一serverId，并注册到zk，由zk统一管理集群节点连接状态；二是自增序列sequenceId可交由zk管理，集群中的自增序列在毫秒级内共用一个值，不过这涉及到共享锁的资源竞争和网络传输，性能很差，默认没开启，下面有具体的压测情况。</li></ul><p><br></p>
                </div>
              </el-col>
            </el-row>
            <p class="info fr">1 楼 2012-12-25 11:20:35</p>
            <el-divider></el-divider>
          </div>
          <div class="item">
            <el-row>
              <el-col :span="3">
                <img src="/imgs/headImg.png" />
                <p class="name">hzk</p>
              </el-col>
              <el-col :span="16">
                <div class="content">
                  <p>分布式全局ID生成器作为分布式架构中重要的组成部分，在高并发场景下承载着分担数据库写瓶颈的压力。</p><p>之前实现过PHP+Swoole版，性能和稳定性在生产环境下运行良好。这次使用Java进行重写，目前测试情况表现还不错，下面我简要介绍下java版的项目情况。</p><p>&nbsp;</p><p><strong>技术架构：</strong>Netty + Zookeeper + Redis Protocol</p><ul><li>Netty：是一个基于NIO的客户、服务端编程框架(类似于swoole)。使用netty作为服务端应用接收客户端请求，对Redis协议数据进行编解码，响应redis客户端请求。</li><li>Zookeeper：主要两个作用。一是生成器服务每个节点需预先定义唯一serverId，并注册到zk，由zk统一管理集群节点连接状态；二是自增序列sequenceId可交由zk管理，集群中的自增序列在毫秒级内共用一个值，不过这涉及到共享锁的资源竞争和网络传输，性能很差，默认没开启，下面有具体的压测情况。</li></ul><p><br></p>
                </div>
              </el-col>
            </el-row>
            <p class="info fr">1 楼 2012-12-25 11:20:35</p>
            <el-divider></el-divider>
          </div> -->
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

        <el-divider></el-divider>
        <div class="answer">
          <el-form>
          <el-form-item>
            <el-input type="textarea" v-model="content" rows="8" style="width: 66%;margin-left:13%;"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="margin-left:73%" :disabled="username == null || username == '' ? true : false" @click="pubilsh">回 复</el-button>
          </el-form-item>
          </el-form>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'
export default {
  name: 'forum-detail',
  data() {
    return {
      id: this.$route.query.id,
      queryInfo: {
        currPage: 1, // 當前頁碼
        pageSize: 10, // 每頁條目數
        forumId: this.$route.query.id
      },
      rows: 0,
      list: [],
      content: '',
      forum: {}
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
    this.datail()
    this.replayList()
  },
  methods: {
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
      this.replayList()
    },
    // 當前頁切換
    handleCurrentChange(newCurrPage) {
      this.queryInfo.currPage = newCurrPage
      this.replayList()
    },
    datail() {
      this.axios.get(`/forum/${this.id}`).then(res => {
        this.forum = res.data
      })
    },
    pubilsh() {
      if (!this.content) { this.$message.error('请填写回复内容'); return }
      this.axios.post('/replay', { username: this.username, content: this.content, forumId: this.id, floor: this.rows + 1 }).then(res => {
        this.$message.success('回复成功')
        this.content = ''
        this.replayList()
      })
    },
    replayList() {
      this.axios.get(`/replay/${this.queryInfo.currPage}/${this.queryInfo.pageSize}`, { params: { forumId: this.queryInfo.forumId } }).then(res => {
        this.list = res.rows
        this.rows = res.total
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.detail{
  .header{
    color: #333;
    font-size: 16px;
    line-height: 16px;
    font-weight: 600;
    span{
      color: #AAA;
      font-size: 12px;
      font-weight: 100;
      margin-left: 25px;
    }
  }
  .el-card{
    background-color: #F5F7FA;
  }
  // .el-col{
  //   background-color: #ffffff
  // }
  .el-tag{
    margin-right: 10px;
  }
  .push-ini{
    // float: right;
    margin-bottom: 15px;
  }
  .name{
    margin-top: 10px;
    margin-left: 40px;
    color: #2d64b3
  }
  .content {
    padding-top: 15px;
    padding-right: 100px;
    padding-left: 20px;
    background-color: #ffffff;
    min-height: 200px;
  }
  .forum-item{
    // position: relative;
    .item{
      margin-top: 15px;
      // margin-bottom: 20px;
    }
    .info {
      color: #999;
      // position: absolute;
      margin-right: 21%;
      margin-top: 10px;
      margin-bottom: 10px;
    }
    .el-divider{
      margin-top: 40px;
    }
  }
}
</style>
