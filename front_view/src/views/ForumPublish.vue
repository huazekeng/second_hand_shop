<template>
  <div class="publish">
    <div class="container" style="padding-top:25px;">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom:15px;">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/forum/list' }">站内论坛</el-breadcrumb-item>
        <el-breadcrumb-item>发布帖子</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>
        <el-alert
            title="发帖"
            type="info"
            center
            show-icon
            closable="false">
        </el-alert>
        <el-form label-width="120px" :model="form" :rules="rules" ref="form" label-position="top">
          <el-form-item prop="title" label="标题：">
            <el-input v-model="form.title"></el-input>
          </el-form-item>
          <el-form-item prop="type" label="类型：">
            <el-select
              v-model="form.type"
              filterable
              default-first-option
              placeholder="请选择类型">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="content" label="内容：">
            <quill-editor v-model="form.content"
              ref="myQuillEditor"
              :options="editorOption">
            </quill-editor>
          </el-form-item>
          <el-form-item class="fr">
            <el-button type="primary" @click="pubilsh">发 贴</el-button>
            <el-button @click="cancel">取 消</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import Editor from '../api/editor.js'
export default {
  name: 'forum-list',
  data() {
    return {
      options: [{
        value: '技术',
        label: '技术'
      }, {
        value: '精华',
        label: '精华'
      }, {
        value: '科普',
        label: '科普'
      }, {
        value: '文学',
        label: '文学'
      }, {
        value: '娱乐',
        label: '娱乐'
      }],
      form: {
        title: '',
        type: '',
        content: ''
      },
      editorOption: Editor,
      rules: {
        content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        type: [{ required: true, message: '请选择类型', trigger: 'blur' }]
      }
    }
  },
  components: {
  },
  computed: {
    ...mapState(['username', 'socket', 'socketMsg'])
  },
  watch: {
  },
  mounted() {
  },
  methods: {
    push() {
      this.$router.push('/forum/pubilsh')
    },
    cancel() {
      this.$router.push('/forum/list')
    },
    pubilsh() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        this.axios.post('/forum', this.form).then(res => {
          this.$message.success('发帖成功')
          this.cancel()
        })
      })
    }
  }
}
</script>

<style lang="scss">
.publish{
  .el-tag{
    margin-right: 10px;
  }
  .push-ini{
    // float: right;
    margin-bottom: 15px;
  }
  .el-form-item__label{
    color: #666666;
    font-size: 14px;
    font-weight: 600;
  }
  .el-select{
    width: 100%;
  }
  .ql-editor{
    height: 300px;
  }
}
</style>
