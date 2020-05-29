<template>
  <div class="goods-publish">
    <div class="container">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom:15px;">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/goods/list' }">商品管理</el-breadcrumb-item>
        <el-breadcrumb-item>发布商品</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>
            <el-alert
                title="添加商品信息"
                type="info"
                center
                show-icon>
            </el-alert>
            <el-steps :space="200" :active="parseInt(stepActive)" finish-status="success" align-center>
                <el-step title="基本信息"></el-step>
                <el-step title="商品参数"></el-step>
                <el-step title="商品属性"></el-step>
                <el-step title="商品图片"></el-step>
                <el-step title="商品内容"></el-step>
            </el-steps>
            <el-form label-width="120px" :model="goodAddForm" :rules="goodAddRules" ref="goodAddRef" label-position="top">
                <el-tabs tab-position="left" @tab-click="tabClick" :before-leave="tabLeave"  v-model="stepActive">
                    <el-tab-pane label="基本信息" name="1">
                        <el-form-item prop="goodsName">
                            <span slot="label"> 商品名称：</span>
                            <el-input v-model="goodAddForm.goodsName"></el-input>
                        </el-form-item>
                        <el-form-item prop="subTitle">
                            <span slot="label"> 副标题：</span>
                            <el-input v-model="goodAddForm.subTitle"></el-input>
                        </el-form-item>
                        <el-form-item prop="goodsPrice">
                            <span slot="label"> 商品价格：</span>
                            <el-input type="number" v-model="goodAddForm.goodsPrice"></el-input>
                        </el-form-item>
                        <el-form-item prop="goodsNumber">
                            <span slot="label"> 商品数量：</span>
                            <el-input type="number" v-model="goodAddForm.goodsNumber"></el-input>
                        </el-form-item>
                        <el-form-item prop="goodsWeight">
                            <span slot="label"> 商品重量：</span>
                            <el-input type="number" v-model="goodAddForm.goodsWeight"></el-input>
                        </el-form-item>
                        <el-form-item label="商品分类：" prop="catIds">
                            <el-cascader
                                v-model="goodAddForm.catIds"
                                :options="secondCategorys"
                                :props="{ expandTrigger: 'hover', label: 'catName', value: 'catId'}"
                                @change="secondCategorysChange"
                                clearable
                                filterable>
                            </el-cascader>
                        </el-form-item>
                        <el-form-item>
                            <span slot="label"> 备注：</span>
                            <el-input type="textarea" v-model="goodAddForm.bakcup"></el-input>
                        </el-form-item>
                    </el-tab-pane>
                    <el-tab-pane label="商品参数" name="2">
                        <el-form-item v-for="(item, i) in manyAttr" :key="item.attrId">
                            <span slot="label" type="number"> {{ item.attrName }}：</span>
                            <el-checkbox-group v-model="theManyAttr[i].attrVals">
                                <el-checkbox v-for="(item1, i) in item.attrVals" :key="i" :label="item1"></el-checkbox>
                            </el-checkbox-group>
                        </el-form-item>
                    </el-tab-pane>
                    <el-tab-pane label="商品属性" name="3">
                        <el-form-item v-for="item in onlyAttr" :key="item.attrId">
                            <span slot="label" type="number"> {{ item.attrName }}：</span>
                            <el-input v-model="item.attrVals"></el-input>
                        </el-form-item>
                    </el-tab-pane>
                    <el-tab-pane label="商品图片" name="4">
                      <el-form-item label="主图：">
                        <el-upload
                          class="upload-demo"
                          :action="uploadAction"
                          :on-preview="handlePreview"
                          :on-remove="handleRemove"
                          :headers="uploadHeader"
                          :data="{'type': 1}"
                          list-type="picture"
                          :on-success="uploadSuccess">
                          <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
                     </el-form-item>
                     <el-form-item label="轮播图：">
                        <el-upload
                          class="upload-demo"
                          :action="uploadAction"
                          :on-preview="handlePreview"
                          :on-remove="handleRemove"
                          :headers="uploadHeader"
                          list-type="picture"
                          :data="{'type': 3}"
                          :multiple="false"
                          :limit="1"
                          :on-success="uploadSuccess">
                          <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
                     </el-form-item>
                     <el-form-item label="Banner图：">
                        <el-upload
                          class="upload-demo"
                          :action="uploadAction"
                          :on-preview="handlePreview"
                          :on-remove="handleRemove"
                          :headers="uploadHeader"
                          list-type="picture"
                          :data="{'type': 2}"
                          :limit="1"
                          :multiple="false"
                          :on-success="uploadSuccess">
                          <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
                     </el-form-item>
                     <el-form-item label="视频：">
                        <el-upload
                          class="upload-demo"
                          :action="uploadAction"
                          :on-preview="handlePreview"
                          :on-remove="handleRemove"
                          :headers="uploadHeader"
                          :multiple="false"
                          :limit="1"
                          list-type="picture"
                          :data="{'type': 4}"
                          :on-success="uploadSuccess">
                          <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
                     </el-form-item>
                    </el-tab-pane>
                    <el-tab-pane label="商品内容" name="5">
                        <quill-editor v-model="goodAddForm.goodsIntroduce"
                                        ref="myQuillEditor"
                                        :options="editorOption">
                        </quill-editor>
                        <div class="addBtn"><el-button type="primary" @click="addGoods">添加商品</el-button></div>
                    </el-tab-pane>
                </el-tabs>
            </el-form>
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
        </el-card>
    </div>
  </div>
</template>
<script>
import Category from '../api/goods/category.js'
import Goods from '../api/goods/goods.js'
import Param from '../api/goods/param.js'
import Editor from '../api/editor.js'
import _ from 'lodash'
export default {
  name: 'goods-publish',
  data() {
    var validName = (rule, value, cb) => {
      if (this.nameDisabled) {
        return cb()
      }
      Goods.checkName(value).then(res => {
        if (!res.data) {
          return cb()
        }
        cb(new Error('该商品名称已经存在'))
      })
    }
    return {
      falseFlag: false,
      stepActive: '1',
      uploadAction: 'http://localhost/api/goods/upload',
      goodAddForm: {
        goodsName: '',
        catIds: [],
        goodsIntroduce: '',
        goodsPrice: '',
        goodsNumber: '',
        goodsWeight: '',
        goodsAttr: [],
        goodsPic: [],
        bakcup: '',
        subTitle: '',
        bannerPic: '',
        slidePic: '',
        videoUrl: ''
      },
      goodAddRules: {
        goodsName: [{ required: true, message: '请输入商品名称', trigger: 'blur' },
          { validator: validName, trigger: 'blur' }],
        subTitle: [{ required: true, message: '请输入商品副标题', trigger: 'blur' }],
        catIds: [{ required: true, message: '请选择商品分类', trigger: 'blur' }],
        goodsPrice: [{ required: true, message: '请输入商品价格', trigger: 'blur' }],
        goodsNumber: [{ required: true, message: '请输入商品数量', trigger: 'blur' }],
        goodsWeight: [{ required: true, message: '请输入商品重量', trigger: 'blur' }]
      },
      secondCategorys: [],
      manyAttr: [],
      onlyAttr: [],
      theManyAttr: [],
      theOnlyAttr: [],
      editorOption: Editor,
      dialogVisible: false,
      preViewImg: '',
      videVisable: false
    }
  },
  created() {
    this.getCategoryList()
    console.log(this.editorOption)
  },
  methods: {
    tabClick(obj) {
      this.stepActive = obj.name
    },
    secondCategorysChange(oldValue) {
      // if (this.goodAddForm.catIds.length)
      this.getOnlyAttrs()
      this.getManyAttrs()
    },
    async getCategoryList() {
      const res = await Category.list(-1, -1, 3)
      this.secondCategorys = res.rows
    },
    tabLeave(activeName, oldActiveName) {
      if (oldActiveName === '1') {
        if (this.goodAddForm.catIds.length !== 3) {
          this.$message.info('请先选择商品分类')
          this.stepActive = oldActiveName
          return false
        }
      }
    },
    async getManyAttrs() {
      const res = await Param.list(this.catId, 'many')
      this.manyAttr = res.data
      this.handlerAttrs(this.manyAttr)
      this.theManyAttr = _.cloneDeep(this.manyAttr)
    },
    async getOnlyAttrs() {
      const res = await Param.list(this.catId, 'only')
      this.onlyAttr = res.data
      // this.handlerAttrs(this.onlyAttr)
      // this.theOnlyAttr = _.cloneDeep(this.onlyAttr)
    },
    handlerAttrs(data) {
      data.forEach(element => {
        element.attrVals = element.attrVals ? element.attrVals.split(',') : []
      })
    },
    handlePreview(obj) {
      this.dialogVisible = true
      this.preViewImg = obj.response.data.url
      if (obj.response.data.type === 4) {
        this.videVisable = true
      } else {
        this.videVisable = false
      }
    },
    handleRemove(obj) {
      const path = obj.response.data.url
      if (obj.response.data.type === 1) {
        const index = this.goodAddForm.goodsPic.indexOf(path)
        this.goodAddForm.goodsPic.splice(index, 1)
      } else if (obj.response.data.type === 2) {
        this.goodAddForm.bannerPic = ''
      } else if (obj.response.data.type === 3) {
        this.goodAddForm.slidePic = ''
      } else if (obj.response.data.type === 4) {
        this.goodAddForm.videoUrl = ''
      }
    },
    async addGoods() {
      this.$refs.goodAddRef.validate(async valid => {
        // console.log(this.theManyAttr)
        // console.log(this.onlyAttr)
        if (!valid) return
        if (this.goodAddForm.goodsPic.length === 0) return this.$message.info('请上传商品图')
        this.goodAddForm.goodsAttr = []
        this.theManyAttr.forEach(many => {
          console.log(many)
          const attr = { attrId: many.attrId, attrValue: many.attrVals.join(',') }
          this.goodAddForm.goodsAttr.push(attr)
        })
        this.onlyAttr.forEach(many => {
          console.log(many)
          const attr = { attrId: many.attrId, attrValue: many.attrVals }
          this.goodAddForm.goodsAttr.push(attr)
        })
        // console.log(this.goodAddForm)
        await Goods.add(this.goodAddForm)
        this.$message.success('添加成功')
        this.$router.push('/goods/list')
      })
    },
    uploadSuccess(response, file, fileList) {
      if (response.code === 20000) {
        this.$message.success('上传成功')
        if (response.data.type === 1) this.goodAddForm.goodsPic.push(response.data.path)
        else if (response.data.type === 2) this.goodAddForm.bannerPic = response.data.path
        else if (response.data.type === 3) this.goodAddForm.slidePic = response.data.path
        else if (response.data.type === 4) this.goodAddForm.videoUrl = response.data.path
      } else {
        this.$message.error('上传失败')
      }
    },
    closeBefore() {
      document.getElementById('video').pause()
    }
  },
  computed: {
    catId() {
      return this.goodAddForm.catIds[2]
    },
    uploadHeader() {
      const Authorization = window.sessionStorage.getItem('token')
      return { Authorization }
    }
  },
  mounted() {
    Editor.initButton()
    // console.log(this.$quill)
    Editor.register(this.$quill)
  }
}
</script>
<style lang="scss">
.el-tabs{
  margin-top: 15px;
}
.ql-editor{
  height: 300px;
}
.addBtn{
  margin-top: 25px;
  text-align: right;
}
</style>
