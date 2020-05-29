# second_shop

#### 介绍
商城后台管理系统接口，前端Vue项目:https://gitee.com/skhzk/vue_shop，详细信息可以看（项目-二手商城部署版.7z，内容更详细）
#### 软件架构
软件架构说明

1.  SpringBoot
2.  MyBatisPlus
3.  Redis
4.  SpringSecurity
5.  Swagger
6.  JWT

#### 安装教程


- 前端环境：
- 安装Node即可：https://www.jianshu.com/p/63baa3c533d6
- 后端环境：
- java：
- 安装JDK1.8：https://www.cnblogs.com/yybrhr/p/9811352.html
- 安装IDEA
- Maven（使用IDEA默认的即可）
- 数据库：
- 1、安装Mysql(5.7..版本即可)，Navicate。
- 2、先创建数据库 名字为second_hand_shop，编码UTF-8
- 3、将 项目/数据库下的sql文件拖到Navicate执行，创建数据库。
- 4、安装Redis(默认配置即可)
- 5、支付宝沙箱申请（用于测试支付宝支付，需自己申请，如果不使用支付功能，5、6步骤可以不做）
- https://www.cnblogs.com/niceyoo/p/12196095.html
- 注：使用支付包沙箱APP和账号测试，记得替换application.yml的配置为自己的沙箱
- 6、申请内网穿透，将自己的主机映射到公网域名，用于支付宝回调自己接口
- 可以使用免费的公网穿透：
- http://ngrok.cc


#### 后台界面

- 1、首页，默认账户（hzk ：123456 , lzj： 123456）
- ![1、用户 默认账户（admin，123456）](https://images.gitee.com/uploads/images/2020/0529/220038_ab0236fd_2016556.png "屏幕截图.png")
- 2.1、角色、权限控制模块
- ![2.1、角色、权限控制模块](https://images.gitee.com/uploads/images/2020/0529/220106_7c8878ca_2016556.png "屏幕截图.png")
- 2.2、角色、权限控制模块 
- ![2.2、角色、权限控制模块](https://images.gitee.com/uploads/images/2020/0529/220205_bfc00cd8_2016556.png "屏幕截图.png")
- 3、订单模块]
- ![3、订单模块](https://images.gitee.com/uploads/images/2020/0529/220234_b6ffeb12_2016556.png "屏幕截图.png")
- 4、商品、参数、分类模块
- ![4、商品、参数、分类模块](https://images.gitee.com/uploads/images/2020/0529/220256_62f9cbdc_2016556.png "屏幕截图.png")
- 5、数据统计模块
- ![5、数据统计模块](https://images.gitee.com/uploads/images/2020/0529/220308_151f846c_2016556.png "屏幕截图.png")

#### 前台界面

- 1、首页，默认账户（hzk ：123456 , lzj： 123456）
- ![1、首页，默认账户（hzk ：123456 , lzj： 123456）](https://images.gitee.com/uploads/images/2020/0529/220359_9955512a_2016556.png "屏幕截图.png")
- 2、地址管理
- ![2、地址管理](https://images.gitee.com/uploads/images/2020/0529/220425_7b3787d4_2016556.png "屏幕截图.png")
- 3、商品管理
- ![3、商品管理](https://images.gitee.com/uploads/images/2020/0529/220436_61943a33_2016556.png "屏幕截图.png")
- 4、求购管理
- ![4、求购管理](https://images.gitee.com/uploads/images/2020/0529/220450_827cdd36_2016556.png "屏幕截图.png")
- 5、站内消息，基于websocket在线聊天
- ![5、站内消息，基于websocket在线聊天](https://images.gitee.com/uploads/images/2020/0529/220503_29aa6cf8_2016556.png "屏幕截图.png")
- 6、站内论坛
- ![6、站内论坛](https://images.gitee.com/uploads/images/2020/0529/220525_06fd9fdf_2016556.png "屏幕截图.png")
- 7、订单列表
- ![7、订单列表](https://images.gitee.com/uploads/images/2020/0529/220548_6c48d069_2016556.png "屏幕截图.png")
- 8、购物车
- ![8、购物车](https://images.gitee.com/uploads/images/2020/0529/220606_e7d8c9e6_2016556.png "屏幕截图.png")
- 9、支付
- ![9、支付](https://images.gitee.com/uploads/images/2020/0529/220620_b91b0985_2016556.png "屏幕截图.png")
- 10、搜索
- ![10、搜索](https://images.gitee.com/uploads/images/2020/0529/220632_ebbf2b25_2016556.png "屏幕截图.png")
- 11.1、广告位
- ![11.1、广告位](https://images.gitee.com/uploads/images/2020/0529/220652_4e2e5946_2016556.png "屏幕截图.png")
- 11.2 求购列表
- ![11.2 求购列表](https://images.gitee.com/uploads/images/2020/0529/220721_890506fc_2016556.png "屏幕截图.png")
- 12、聊天
- ![12、聊天](https://images.gitee.com/uploads/images/2020/0529/220736_faeef9f7_2016556.png "屏幕截图.png")

#### 流程图

- ![后台](https://images.gitee.com/uploads/images/2020/0529/220837_f0efce32_2016556.png "屏幕截图.png")
- ![前台](https://images.gitee.com/uploads/images/2020/0529/220937_0c9395d4_2016556.png "屏幕截图.png")


#### 使用说明

1.  数据库不是原前端VUE_SHOP数据库，有做修改
2.  有做刷新Token处理，Token过期时间30min，refresh_token过期一天，前端已经做token过期处理
3.  前端为Vue项目
 
#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
