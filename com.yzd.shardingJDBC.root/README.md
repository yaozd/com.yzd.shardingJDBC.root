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