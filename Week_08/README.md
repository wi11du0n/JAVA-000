# Week 08
## 周四
### 2.（必做）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

代码参见 demo 文件夹，使用 mybatis-plus + sharding-jdbc，基于一个实例两个库来模拟分库。

``` text
2020-12-09 16:04:47.389  INFO 26305 --- [           main] ShardingSphere-SQL                       : Actual SQL: ds1 ::: SELECT id,username,nick_name,mobile,email,password,salt,create_time,update_time FROM user_1 WHERE id=?  ::: [1336582547035648001]
2020-12-09 16:04:47.426  INFO 26305 --- [           main] com.example.demo.DemoApplication         : username is equal: true
2020-12-09 16:04:47.427  INFO 26305 --- [           main] com.example.demo.DemoApplication         : user in db: User(id=1336582547035648001, username=292a245e-bf4f-4905-a7ac-19e56ae709a8, nickName=292a245e-bf4f-4905-a7ac-19e56ae709a8, mobile=292a245e-bf4f-4905-a7ac-19e56ae709a8, email=292a245e-bf4f-4905-a7ac-19e56ae709a8, password=292a245e-bf4f-4905-a7ac-19e56ae709a8, salt=292a245e-bf4f-4905-a7ac-19e56ae709a8, createTime=Wed Dec 09 16:04:48 CST 2020, updateTime=Wed Dec 09 16:04:48 CST 2020)
2020-12-09 16:04:47.453  INFO 26305 --- [           main] ShardingSphere-SQL                       : Logic SQL: UPDATE user  SET username=?,
nick_name=?,
mobile=?,
email=?,
password=?,
salt=?  WHERE id=?
```

## 周六
### 2.（必做）基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。