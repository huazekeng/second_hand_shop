<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>商品列表</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card class="box-card">
            <el-row :gutter="20">
                <el-col :span="10" class="table-tool">
                    <el-input placeholder="请输入搜索内容" v-model="queryInfo.search" clearable @clear="getList">
                        <el-button slot="append" icon="el-icon-search" @click="getList"></el-button>
                    </el-input>
                </el-col>
                <!-- <el-col :span="2">
                    <el-button type="primary" @click="toGoodsAdd">添加商品</el-button>
                </el-col> -->
            </el-row>

            <el-table
                :data="goodList"
                style="width: 100%" stripe border>
                <el-table-column label="#" type="index"></el-table-column>
                <el-table-column label="商品名称" prop="goodsName"></el-table-column>
                <el-table-column label="商品价格（元）" prop="goodsPrice"></el-table-column>
                <el-table-column label="商品数量" prop="goodsNumber"></el-table-column>
                <el-table-column label="商品重量" prop="goodsWeight"></el-table-column>
                <el-table-column label="发布者" prop="createUser"></el-table-column>
                <el-table-column label="创建时间">
                    <template slot-scope="scope">
                        {{ scope.row.gmtCreate | dateFormat }}
                    </template>
                </el-table-column>
                <!-- <el-table-column label="操作">
                <template slot-scope="scope">
                     <el-button type="primary" icon="el-icon-edit" size="mini" @click="editDialog(scope.row)"></el-button>
                     <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteById(scope.row.goodsId)"></el-button>
                </template>
                </el-table-column> -->
                <el-table-column label="审核状态">
                  <template slot-scope="scope">
                    <el-switch
                      :active-value="active_value"
                      :inactive-value="inactive_value"
                      v-model="scope.row.goodsState"
                      @change="stateChanged(scope.row, 1)">
                    </el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="是否促销">
                  <template slot-scope="scope">
                    <el-switch
                      :active-value="active_value"
                      :inactive-value="inactive_value"
                      v-model="scope.row.isPromote"
                      @change="stateChanged(scope.row, 2)">
                    </el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="是否banner">
                  <template slot-scope="scope">
                    <el-switch
                      :active-value="active_value"
                      :inactive-value="inactive_value"
                      v-model="scope.row.isBanner"
                      @change="stateChanged(scope.row, 3)">
                    </el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="是否轮播">
                  <template slot-scope="scope">
                    <el-switch
                      :active-value="active_value"
                      :inactive-value="inactive_value"
                      v-model="scope.row.isSlide"
                      @change="stateChanged(scope.row, 4)">
                    </el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="是否广告">
                  <template slot-scope="scope">
                    <el-switch
                      :active-value="active_value"
                      :inactive-value="inactive_value"
                      v-model="scope.row.isAds"
                      @change="stateChanged(scope.row, 5)">
                    </el-switch>
                  </template>
                </el-table-column>
            </el-table>

            <!-- 分頁組件 -->
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="queryInfo.currPage"
              :page-sizes="[5, 10, 30, 40]"
              :page-size="queryInfo.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="rows">
            </el-pagination>
        </el-card>
    </div>
</template>

<script>
import Goods from '../../api/goods/goods.js'

export default {
  created() {
    this.getList()
  },
  data() {
    return {
      queryInfo: {
        currPage: 1, // 當前頁碼
        pageSize: 5, // 每頁條目數
        search: ''
      },
      rows: 0, // 縂條目數
      goodList: [], // 商品列表,
      active_value: 1,
      inactive_value: 0 // 为开关设置不开启的值
    }
  },
  methods: {
    async getList() {
      const res = await Goods.list(this.queryInfo)
      if (res.data.code === 20000) {
        this.goodList = res.data.data.rows
        this.rows = res.data.data.total
      } else {
        this.$message({ 'message': res.data.message,
          'type': 'info'
        })
      }
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
    async deleteById(id) {
      const res = await this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => {
        return err
      })
      // console.log(res)
      if (res === 'confirm') {
        let res = await Goods.delete(id)
        if (res.data.code === 20000) {
          this.$message.success('删除成功')
          this.getList()
        } else {
          this.$message.error(res.data.message)
        }
      } else {
        this.$message.info('已取消删除')
      }
    },
    toGoodsAdd() {
      this.$router.push('/goods/add')
    },
    stateChanged(data, type) {
      this.$http.put('/backend-goods', data).then(res => {
        // console.log(data)
        // console.log(res)
        if (res.data.code === 20000) {
          if (type === 1) {
            if (data.goodsState === 1) {
              this.$message.success('修改状态审核通过')
            } else {
              this.$message.success('修改状态审核不通过')
            }
          } else if (type === 2) {
            if (data.isPromote === 1) {
              this.$message.success('修改状态为促销')
            } else {
              this.$message.success('修改状态为非促销')
            }
          } else if (type === 3) {
            if (data.isBanner === 1) {
              this.$message.success('请通知发布者添加banner图')
            } else {
              this.$message.success('已经取消banner展示')
            }
          } else if (type === 4) {
            if (data.isSlide === 1) {
              this.$message.success('请通知发布者添加轮播图')
            } else {
              this.$message.success('已经取消轮播图展示')
            }
          } else if (type === 5) {
            if (data.isSlide === 1) {
              this.$message.success('请通知发布者添加广告图')
            } else {
              this.$message.success('已经取消广告图展示')
            }
          }
        } else {
          this.$message.error(res.data.message)
          data.isDeleted = !data.isDeleted
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>

</style>
