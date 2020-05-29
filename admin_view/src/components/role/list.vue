<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>角色管理</el-breadcrumb-item>
            <el-breadcrumb-item>角色列表</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card>
          <el-row class="table-tool">
            <el-col>
              <el-button type="primary" @click="addDialogVisible = true;roleDialogTitle='添加角色';isEdit=false">添加角色</el-button>
            </el-col>
          </el-row>
          <el-table
                :data="roleList"
                style="width: 100%"
                stripe border>
                <el-table-column label="#" type="expand">
                  <template slot-scope="scope">
                    <el-row :class="['bobottom',index == 0 ? 'botop' : '','vcenter']" v-for="(item,index) in scope.row.children" :key="item.psId">
                      <el-col :span="5">
                        <el-tag closable @close="deleteRightByRole(scope.row,item.psId)">{{ item.psName }}</el-tag><i class="el-icon-caret-right"></i>
                      </el-col>
                      <el-col :span="19">
                        <el-row v-for="(item2,index2) in item.children" :key="item2.psId" :class="[index2!=0 ? 'botop' : '','vcenter']">
                          <el-col :span="6">
                            <el-tag type="success" closable @close="deleteRightByRole(scope.row,item2.psId)">{{ item2.psName }}</el-tag><i class="el-icon-caret-right"></i>
                          </el-col>
                          <el-col :span="18">
                            <el-tag type="warning" v-for="item3 in item2.children" :key="item3.psId" closable @close="deleteRightByRole(scope.row,item3.psId)">
                              {{ item3.psName }}
                            </el-tag>
                          </el-col>
                        </el-row>
                      </el-col>
                    </el-row>
                  </template>
                </el-table-column>
                <el-table-column label="名称" prop="roleName"></el-table-column>
                <el-table-column label="描述" prop="roleDesc"></el-table-column>
                <el-table-column label="字符" prop="roleStr"></el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row)">编辑</el-button>
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteRow(scope.row.roleId)">删除</el-button>
                    <el-button type="warning" icon="el-icon-setting" size="mini" @click="rightDialogShow(scope.row)">分配权限</el-button>
                  </template>
                </el-table-column>
            </el-table>
        </el-card>
        <!-- 添加角色模态框 -->
      <el-dialog
        :title="roleDialogTitle"
        :visible.sync="addDialogVisible"
        width="50%" @close="reset">
        <el-form label-width="120px" :model="roleForm" :rules="addFormRules" ref="addRoleFormRef">
          <el-form-item label="ID：" hidden>
            <el-input v-model="roleForm.roleId"></el-input>
          </el-form-item>
          <el-form-item prop="roleName">
            <span slot="label"> 角色名称：</span>
            <el-input v-model="roleForm.roleName" :disabled="isEdit"></el-input>
          </el-form-item>
          <el-form-item prop="roleStr">
            <span slot="label"> 角色字符串：</span>
            <el-input v-model="roleForm.roleStr"></el-input>
          </el-form-item>
          <el-form-item label="角色描述：">
            <el-input v-model="roleForm.roleDesc"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addRole()">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 权限树 -->
        <el-dialog
        title="设置权限"
        :visible.sync="rightDialogVisible"
        width="50%">
        <el-tree
          :data="rigntTreeData"
          :props="rightTreeprops"
          show-checkbox
          default-expand-all
          node-key="psId"
          :default-checked-keys="rightKeys"
          ref="rightRef">
        </el-tree>
        <span slot="footer" class="dialog-footer">
          <el-button @click="rightDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="allotRight">确 定</el-button>
        </span>
      </el-dialog>
    </div>
</template>

<script>
import Roles from '../../api/role.js'

export default {
  created: function() {
    this.getRoles()
  },
  data() {
    return {
      roleList: [],
      roleDialogTitle: '添加角色',
      addDialogVisible: false,
      isEdit: false,
      roleForm: {
        roleId: '',
        roleName: '',
        roleDesc: '',
        roleStr: ''
      },
      addFormRules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ],
        roleStr: [
          { required: true, message: '请输入角色字符', trigger: 'blur' }
        ]
      },
      rightDialogVisible: false,
      rigntTreeData: [],
      rightTreeprops: {
        label: 'psName',
        children: 'children'
      },
      rightKeys: [],
      currentRoId: ''
    }
  },
  methods: {
    getRoles: async function() {
      let res = await Roles.list()
      if (res.data.code === 20000) {
        this.roleList = res.data.data.data
      } else {
        this.$message.error(res.data.message)
      }
    },
    reset () {
      // this.$refs.addRoleFormRef.resetFields()
      Object.keys(this.roleForm).forEach(key => (this.roleForm[key] = ''))
      // console.log(this.roleForm)
    },
    // 添加/编辑角色
    async addRole() {
      let res = {}
      if (this.isEdit) {
        res = await Roles.editRole(this.roleForm)
      } else {
        res = await Roles.addRole(this.roleForm)
      }
      if (res.data.code === 20000) {
        this.$message.success(this.isEdit ? '编辑成功' : '添加成功')
        this.addDialogVisible = false
        this.reset()
        this.getRoles()
      } else {
        this.$message.success(res.data.message)
      }
    },
    edit(row) {
      this.addDialogVisible = true
      this.isEdit = true
      this.roleDialogTitle = '编辑角色'
      let temp = JSON.parse(JSON.stringify(row))
      this.roleForm = temp
    },
    // 删除用户
    async deleteRow(id) {
      const res = await this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => {
        return err
      })
      if (res === 'confirm') {
        let res = await Roles.delete(id)
        // console.log(res)
        if (res.data.code === 20000) {
          this.$message.success('删除成功')
          this.getRoles()
        } else {
          this.$message.error(res.data.message)
        }
      } else {
        this.$message.info('已取消删除')
      }
    },
    async deleteRightByRole(row, psId) {
      const res = await this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => {
        return err
      })
      if (res === 'confirm') {
        let res = await Roles.deletePermissionByRole(row.roleId, psId)
        // console.log(res)
        if (res.data.code === 20000) {
          this.$message.success('删除成功')
          row.children = res.data.data.data
        } else {
          this.$message.error(res.data.message)
        }
      } else {
        this.$message.info('已取消删除')
      }
    },
    async rightDialogShow(row) {
      const res = await this.$http.get('/backend-permission')
      if (res.data.code !== 20000) {
        return this.$message.error('获取权限树失败')
      }
      this.rightDialogVisible = true
      this.rigntTreeData = res.data.data.data
      // console.log(row)
      this.currentRoId = row.roleId
      this.rightKeys = []
      row.children.forEach(c1 => {
        // this.rightKeys.push(c1.psId)
        c1.children.forEach(c2 => {
          // this.rightKeys.push(c2.psId)
          c2.children.forEach(c3 => {
            this.rightKeys.push(c3.psId)
          })
        })
      })
    },
    // 为角色授权
    async allotRight() {
      const pkeys = [
        ...this.$refs.rightRef.getCheckedKeys(),
        ...this.$refs.rightRef.getHalfCheckedKeys()
      ]
      const res = await Roles.allotRight(this.currentRoId, pkeys)
      if (res.data.code !== 20000) {
        return this.$message.error('授权失败')
      }
      this.$message.success('授权成功')
      this.rightDialogVisible = false
      this.getRoles()
    }
  }
}
</script>

<style lang="less" scoped>
  .el-tag{
    margin: 7px;
  }

  .botop{
    border-top: 1px solid #eee;
  }

  .bobottom{
    border-bottom: 1px solid #eee;
  }

  .vcenter{
    display: flex;
    align-items: center
}
</style>
