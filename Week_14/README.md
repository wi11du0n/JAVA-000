# Week 14

## 周四

2. （必做）思考和设计自定义MQ第二个版本或第三个版本，写代码实现其中至少一个功能点，把设计思路和实现代码，提交到github。
    * version 1.0 (内存Queue) 基于内存Queue实现生产和消费API（已经完成）
        - 创建内存BlockingQueue，作为底层消息存储
        - 定义Topic，支持多个Topic
        - 定义Producer，支持Send消息
        - 定义Consumer，支持Poll消息
    * version 2.0 (自定义Queue) 去掉内存Queue，设计自定义Queue，实现消息确认和消费offset
        - 自定义内存Message数组模拟Queue
        - 使用指针记录当前消息写入位置
        - 对于每个命名消费者，用指针记录消费位置
    * version 3.0 (基于SpringMVC实现MQServer) 拆分broker和client(包括producer和consumer)
        - 将Queue保存到webserver端
        - 设计消息读写API接口，确认接口，提交offset接口
        - producer和consumer通过httpclient访问Queue
        - 实现消息确认，offset提交
        - 实现consumer从offset增量拉取
    * version 4.0 (功能完善MQ) 增加多种策略（各条之间没有关系，可以任意选择实现）
        - 考虑实现消息过期，消息重试，消息定时投递等策略
        - 考虑批量操作，包括读写，可以打包和压缩
        - 考虑消息清理策略，包括定时清理，按容量清理、LRU等
        - 考虑消息持久化，存入数据库，或WAL日志文件，或BookKeeper
        - 考虑将springmvc替换成netty下的tcp传输协议，rsocket/websocket
    * version 5.0 (体系完善MQ) 对接各种技术（各条之间没有关系，可以任意选择实现）
        - 考虑封装JMS1.1接口规范
        - 考虑实现STOMP消息规范
        - 考虑实现消息事务机制与事务管理器
        - 对接Spring
        - 对接Camel或SpringIntegration
        - 优化内存和磁盘的使用