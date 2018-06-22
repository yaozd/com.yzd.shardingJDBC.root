#### 分库分表-设计思路-简单版
```
1.  尽量相同用户产生的订单存储在同一个数据库中  
2.  数据库的分库分表规则：用户的ID+时间 (理论上分库的规则最好是两个分片键。让具有相同订单产出现的用户在同相库中)
3.  表的分库分表规则：时间
4.  控制MYSQL中单表的数据量在5百万到1千万之间
```
#### 分库分表-设计思路-实践业务版
```
1.  可以分维度进行分库分表的策略，如：用户维度，商家维度，运营维度
2.  通过canal+MQ进行各个维度之间的数据同步与最终一致性
```
#### 分片算法-标准分片策略(StandardShardingStrategy)
```
1.  PreciseShardingAlgorithm是必选的，用于处理=和IN的分片
2.  RangeShardingAlgorithm是可选的，用于处理BETWEEN AND分片，
    如果不配置RangeShardingAlgorithm，SQL中的BETWEEN AND将按照全库路由处理。
参考：
http://shardingjdbc.io/document/legacy/2.x/cn/02-guide/sharding/
```
#### 分片键数据库的计算思路(ComplexShardingStrategy)
```
分片键数据库=DB_Order_u1_o1,DB_Order_u2_o2
u1:代表 userId的范围
o1:代表 orderId的范围
可以分别对userId与orderId进行范围的StoreRegion计算，然后合并两个suffix，最后将合并结果与分库信息进行比较
```
#### 多分片键数据库表的设计思路(ComplexShardingStrategy)
##### --- 通过存储空间换取更高的设计灵活性
```
0.假设用户ID与订单ID是分片存储的计算的参数
1.用户ID与订单ID存储到所有的表中
2.缺点有的表存储了用户ID会增加存储空间
3.方便以后的数据迁移。具有很高的灵活性
```
#### 参考
```
sharding jdbc + mybatis +spring boot的分库分表实现 - 简书
https://www.jianshu.com/p/3b2ab87b0de7
sharding-JDBC 实现读写分离 - 做个有梦想的咸鱼 - 博客园
https://www.cnblogs.com/boothsun/p/7853526.html
官方文档-模块说明
http://shardingjdbc.io/document/legacy/2.x/cn/03-design/module/
官方文档-核心概念
http://shardingjdbc.io/document/legacy/2.x/cn/02-guide/concepts/
官方文档-使用限制
http://shardingjdbc.io/document/legacy/2.x/cn/01-start/limitations/
【分库分表】sharding-jdbc—分片策略 - Mr.yang.localhost - 博客园
https://www.cnblogs.com/mr-yang-localhost/p/8313360.html#_label0
【分库分表】sharding-jdbc—解决的问题 - Mr.yang.localhost - 博客园
https://www.cnblogs.com/mr-yang-localhost/p/8120543.html
shardingjdbc 学习(四)-SQL路由实现 - CSDN博客
https://blog.csdn.net/bohu83/article/details/80571367
```