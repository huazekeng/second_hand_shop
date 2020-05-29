<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>参数列表</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card>
            <el-alert type="warning" title="注意：只允许为第三级分类设置相关参数！" :closable="false" show-icon></el-alert>
            <el-row>
                <el-col>
                    <el-cascader
                        v-model="selectKeys"
                        :options="secondCategorys"
                        :props="{ expandTrigger: 'hover', label: 'catName', value: 'catId'}"
                        @change="secondCategorysChange"
                        clearable
                        filterable
                        placeholder="请选择商品分类"></el-cascader>
                </el-col>
            </el-row>

            <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="动态参数" name="many">
                    <el-button class="table-tool" size="mini" type="primary" :disabled="btnAbled" @click="attrDialogVisible = true;isEdit = false">添加参数</el-button>
                <el-table
                    :data="manyTable"
                    style="width: 100%" stripe border>
                    <el-table-column type="expand">
                      <template slot-scope="scope">
                        <el-tag class="tag-val" closable v-for="(item,i) in scope.row.attrVals" :key="i" @close="clostTag(scope.row,item)">
                          {{ item }}
                        </el-tag>
                        <el-input
                          class="saveTagInput"
                          v-if="scope.row.inputVisible"
                          v-model="scope.row.inputValue"
                          ref="saveTagInput"
                          size="small"
                          @keyup.enter.native="handleInputConfirm(scope.row)"
                          @blur="handleInputConfirm(scope.row)"
                        >
                        </el-input>
                        <el-button v-else class="button-new-tag" size="small" @click="showInput(scope.row)">+ New Tag</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column label="#" type="index"></el-table-column>
                    <el-table-column label="名称" prop="attrName"></el-table-column>
                    <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="primary" icon="el-icon-edit" size="mini" @click="editDialog(scope.row)"></el-button>
                        <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteById(scope.row.attrId)"></el-button>
                    </template>
                    </el-table-column>
                </el-table>
                </el-tab-pane>
                <el-tab-pane label="静态属性" name="only">
                    <el-button class="table-tool" size="mini" type="primary" :disabled="btnAbled" @click="attrDialogVisible = true;isEdit = false">添加属性</el-button>
                    <el-table
                    :data="onlyTable"
                    style="width: 100%" stripe border>
                    <el-table-column type="expand">
                      <template slot-scope="scope">
                        <el-tag class="tag-val" closable v-for="(item,i) in scope.row.attrVals" :key="i" @close="clostTag(scope.row,item)">
                          {{ item }}
                        </el-tag>
                        <el-input
                          class="saveTagInput"
                          v-if="scope.row.inputVisible"
                          v-model="scope.row.inputValue"
                          ref="saveTagInput"
                          size="small"
                          @keyup.enter.native="handleInputConfirm(scope.row)"
                          @blur="handleInputConfirm(scope.row)"
                        >
                        </el-input>
                        <el-button v-else class="button-new-tag" size="small" @click="showInput(scope.row)">+ New Tag</el-button>
                      </template>
                    </el-table-column>
                    <el-table-column label="#" type="index"></el-table-column>
                    <el-table-column label="名称" prop="attrName"></el-table-column>
                    <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="primary" icon="el-icon-edit" size="mini" @click="editDialog(scope.row)"></el-button>
                        <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteById(scope.row.attrId)"></el-button>
                    </template>
                    </el-table-column>
                </el-table>
                </el-tab-pane>
            </el-tabs>
        </el-card>

        <el-dialog
          :title="attrDialogTitle"
          :visible.sync="attrDialogVisible"
          width="50%" @close="reset">
          <el-form label-width="120px" :model="attrForm" :rules="formRules" ref="formRef">
          <el-form-item prop="attrName">
              <span slot="label"> 名称：</span>
              <el-input v-model="attrForm.attrName"></el-input>
          </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
          <el-button @click="attrDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addAttr()">确 定</el-button>
          </span>
      </el-dialog>
    </div>
</template>

<script>
import Category from '../../api/goods/category.js'
import Param from '../../api/goods/param.js'
export default {
  created() {
    this.getSecoindCategory()
  },
  data() {
    return {
      selectKeys: [],
      secondCategorys: [],
      activeName: 'many',
      manyTable: [],
      onlyTable: [],
      attrDialogVisible: false,
      attrForm: {
        attrId: '',
        attrName: '',
        catId: '',
        attrSel: ''
      },
      formRules: {
        'attrName': [{ required: true, message: '请输入名称', trigger: 'blur' }]
      },
      currRow: {},
      editForm: {
        attrId: '',
        attrName: ''
      },
      isEdit: false
    }
  },
  methods: {
    secondCategorysChange() {
      this.getTableData()
    },
    async getSecoindCategory() {
      const res = await Category.list(-1, -1, 3)
      // console.log(res)
      if (res.data.code !== 20000) {
        this.$message.error(res.data.message)
      }
      this.secondCategorys = res.data.data.rows
    },
    handleClick() {
      this.getTableData()
    },
    async getTableData() {
      console.log(this.selectKeys)
      if (this.selectKeys.length !== 3) {
        this.selectKeys = []
        return
      }
      const res = await Param.list(this.selectKeys[2], this.activeName)
      if (res.data.code !== 20000) {
        this.$message.error(res.data.message)
      } else {
        res.data.data.data.forEach(row => {
          row.attrVals = row.attrVals ? row.attrVals.split(',') : []
          row.inputVisible = false
          row.inputValue = ''
        })
        console.log(res.data.data.data)
        if (this.activeName === 'many') this.manyTable = res.data.data.data
        else this.onlyTable = res.data.data.data
      }
    },
    reset() {
      this.attrForm = {
        attrId: '',
        attrName: '',
        catId: '',
        attrSel: this.activeName
      }
    },
    async addAttr() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.attrForm.attrSel = this.activeName
        this.attrForm.catId = this.selectKeys[2]
        console.log(this.attrForm)
        let res = {}
        if (!this.isEdit) {
          res = await Param.add(this.attrForm)
        } else {
          this.editForm.attrId = this.currRow.attrId
          this.editForm.attrName = this.attrForm.attrName
          res = await Param.edit(this.editForm)
        }
        if (res.data.code !== 20000) {
          return this.$message.error(res.data.message)
        }
        this.$message.success(this.isEdit ? '编辑成功' : '添加成功')
        this.attrDialogVisible = false
        this.getTableData()
      })
    },
    editDialog(row) {
      this.isEdit = true
      this.attrForm.attrName = row.attrName
      this.attrDialogVisible = true
      this.currRow = row
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
        let res = await Param.delete(id)
        console.log(res)
        if (res.data.code === 20000) {
          this.$message.success('删除成功')
          this.getList()
        } else {
          this.$message.error(res.data.message)
          this.getTableData()
        }
      } else {
        this.$message.info('已取消删除')
      }
    },
    showInput(row) {
      row.inputVisible = true
      this.$nextTick(() => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },

    handleInputConfirm(row) {
      if (row.inputValue) {
        row.attrVals.push(row.inputValue)
        this.editAttrVals(row)
      }
      row.inputVisible = false
      row.inputValue = ''
    },

    async editAttrVals(row) {
      const attrVals = row.attrVals.join(',')
      const attrId = row.attrId
      const data = { attrId, attrVals }
      const res = await Param.edit(data)
      if (res.data.code !== 20000) this.$message.error(res.data.message)
      else this.$message.success('修改成功')
    },
    clostTag(row, item) {
      row.attrVals.splice(item, 1)
      this.editAttrVals(row)
    }
  },
  computed: {
    btnAbled() {
      if (this.selectKeys.length === 3) return false; else return true
    },
    attrDialogTitle() {
      if (this.activeName === 'many') return (this.isEdit ? '编辑' : '添加') + '动态参数'
      else return (this.isEdit ? '编辑' : '添加') + '静态属性'
    }
  }
}
</script>

<style lang="less" scoped>
.el-row {
    margin-top: 15px;
}
.tag-val {
    margin: 10px;
}
.saveTagInput {
    width: 200px;
}
</style>
