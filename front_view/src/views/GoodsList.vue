<template>
  <div class="goods-list">
    <div class="container">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="list-box">
        <el-row :gutter="20">
            <el-col :span="10" class="table-tool">
                <el-input placeholder="请输入搜索内容" v-model="queryInfo.search" clearable @clear="getList">
                    <el-button slot="append" icon="el-icon-search" @click="getList"></el-button>
                </el-input>
            </el-col>
            <el-col :span="2">
                <el-button type="primary" @click="toGoodsAdd">添加商品</el-button>
            </el-col>
        </el-row>
        <el-table
          :data="tableData"
          style="width: 100%">
          <el-table-column label="#" type="index"></el-table-column>
            <el-table-column label="商品名称" prop="goodsName"></el-table-column>
            <el-table-column label="商品价格（元）" prop="goodsPrice"></el-table-column>
            <el-table-column label="商品数量" prop="goodsNumber"></el-table-column>
            <el-table-column label="商品重量" prop="goodsWeight"></el-table-column>
            <el-table-column label="审核状态">
              <template slot-scope="scope">
                {{ scope.row.goodsState == 0 ? '未通过' : '通过' }}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="350">
              <template slot-scope="scope">
                <el-button size="mini" type="primary" @click="uploadPic(scope.row, 1)">Banner图</el-button>
                <el-button size="mini" type="primary"  @click="uploadPic(scope.row, 2)">轮播图</el-button>
                <el-button size="mini" type="primary"  @click="uploadPic(scope.row, 3)">广告图</el-button>
                <el-button size="mini" type="primary"  @click="operateGoods(scope.row.goodsId, 0)" v-if="scope.row.status == 1">下架</el-button>
                <el-button size="mini" type="primary"  @click="operateGoods(scope.row.goodsId, 1)" v-else>上架</el-button>
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
          background
          :total="rows">
        </el-pagination>

        <el-dialog
                :title="dialogTitle"
                :visible.sync="dialogVisible"
                width="50%">
                <el-upload
                          class="upload-demo"
                          :action="uploadAction"
                          :on-preview="handlePreview"
                          :headers="uploadHeader"
                          :on-remove="handleRemove"
                          list-type="picture"
                          :file-list="fileList"
                          :data="param"
                          :on-success="uploadSuccess">
                          <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                </span>
            </el-dialog>

            <el-dialog
                title="图片预览"
                :visible.sync="preVisible"
                width="50%">
                <img :src="preViewImg" style="width:100%">
                <span slot="footer" class="dialog-footer">
                    <el-button @click="preVisible = false">取 消</el-button>
                </span>
            </el-dialog>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'forum-publish',
  data() {
    return {
      uploadAction: 'http://localhost/api/goods/uploadPic',
      tableData: [],
      queryInfo: {
        currPage: 1, // 當前頁碼
        pageSize: 10, // 每頁條目數
        search: ''
      },
      rows: 0,
      fileList: [],
      dialogTitle: '上传Banner图',
      dialogVisible: false,
      preVisible: false,
      preViewImg: '',
      param: {},
      currentRow: {}
    }
  },
  components: {
  },
  computed: {
    uploadHeader() {
      const Authorization = window.sessionStorage.getItem('token')
      return { Authorization }
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    getList() {
      this.axios.get(`goods/${this.queryInfo.currPage}/${this.queryInfo.pageSize}`, { params: { query: this.queryInfo.search } }).then(res => {
        this.rows = res.total
        this.tableData = res.rows
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
    toGoodsAdd() {
      this.$router.push('publish')
    },
    handlePreview(obj) {
      this.preVisible = true
      if (obj.response) this.preViewImg = obj.response.data.url
      else this.preViewImg = obj.url
    },
    uploadPic(row, type) {
      this.currentRow = row
      if (type === 1) {
        this.dialogTitle = '上传Banner图'
        this.fileList = []
        if (row.bannerPic) {
          this.fileList = [{}]
          this.fileList[0].name = 'Banner图'
          this.fileList[0].url = row.bannerPic
        }
      } else if (type === 2) {
        this.dialogTitle = '上传轮播图'
        this.fileList = []
        if (row.slidePic) {
          this.fileList = [{}]
          this.fileList[0].name = '轮播图'
          this.fileList[0].url = row.slidePic
        }
      } else if (type === 3) {
        this.dialogTitle = '上传广告图'
        this.fileList = []
        if (row.adsPic) {
          this.fileList = [{}]
          this.fileList[0].name = '广告图'
          this.fileList[0].url = row.adsPic
        }
      }
      this.dialogVisible = true
      this.param.type = type
      this.param.id = row.goodsId
    },
    uploadSuccess(response, file, fileList) {
      if (response.code === 20000) {
        // this.fileList = this.fileList.splice(1, 1)
        this.fileList = []
        this.fileList[0] = file
        this.$message.success('上传成功')
        if (response.data.type === 1) this.currentRow.bannerPic = response.data.url
        else if (response.data.type === 2) this.currentRow.slidePic = response.data.url
        else if (response.data.type === 3) this.currentRow.adsPic = response.data.url
      } else {
        this.$message.error('上传失败')
      }
    },
    handleRemove() {
      this.fileList = []
    },
    operateGoods(id, status) {
      this.axios.put('/goods', { goodsId: id, status: status }).then(res => {
        this.$message.success(status === 0 ? '下架成功' : '上架成功')
        this.getList()
      })
    }
  }
}
</script>
<style lang="scss">
.goods-list{
  .list-box{
    padding-top: 25px;
    padding-bottom: 25px;
    .el-table {
      margin-top: 15px;
      margin-bottom: 25px;
    }
    .el-pagination.is-background .el-pager li:not(.disabled).active {
      background-color: #FF6600;
    }
  }
}
</style>
