<template>
  <div class="goods-list">
    <div class="container">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom:15px;">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>求购管理</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="list-box">
        <el-row :gutter="20">
            <el-col :span="10" class="table-tool">
                <el-input placeholder="请输入搜索内容" v-model="queryInfo.search" clearable @clear="getList">
                    <el-button slot="append" icon="el-icon-search" @click="getList"></el-button>
                </el-input>
            </el-col>
            <el-col :span="2">
                <el-button type="primary" @click="addDialogShow = true">发布求购</el-button>
            </el-col>
        </el-row>
        <el-table
          :data="tableData"
          style="width: 100%">
          <el-table-column label="#" type="index"></el-table-column>
            <el-table-column label="标题" prop="title"></el-table-column>
            <el-table-column label="接受价格（元）">
              <template  slot-scope="scope">
                {{ scope.row.min }} - {{ scope.row.max}}
              </template>
            </el-table-column>
            <el-table-column label="描述" prop="description"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="mini" type="primary"  @click="operateRequire(scope.row.id, 0)" v-if="scope.row.status == 1">下架</el-button>
                <el-button size="mini" type="primary"  @click="operateRequire(scope.row.id, 1)" v-else>上架</el-button>
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
            title="发布求购"
            :visible.sync="addDialogShow"
            width="50%"
            @close="closeBefore">
            <el-form ref="form" :model="form" label-width="120px" :rules="rules">
              <el-form-item label="标题：" prop="title">
                <el-input v-model="form.title"></el-input>
              </el-form-item>
              <el-form-item label="接受价格：">
                <el-input class="price" v-model="form.min" type="number"></el-input> - <el-input class="price" v-model="form.max" type="number"></el-input>
              </el-form-item>
              <el-form-item label="联系方式：" prop="telephone">
                <el-input v-model="form.telephone"></el-input>
              </el-form-item>
              <el-form-item label="描述：" prop="description">
                <el-input v-model="form.description" type="textarea"></el-input>
              </el-form-item>
              <el-form-item label="图片：">
                <el-upload
                      class="upload-demo"
                      :action="uploadAction"
                      :on-preview="handlePreview"
                      :on-remove="handleRemove"
                      :headers="uploadHeader"
                      :data="{'type': 5}"
                      list-type="picture"
                      :on-success="uploadSuccess">
                      <el-button size="small" type="primary">点击上传</el-button>
                    </el-upload>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="publish">发 布</el-button>
                <el-button @click="addDialogShow = false">取 消</el-button>
            </span>
        </el-dialog>
         <el-dialog
                title="图片预览"
                :visible.sync="dialogVisible"
                width="50%"
                @close="closeBefore">
                <video id="video" :src="preViewImg" style="width:100%" controls="controls" v-if="videVisable">您的浏览器不支持 video 标签。</video>
                <img :src="preViewImg" style="width:100%" v-else>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                </span>
            </el-dialog>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'require-list',
  data() {
    var validPhone = (rule, value, cb) => {
      if ((/^1[3456789]\d{9}$/.test(value))) {
        return cb()
      }
    }
    return {
      uploadAction: 'http://localhost/api/goods/upload',
      tableData: [],
      queryInfo: {
        currPage: 1, // 當前頁碼
        pageSize: 5, // 每頁條目數
        search: ''
      },
      rows: 0,
      fileList: [],
      dialogVisible: false,
      preViewImg: '',
      addDialogShow: false,
      form: {
        img: '',
        title: '',
        description: '',
        min: '',
        max: '',
        telephone: ''
      },
      rules: {
        telephone: [{ required: true, message: '请输入联系', trigger: 'blur' },
          { validator: validPhone, trigger: 'blur' }],
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        description: [{ required: true, message: '请输入描述', trigger: 'blur' }]
      }
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
      this.axios.get(`require/${this.queryInfo.currPage}/${this.queryInfo.pageSize}`, { params: { query: this.queryInfo.search } }).then(res => {
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
    handlePreview(obj) {
      this.dialogVisible = true
      this.preViewImg = obj.response.data.url
    },
    uploadPic(row, type) {
      this.dialogVisible = true
    },
    uploadSuccess(response, file, fileList) {
      if (response.code === 20000) {
        this.form.img = response.data.url
      } else {
        this.$message.error('上传失败')
      }
    },
    handleRemove() {
      this.form.img = ''
    },
    closeBefore() {
      this.form = {
        img: '',
        title: '',
        description: '',
        min: '',
        max: '',
        telephone: ''
      }
      this.fileList = []
    },
    async publish() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        await this.axios.post('/require', this.form)
        this.$message.success('发布成功')
      })
    },
    operateRequire(id, status) {
      this.axios.put('/require', { id, status }).then(res => {
        this.$message.success(status === 0 ? '下架成功' : '上架成功')
        this.getList()
      })
    }
  }
}
</script>
<style lang="scss" scoped>
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
.price{
  width: 48.9%;
}
</style>
