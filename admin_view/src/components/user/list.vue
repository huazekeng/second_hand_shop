
<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>用户管理</el-breadcrumb-item>
            <el-breadcrumb-item>用户列表(后台)</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card class="box-card">

          <!-- 搜索框 -->
            <el-row :gutter="20">
                <el-col :span="10" class="table-tool">
                    <el-input placeholder="请输入内容" v-model="query" clearable>
                        <el-button slot="append" icon="el-icon-search" @click="getUsers"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button type="primary" @click="addDialogVisible = true; nameDisabled = false;
                      userDialogTitle = '添加用户'">添加用户</el-button>
                </el-col>
                 <!-- <el-col :span="2">
                    <el-button type="primary">导入用户</el-button>
                </el-col> -->
            </el-row>

            <!-- 用戶列表 -->
            <el-table
                :data="userList"
                style="width: 100%" stripe border>
                <el-table-column label="#" type="index"></el-table-column>
                <el-table-column label="姓名" prop="mgName"></el-table-column>
                <el-table-column label="郵箱" prop="mgEmail"></el-table-column>
                <el-table-column label="電話" prop="mgMobile"></el-table-column>
                <el-table-column label="狀態">
                  <template slot-scope="scope">
                    <el-switch
                      :active-value="active_value"
                      :inactive-value="inactive_value"
                      v-model="scope.row.isDeleted"
                      @change="stateChanged(scope.row)">
                    </el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                <template slot-scope="scope">
                     <el-button type="primary" icon="el-icon-edit" size="mini" @click="editUserDialog(scope.row)"></el-button>
                     <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteUser(scope.row.mgId)"></el-button>
                     <el-tooltip effect="dark" content="分配角色" placement="top" :enterable="false">
                      <el-button type="warning" icon="el-icon-setting" size="mini" @click="roleDialogShow(scope.row)"></el-button>
                     </el-tooltip>
                </template>
                </el-table-column>
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

            <!-- 添加用户模态框 -->
            <el-dialog
              :title="userDialogTitle"
              :visible.sync="addDialogVisible"
              width="50%" @close="reset">
              <el-form label-width="120px" :model="userForm" :rules="addFormRules" ref="addUserFormRef">
                  <el-form-item label="ID：" hidden>
                    <el-input v-model="userForm.mgId"></el-input>
                  </el-form-item>
                  <el-form-item prop="mgName" label="用户名：">
                    <el-input v-model="userForm.mgName" :disabled="nameDisabled"></el-input>
                  </el-form-item>
                  <el-form-item prop="mgPwd"  :hidden="nameDisabled">
                    <span slot="label"><font color="#F56C6C">*</font> 密码：</span>
                    <el-input type="password" v-model="userForm.mgPwd"></el-input>
                  </el-form-item>
                  <el-form-item prop="confirmPwd" :hidden="nameDisabled">
                    <span slot="label"><font color="#F56C6C">*</font> 确认密码：</span>
                    <el-input type="password" v-model="userForm.confirmPwd"></el-input>
                  </el-form-item>
                  <el-form-item prop="mgEmail" label="邮箱：">
                    <el-input v-model="userForm.mgEmail"></el-input>
                  </el-form-item>
                  <el-form-item prop="mgMobile" label="号码：">
                    <el-input v-model="userForm.mgMobile"></el-input>
                  </el-form-item>
              </el-form>
              <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addManager()">确 定</el-button>
              </span>
            </el-dialog>

            <el-dialog
              title="分配角色"
              :visible.sync="roleDialogVisible"
              width="50%">
              <el-form label-width="100px">
                <el-form-item label="用户名：">
                  <el-input disabled v-model="currUser.mgName"></el-input>
                </el-form-item>
                <el-form-item label="角色：">
                  <el-select v-model="theRoles" multiple placeholder="请选择" filterable>
                    <el-option
                      v-for="item in roles"
                      :key="item.roleId"
                      :label="item.roleName"
                      :value="item.roleId">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-form>
              <span slot="footer" class="dialog-footer">
                <el-button @click="roleDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="allotRoles">确 定</el-button>
              </span>
            </el-dialog>
        </el-card>
    </div>
</template>

<script>
import user from '../../api/user.js'
import role from '../../api/role.js'

export default {
  data() {
    var validEmail = (rule, value, cb) => {
      if (/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test(value)) {
        return cb()
      }
      cb(new Error('请填入正确格式的邮箱'))
    }
    var validMobile = (rule, value, cb) => {
      if (/^(0|86|17951)?(13[0-9]|15[0123456789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(value)) {
        return cb()
      }
      cb(new Error('请填入正确格式的号码'))
    }
    var validConfirmPwd = (rule, value, cb) => {
      if (this.nameDisabled || value === this.userForm.mgPwd) {
        return cb()
      }
      cb(new Error('请确保密码和确认密码一致'))
    }
    var validName = (rule, value, cb) => {
      if (this.nameDisabled) {
        return cb()
      }
      this.$http.get('/backend-manager/validName', { params: { name: value } }).then(res => {
        console.log(res)
        if (!res.data.data.data) {
          return cb()
        }
        cb(new Error('该用户名已经存在'))
      })
    }
    var validPwd = (rule, value, cb) => {
      if (this.nameDisabled || (value.length >= 6 && value.length <= 15)) {
        return cb()
      }
      cb(new Error('密码长度6-15'))
    }
    return {
      currPage: 1, // 當前頁碼
      pageSize: 5, // 每頁條目數
      rows: 0, // 縂條目數
      userList: [], // 用戶列表
      query: '', // 查詢參數
      active_value: 0,
      inactive_value: 1, // 为开关设置不开启的值
      addDialogVisible: false, // 添加用户框是否可见
      userForm: {
        mgId: '',
        mgName: '',
        mgPwd: '',
        mgEmail: '',
        mgMobile: '',
        confirmPwd: ''
      },
      addFormRules: {
        mgName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '用户名长度3-10', trigger: 'blur' },
          { validator: validName, trigger: 'blur' }
        ],
        mgPwd: [
          // { required: true, message: '请输入密码', trigger: 'blur' },
          // { min: 6, max: 15, message: '密码长度6-15', trigger: 'blur' }
          { validator: validPwd, trigger: 'blur' }
        ],
        mgEmail: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: validEmail, trigger: 'blur' }
        ],
        mgMobile: [
          { required: true, message: '请输入号码', trigger: 'blur' },
          { validator: validMobile, trigger: 'blur' }
        ],
        confirmPwd: [
          // { required: true, message: '请输入确认密码', trigger: 'blur' },
          { validator: validConfirmPwd, trigger: 'blur' }
        ]
      },
      nameDisabled: false,
      userDialogTitle: '添加用户',
      roleDialogVisible: false,
      currUser: {},
      theRoles: [],
      roles: []
    }
  },
  // 頁面初始化
  created() {
    this.getUsers()
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row)
    },
    handleDelete(index, row) {
      console.log(index, row)
    },
    // 獲取用戶列表
    async getUsers() {
      const res = await this.$http.get('backend-manager/' + this.currPage + '/' + this.pageSize, { params: { query: this.query } })
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
    },
    // 用户状态修改
    stateChanged(data) {
      this.$http.put('/backend-manager', data).then(res => {
        console.log(data)
        console.log(res)
        if (res.data.code === 20000) {
          this.$message.success('更新状态成功')
        } else {
          this.$message.error(res.data.message)
          data.isDeleted = !data.isDeleted
        }
      })
    },
    // 重置表单
    reset () {
      this.$refs.addUserFormRef.resetFields()
      this.userForm.mgId = ''
    },
    // 添加/编辑用户
    addManager () {
      this.$refs.addUserFormRef.validate(async valid => {
        if (valid) {
          console.log(this.userForm)
          let res = {}
          if (!this.nameDisabled) {
            res = await this.$http.post('/backend-manager', this.userForm)
          } else {
            res = await this.$http.put('/backend-manager', this.userForm)
          }
          if (res.data.code === 20000) {
            this.$message.success(this.nameDisabled ? '编辑成功' : '添加成功')
            this.addDialogVisible = false
            this.getUsers()
          } else {
            this.$message.error(res.data.message)
          }
        }
      })
    },
    // 用户编辑框
    editUserDialog (row) {
      this.addDialogVisible = true
      this.nameDisabled = true
      this.userDialogTitle = '编辑用户'
      let temp = JSON.parse(JSON.stringify(row))
      this.userForm = {
        mgId: temp.mgId,
        mgName: temp.mgName,
        mgPwd: temp.mgPwd,
        mgEmail: temp.mgEmail,
        mgMobile: temp.mgMobile,
        confirmPwd: temp.mgPwd
      }
    },
    // 删除用户
    async deleteUser(id) {
      const res = await this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => {
        return err
      })
      // console.log(res)
      if (res === 'confirm') {
        let res = await user.deleteUser(id)
        console.log(res)
        if (res.data.code === 20000) {
          this.$message.success('删除成功')
          this.getUsers()
        } else {
          this.$message.error(res.data.message)
        }
      } else {
        this.$message.info('已取消删除')
      }
    },
    // 分配角色Dialog
    roleDialogShow(row) {
      this.roleDialogVisible = true
      this.currUser = row
      const res = role.list()
      res.then(r1 => {
        if (r1.data.code !== 20000) {
          this.$message.error('获取角色列表失败')
        } else {
          this.roles = r1.data.data.data
        }
      })
      const res2 = user.userRole(row.mgId)
      res2.then((r2) => {
        if (r2.data.code !== 20000) {
          return this.$message.error('获取用户角色失败')
        } else {
          this.theRoles = r2.data.data.data
        }
      }, console)
    },
    // 分配角色
    async allotRoles() {
      // console.log(this.currUser.mgId)
      // console.log(this.theRoles)
      const res = await user.allotRoles(this.currUser.mgId, this.theRoles)
      if (res.data.code !== 20000) {
        return this.$message.error('分配角色失败')
      }
      this.$message.success('分配角色成功')
      this.roleDialogVisible = false
    }
  }
}
</script>

<style lang="less" scoped>

</style>
