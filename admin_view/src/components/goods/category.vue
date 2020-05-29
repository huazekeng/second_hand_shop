<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>分类列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-card>
             <el-row class="table-tool">
                <el-col><el-button type="primary" @click="showAddDialog">添加分类</el-button></el-col>
            </el-row>

            <!--<el-table
                :data="categoryList"
                row-key="catId"
                style="width: 100%" stripe border>
                <el-table-column label="#" type="index"></el-table-column>
                <el-table-column label="名称" prop="catName"></el-table-column>
            </el-table> -->

            <tree-table
                :data="categoryList"
                :columns="columns"
                show-index
                border
                :selection-type="false"
                :expand-type="false"
                index-text="#">
                <template slot="isok" slot-scope="scope">
                    <i class="el-icon-success" v-if="scope.row.isDeleted == 0"></i>
                    <i class="el-icon-error" v-if="scope.row.isDeleted == 1"></i>
                </template>
                <template slot="level" slot-scope="scope">
                    <el-tag  v-if="scope.row.catLevel == 0" type="primary">一级</el-tag>
                    <el-tag  v-if="scope.row.catLevel == 1" type="success">二级</el-tag>
                    <el-tag  v-if="scope.row.catLevel == 2" type="warning">三级</el-tag>
                </template>
                <template slot="operation" slot-scope="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="editDialog(scope.row)">编辑</el-button>
                     <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteById(scope.row.catId)">删除</el-button>
                </template>
            </tree-table>

            <!-- 分頁組件 -->
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currPage"
              :page-sizes="[5, 10, 30, 40]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>

            <el-dialog
                :title="categoryDialogTitle"
                :visible.sync="calegoryDialogVisible"
                width="50%" @close="reset">
                <el-form label-width="120px" :model="categoryForm" :rules="formRules" ref="formRef">
                <el-form-item prop="catName">
                    <span slot="label"> 角色名称：</span>
                    <el-input v-model="categoryForm.catName"></el-input>
                </el-form-item>
                <el-form-item prop="order">
                    <span slot="label"> 排序：</span>
                    <el-input v-model="categoryForm.orderLevel"></el-input>
                </el-form-item>
                <el-form-item label="父分类：">
                     <el-cascader
                        v-model="categoryKeys"
                        :options="secondCategorys"
                        :props="{ expandTrigger: 'hover', label: 'catName', value: 'catId',checkStrictly: true}"
                        @change="secondCategorysChange"
                        clearable
                        filterable></el-cascader>
                </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                <el-button @click="calegoryDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addCategory()">确 定</el-button>
                </span>
            </el-dialog>
        </el-card>
    </div>
</template>

<script>
import Category from '../../api/goods/category.js'
export default {
  created() {
    this.getList()
  },
  data() {
    return {
      currPage: 1,
      pageSize: 5,
      total: 0,
      categoryList: [],
      calegoryDialogVisible: false,
      categoryDialogTitle: '添加分类',
      categoryForm: {
        catId: '',
        catName: '',
        catLevel: 0,
        catPid: 0,
        orderLevel: 0
      },
      isEdit: false,
      formRules: {
        'catName': [{ required: true, message: '请输入分类名称', trigger: 'blur' }],
        'orderLevel': [{ required: true, message: '请输入排序级别', trigger: 'blur' }]
      },
      secondCategorys: [],
      categoryKeys: [],
      columns: [
        {
          label: '分类名称',
          prop: 'catName'
        },
        {
          label: '是否有效',
          type: 'template',
          template: 'isok'
        },
        {
          label: '级别',
          type: 'template',
          template: 'level'
        },
        {
          label: '排序',
          prop: 'orderLevel'
        },
        {
          label: '操作',
          type: 'template',
          template: 'operation'
        }
      ],
      currCategory: {
        catId: '',
        catName: '',
        catLevel: 0,
        catPid: 0
      }
    }
  },
  methods: {
    async getList() {
      const res = await Category.list(this.currPage, this.pageSize, 3)
      if (res.data.code !== 20000) {
        this.$message.error(res.data.message)
      }
      console.log(res)
      this.categoryList = res.data.data.rows
      this.total = res.data.data.total
    },
    // 每頁條目數切換
    handleSizeChange(newPageSize) {
      this.pageSize = newPageSize
      this.currPage = 1
      this.getList()
    },
    // 當前頁切換
    handleCurrentChange(newCurrPage) {
      this.currPage = newCurrPage
      this.getList()
    },
    reset() {
      this.categoryForm = {
        catId: '',
        catName: '',
        catLevel: 0,
        catPid: 0
      }
      this.categoryKeys = []
    },
    showAddDialog() {
      this.categoryDialogTitle = '添加分类'
      this.calegoryDialogVisible = true
      this.isEdit = false
      this.getSecoindCategory()
    },
    async addCategory() {
      this.$refs.formRef.validate(async valid => {
        if (valid) {
          if (!this.validLevel()) {
            this.$message.error('请不要跨级修改')
            return
          }
          let res = {}
          if (!this.isEdit) res = await Category.add(this.categoryForm)
          else res = await Category.edit(this.categoryForm)
          if (res.data.code !== 20000) {
            return this.$message.error(res.data.message)
          }
          this.$message.success(this.isEdit ? '编辑成功' : '添加成功')
          this.calegoryDialogVisible = false
          this.getList()
        }
      })
    },
    async getSecoindCategory() {
      const res = await Category.list(-1, -1, 2)
      // console.log(res)
      if (res.data.code !== 20000) {
        this.$message.error(res.data.message)
      }
      this.secondCategorys = res.data.data.rows
    },
    secondCategorysChange() {
      if (!this.validLevel()) {
        this.$message.error('请不要跨级修改')
      }
      // console.log(this.categoryKeys)
      if (this.categoryKeys.length === 0) {
        this.categoryForm.catLevel = 0
        this.categoryForm.catPid = 0
      } else {
        this.categoryForm.catLevel = this.categoryKeys.length
        this.categoryForm.catPid = this.categoryKeys[this.categoryKeys.length - 1]
      }
    },
    async editDialog(row) {
      let temp = JSON.parse(JSON.stringify(row))
      this.categoryForm = {
        catId: temp.catId,
        catName: temp.catName,
        catLevel: temp.catLevel,
        catPid: temp.catPid
      }
      this.getSecoindCategory()
      this.categoryDialogTitle = '编辑分类'
      this.calegoryDialogVisible = true
      this.isEdit = true
      this.currCategory = row
      if (row.catLevel === 1) {
        this.categoryKeys = [row.catPid]
      } else if (row.catLevel === 2) {
        const res = await Category.getInfo(row.catPid)
        if (res.data.code !== 20000) {
          return this.$message.error(res.data.message)
        }
        this.categoryKeys = [res.data.data.data.catPid, row.catPid]
      }
    },
    validLevel() {
      if (this.isEdit && this.categoryKeys.length !== this.currCategory.catLevel) {
        return false
      }
      return true
    },
    async deleteById(id) {
      const res = await this.$confirm('是否继续删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => {
        return err
      })
      // console.log(res)
      if (res === 'confirm') {
        let res = await Category.delete(id)
        console.log(res)
        if (res.data.code === 20000) {
          this.$message.success('删除成功')
          this.getList()
        } else {
          this.$message.error(res.data.message)
        }
      } else {
        this.$message.info('已取消删除')
      }
    }
  }
}
</script>

<style lang="less" scoped>
.el-icon-success{
    color: lightgreen;
}
.el-icon-error{
    color: red;
}
.el-cascader{
    width: 100%;
}
</style>
