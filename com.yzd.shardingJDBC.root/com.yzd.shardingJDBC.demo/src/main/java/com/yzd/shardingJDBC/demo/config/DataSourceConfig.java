package com.yzd.shardingJDBC.demo.config;


import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingjdbc.core.jdbc.core.datasource.ShardingDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.yzd.shardingJDBC.demo.dao.mapper", sqlSessionTemplateRef = "testSqlSessionTemplate")
public class DataSourceConfig {

    @Bean(name = "shardingDataSource")
    DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig;
        shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getOrderPayTableRuleConfiguration());
        //BindingTable:指在任何场景下分片规则均一致的主表和子表。例：订单表和订单项表，均按照订单ID分片，则此两张表互为BindingTable关系。BindingTable关系的多表关联查询不会出现笛卡尔积关联，关联查询效率将大大提升。
        //BindingTable 绑定后则以主表进行分片操作，所以在使用join的情况下最好进行绑定表BindingTable
        shardingRuleConfig.getBindingTableGroups().add("tb_order,tb_order_item");
        //默认分库分表的规则
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", DatabaseShardingAlgorithmForPrecise.class.getName(),DatabaseShardingAlgorithmForRange.class.getName()));
        //shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", DemoDatabaseShardingAlgorithm.class.getName()));
        //shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id", DemoTableShardingAlgorithm.class.getName()));
        //shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", DemoTableShardingAlgorithm.class.getName()));
        return new ShardingDataSource(shardingRuleConfig.build(createDataSourceMap()));
        //return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig);
    }

    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("tb_order");
        //table的配置写法-01(是groovy动态语法，代表0到1进行循环)
        //orderTableRuleConfig.setActualDataNodes("user_${0..1}.user_info_${0..1}");
        //table的配置写法-02
        orderTableRuleConfig.setActualDataNodes("db_order_1.tb_order,db_order_2.tb_order");
        //
        //orderTableRuleConfig.setKeyGeneratorClass(MyKeyGenerator.class);
        //理论上讲订单的order_id，是要显示赋值的。不能隐藏起来这样就拿不到订单ID的，目前这里只是为测试方便然而已
        orderTableRuleConfig.setKeyGeneratorColumnName("order_id");
        //自定义分库分表的规则
        //orderTableRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",DemoTableShardingAlgorithm.class.getName()));
        //orderTableRuleConfig.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",DemoDatabaseShardingAlgorithm.class.getName()));
        return orderTableRuleConfig;
    }

    TableRuleConfiguration getOrderItemTableRuleConfiguration() {
        TableRuleConfiguration orderItemTableRuleConfig = new TableRuleConfiguration();
        orderItemTableRuleConfig.setLogicTable("tb_order_item");
        orderItemTableRuleConfig.setActualDataNodes("db_order_1.tb_order_item,db_order_2.tb_order_item");
        //SQLException: Field 'item_id' doesn't have a default value的问题可能是没有设置setKeyGeneratorColumnName("item_id")
        orderItemTableRuleConfig.setKeyGeneratorColumnName("item_id");
        return orderItemTableRuleConfig;
    }
    TableRuleConfiguration getOrderPayTableRuleConfiguration() {
        TableRuleConfiguration orderItemTableRuleConfig = new TableRuleConfiguration();
        orderItemTableRuleConfig.setLogicTable("tb_order_pay");
        orderItemTableRuleConfig.setActualDataNodes("db_order_1.tb_order_pay,db_order_2.tb_order_pay");
        //这些主键采用显示赋值，不在使用内部的自动生成
        //orderItemTableRuleConfig.setKeyGeneratorColumnName("pay_id");
        return orderItemTableRuleConfig;
    }
    /**
     * 需要手动配置事务管理器
     *
     * @param shardingDataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactitonManager(DataSource shardingDataSource) {
        return new DataSourceTransactionManager(shardingDataSource);
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource shardingDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(shardingDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/yzd/shardingJDBC/demo/dao/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put("db_order_1", createDataSource("mdev_db_order_1"));
        result.put("db_order_2", createDataSource("mdev_db_order_2"));
        return result;
    }

    private DataSource createDataSource(final String dataSourceName) {
        BasicDataSource result = new BasicDataSource();
        result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        result.setUrl(String.format("jdbc:mysql://127.0.0.1:3306/%s?zeroDateTimeBehavior=convertToNull&amp;useUnicode=true&amp;characterEncoding=utf-8", dataSourceName));
        result.setUsername("root");
        result.setPassword("123456");
        return result;
    }

}
