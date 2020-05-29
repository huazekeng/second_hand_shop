/*
 *
 * @author : Jeason Laung
 * @date   : 2019/1/22
 *
 */
class socket {
  /* websocket实例 */
  ws = null

  /* '#'为私有属性，外部不可调用 */

  /* 状态 */
  // 连接状态
  alive = false
  // 把类的参数传入这里，方便调用
  params = null

  /* 计时器 */
  // 重连计时器
  reconnectTimer = null
  // 心跳计时器
  heartTimer = null
  // 信息onmessage缓存方法
  messageRunc = null

  /* 参数 */
  // 心跳时间 1秒一次
  heartBeat = 50000
  // 心跳信息：默认为‘hello’随便改，看后台
  heartMsg = 'hello'
  // 是否自动重连
  reconnect = true
  // 重连间隔时间
  reconnectTime = 5000
  // 重连次数
  reconnectTimes = 10

  constructor(params) {
    this.params = params
    this.init()
  }

  /* 初始化 */
  init() {
    // 重中之重，不然重连的时候会越来越快
    clearInterval(this.reconnectTimer)
    clearInterval(this.heartTimer)

    // 取出所有参数
    const params = this.params
    // 设置连接路径
    const { url, port } = params
    const globalParams = ['heartBeat', 'heartMsg', 'reconnect', 'reconnectTime', 'reconnectTimes']

    // 定义全局变量
    Object.keys(params).forEach(key => {
      if (globalParams.indexOf(key) !== -1) {
        this[key] = params[key]
      }
    })

    const wsRrl = port ? url + ':' + port : url
    // console.log(wsRrl)
    // this.ws = null
    delete this.ws
    this.ws = new WebSocket(wsRrl)

    // window.console.log(this.#messageRunc)
    if (this.messageRunc) {
      this.onmessage(this.messageRunc)
    }

    // 默认绑定事件
    this.ws.onopen = () => {
      // 设置状态为开启
      this.alive = true
      clearInterval(this.reconnectTimer)
      // 连接后进入心跳状态
      this.onheartbeat()
    }
    this.ws.onclose = () => {
      // 设置状态为断开
      this.alive = false

      clearInterval(this.heartTimer)

      // 自动重连开启  +  不在重连状态下
      if (this.reconnect === true) {
        /* 断开后立刻重连 */
        this.onreconnect()
      }
    }
  }

  /*
   *
   * 新增‘心跳事件’和‘重连事件’
   *
   */

  /* 心跳事件 */
  onheartbeat(func) {
    // 在连接状态下
    if (this.alive === true) {
      /* 心跳计时器 */
      this.heartTimer = setInterval(() => {
        // 发送心跳信息
        this.send(this.heartMsg)
        if (func) {
          func(event)
        }
      }, this.heartBeat)
    }
  }

  /* 重连事件 */
  onreconnect(func) {
    /* 重连间隔计时器 */
    this.reconnectTimer = setInterval(() => {
      // 限制重连次数
      if (this.reconnectTimes <= 0) {
        // 关闭定时器
        // this.#isReconnect = false
        clearInterval(this.reconnectTimer)
        // 跳出函数之间的循环
        return
      } else {
        // 重连一次-1
        this.reconnectTimes--
      }
      // 进入初始状态
      this.init()
      if (func) {
        func(event)
      }
    }, this.reconnectTime)
  }

  /*
   *
   * 对原生方法和事件进行封装
   *
   */

  // 发送消息
  send(text) {
    if (this.alive === true) {
      text = typeof text === 'string' ? text : JSON.stringify(text)
      // console.log(text)
      this.ws.send(text)
    }
  }

  // 断开连接
  close() {
    if (this.alive === true) {
      this.ws.close()
    }
  }

  // 接受消息
  onmessage(func, all = false) {
    this.ws.onmessage = data => {
      this.messageRunc = func
      func(!all ? data.data : data)
    }
  }

  // websocket连接成功事件
  onopen(func) {
    this.ws.onopen = event => {
      this.alive = true
      if (func) {
        func(event)
      }
      // func ? func(event) : false
    }
  }

  // websocket关闭事件
  onclose(func) {
    this.ws.onclose = event => {
      // 设置状态为断开
      this.alive = false

      clearInterval(this.heartTimer)

      // 自动重连开启  +  不在重连状态下
      if (this.reconnect === true) {
        /* 断开后立刻重连 */
        this.onreconnect()
      }
      if (func) {
        func(event)
      }
    }
  }

  // websocket错误事件
  onerror(func) {
    this.ws.onerror = event => {
      if (func) {
        func(event)
      }
    }
  }
}

export default socket
