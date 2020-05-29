<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>订单管理</el-breadcrumb-item>
            <el-breadcrumb-item>订单列表</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card class="box-card">
            <!-- 搜索框 -->
            <el-row :gutter="20">
                <el-col :span="10" class="table-tool">
                    <el-input placeholder="请输入内容" v-model="queryInfo.search" clearable @clear="getList">
                        <el-button slot="append" icon="el-icon-search" @click="queryInfo.page = 1;getList()"></el-button>
                    </el-input>
                </el-col>
            </el-row>

            <el-table
                :data="orderList"
                style="width: 100%" stripe border>
                <el-table-column label="#" type="index"></el-table-column>
                <el-table-column label="订单编号" prop="orderNumber"></el-table-column>
                <el-table-column label="订单价格" prop="orderPrice"></el-table-column>
                <el-table-column label="是否付款">
                    <template slot-scope="scope">
                        <el-tag type="error" size="mini" v-if="scope.row.payStatus === '0'">未付款</el-tag>
                        <el-tag type="success" size="mini" v-else>已付款</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="是否发货" prop="isSend"></el-table-column>
                <el-table-column label="下单时间">
                    <template slot-scope="scope">
                        {{ scope.row.gmtCreate | dateFormat }}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="primary" icon="el-icon-edit" size="mini" @click="editAddress(scope.row.id)"></el-button>
                        <el-button type="success" icon="el-icon-location" size="mini" @click="wuliuDialogVisable=true"></el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分頁組件 -->
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="queryInfo.page"
              :page-sizes="[5, 10, 30, 40]"
              :page-size="queryInfo.limit"
              layout="total, sizes, prev, pager, next, jumper"
              :total="rows">
            </el-pagination>

            <el-dialog
                title="修改地址"
                :visible.sync="dialogVisible"
                width="50%" @close="reset">
                <el-form label-width="120px" :model="form" :rules="formRules" ref="formRef">
                <el-form-item label="省/市/区：" prop="provice">
                     <el-cascader
                        v-model="form.provice"
                        :options="options"
                        :props="{ expandTrigger: 'hover', label: 'label', value: 'value'}"
                        clearable
                        filterable></el-cascader>
                </el-form-item>
                <el-form-item prop="detail">
                    <span slot="label"> 详细地址：</span>
                    <el-input v-model="form.detail"></el-input>
                </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="putEdit()">确 定</el-button>
                </span>
            </el-dialog>

            <el-dialog
                title="修改地址"
                :visible.sync="wuliuDialogVisable"
                width="50%" @close="reset">

                <el-timeline>
                    <el-timeline-item
                    v-for="(activity, index) in activities"
                    :key="index"
                    :timestamp="activity.ftime">
                    {{activity.context}}
                    </el-timeline-item>
                </el-timeline>
            </el-dialog>
        </el-card>
    </div>
</template>

<script>
import Order from '../../api/order.js'
import City from './citydata.js'
import Wuliu from './wuliu.js'

export default {
  data() {
    return {
      queryInfo: {
        page: 1,
        limit: 5,
        search: ''
      },
      orderList: [],
      rows: 0,
      form: {
        provice: [],
        detail: ''
      },
      dialogVisible: false,
      options: City,
      formRules: {
        'provice': [{ required: true, message: '请输入省市区', trigger: 'blur' }],
        'detail': [{ required: true, message: '请输入详细地址', trigger: 'blur' }]
      },
      wuliuDialogVisable: false,
      activities: Wuliu
    }
  },
  created() {
    this.getList()
  },
  methods: {
    async getList() {
      const res = await Order.list(this.queryInfo)
      if (res.data.code !== 20000) return this.$message.error(res.data.message)
      this.rows = res.data.data.total
      this.orderList = res.data.data.rows
    },
    editAddress() {
      this.dialogVisible = true
    },
    wuliu() {

    },
    // 每頁條目數切換
    handleSizeChange(newPageSize) {
      this.queryInfo.limit = newPageSize
      this.queryInfo.page = 1
      this.getList()
    },
    // 當前頁切換
    handleCurrentChange(newCurrPage) {
      this.queryInfo.page = newCurrPage
      this.getList()
    },
    reset() {
      this.$refs.formRef.resetFields()
    },
    putEdit() {
      this.$refs.formRef.validate(async valid => {
        if (valid) {
          this.$message.success('修改成功')
          this.dialogVisible = false
        }
      })
    }
  },
  mounted() {

  }
}
</script>

<style lang="less" scoped>

</style>
