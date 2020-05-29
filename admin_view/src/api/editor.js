// toolbar工具栏的工具选项（默认展示全部）
const toolOptions = [
  // 加粗 斜体 下划线 删除线
  ['bold', 'italic', 'underline', 'strike'],
  // 加粗 斜体 下划线 删除线
  ['blockquote', 'code-block'],
  // 1、2 级标题
  [{ header: 1 }, { header: 2 }],
  // 有序、无序列表
  [{ list: 'ordered' }, { list: 'bullet' }],
  // 上标/下标
  [{ script: 'sub' }, { script: 'super' }],
  // 缩进
  [{ indent: '-1' }, { indent: '+1' }],
  // 文本方向
  [{ direction: 'rtl' }],
  // 字体大小
  [{ size: ['small', false, 'large', 'huge'] }],
  // 标题
  [{ header: [1, 2, 3, 4, 5, 6, false] }],
  // 字体颜色、字体背景颜色
  [{ color: [] }, { background: [] }],
  // 字体种类
  [{ font: [] }],
  // 对齐方式
  [{ align: [] }],
  [{ clean: '源码编辑' }], // 这是自己加的
  // 链接、图片、视频
  ['link', 'image'],
  // 新添加的工具
  ['sourceEditor']
]

const handlers = {
  shadeBox: null,
  // 添加工具方法
  sourceEditor: function () {
    // alert('我新添加的工具方法');
    const container = this.container
    const firstChild = container.nextElementSibling.firstChild
    // 在第一次点击源码编辑的时候，会在整个工具条上加一个div，层级比工具条高，再次点击工具条任意位置，就会退出源码编辑。可以在下面cssText里面加个背景颜色看看效果
    const reg = /&lt;br&gt;/g
    if (!this.shadeBox) {
      let shadeBox = this.shadeBox = document.createElement('div')
      shadeBox.style.cssText = 'position:absolute; top:0; left:0; width:100%; height:100%; cursor:pointer;background:rgba(0,0,0,0.5);'
      container.style.position = 'relative'
      container.appendChild(shadeBox)
      firstChild.innerText = firstChild.innerHTML.replace(reg).trim()
      shadeBox.addEventListener('click', function () {
        this.style.display = 'none'
        firstChild.innerHTML = firstChild.innerText.trim().replace(reg)
      }, false)
    } else {
      this.shadeBox.style.display = 'block'
      firstChild.innerText = firstChild.innerHTML.replace(reg).trim()
    }
  }
}
// const handlers = {
//   shadeBox: null,
//   sourceEditor: function() {
//     // const reg = /<br>/g
//     const container = this.container
//     const firstChild = container.nextElementSibling.firstChild
//     if (!this.shadeBox) {
//       let shadeBox = this.shadeBox = document.createElement('div')
//       shadeBox.style.cssText = 'position:absolute; top:0; left:0; width:100%; height:100%; background:rgba(0,0,0,0.5); cursor:pointer'
//       container.style.position = 'relative'
//       shadeBox.addEventListener('click', function() {
//         this.style.display = 'none'
//         firstChild.innerHTML = firstChild.innerText.trim()
//       }, false)
//       container.appendChild(shadeBox)
//       let innerHTML = firstChild.innerHTML
//       // innerHTML = innerHTML.replace(reg, '')
//       firstChild.innerText = innerHTML
//     } else {
//       let innerHTML = firstChild.innerHTML
//       // innerHTML = innerHTML.replace(reg, '')
//       firstChild.innerText = innerHTML
//       this.shadeBox.style.display = 'block'
//     }
//   }
// }

export default {
  placeholder: '请输入正文',
  // 主题
  theme: 'snow',
  modules: {
    toolbar: {
      // 工具栏选项
      container: toolOptions,
      // 事件重写
      handlers: handlers
    }
  },
  // 在使用的页面中初始化按钮样式
  initButton: function () {
    // 样式随便改
    const sourceEditorButton = document.querySelector('.ql-sourceEditor')
    sourceEditorButton.style.cssText = 'font-size:16px'
    // 加了elementui的icon
    sourceEditorButton.classList.add('el-icon-edit-outline')
    // 鼠标放上去显示的提示文字
    sourceEditorButton.title = '源码编辑'
  },
  register(q) {
    // 注册标签(因为在富文本编辑器中是没有div,table等标签的，需要自己去注册自己需要的标签)
    class div extends q.import('blots/block/embed') {}
    class table extends q.import('blots/block/embed') {}
    class tr extends q.import('blots/block/embed') {}
    class td extends q.import('blots/block/embed') {}
    div.blotName = div.tagName = 'div'
    table.blotName = table.tagName = 'table'
    tr.blotName = tr.tagName = 'tr'
    td.blotName = td.tagName = 'td'
    q.register(div)
    q.register(table)
    q.register(tr)
    q.register(td)
  }
}
