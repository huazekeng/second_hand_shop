<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>数据统计</el-breadcrumb-item>
            <el-breadcrumb-item>数据报表</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card class="box-card">
            <div id="main" style="width: 95%;height:500px;"></div>
        </el-card>
    </div>
</template>

<script>
import echarts from 'echarts'
import Report from '../../api/report.js'
import _ from 'lodash'

export default {
  data() {
    return {
      baseOption: {
        title: {
          text: '用户来源'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#E9EEF3'
            }
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            boundaryGap: false
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ]
      },
      option: {}
    }
  },
  methods: {
    async userReport() {
      const res = await Report.report(1)
      if (res.data.code !== 20000) {
        return this.$message.error(res.data.message)
      }
      this.option = res.data.data.data
    }
  },
  created() {
  },
  async mounted() {
    const main = echarts.init(document.getElementById('main'))
    await this.userReport()
    this.option.xAxis = this.option.xaxis
    _.merge(this.option, this.baseOption)
    console.log(this.option)
    main.setOption(this.option)
  }
}
</script>

<style lang="less" scoped>

</style>
