package com.yzd.shardingJDBC.demo.dao;

import com.yzd.shardingJDBC.demo.dao.mapper.TbOrderPayMapper;
import com.yzd.shardingJDBC.demo.entity.TbOrderPay;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TbOrderPay_UnitTest extends _BaseUnitTest {
    @Autowired
    TbOrderPayMapper tbOrderPayMapper;

    /***
     * 常见问题： Cannot find table rule and default data source with logic table: 'tb_order_pay'
     * 解决方案： getOrderPayTableRuleConfiguration的规则就可以了。
     */
    @Test
    public void createTable_Test(){
        tbOrderPayMapper.createTbOrderPayIfNotExistsTable();
    }

    /***
     * 主键进行显示的赋值，不在通过shardingJDBC内部的主键生成，这样可以直接获得payId与orderId的值，方便业务操作
     * payId 与orderId 实际生产中应该都是通过分布主键生成器产成。
     * 实现方法是在
     * /////////////////////////////////////////////////////////////////////
     <!-- 注释【generatedKey column="order_id" sqlStatement="JDBC" identity="true"】就不自动生成主键-->
     <table tableName="tb_order" enableDeleteByPrimaryKey="false" enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false">
     <!--Mybatis-V2-ShardingJDBC版 -->
     <!--identity=true 代表生成自增长的id -->
     <!--<generatedKey column="order_id" sqlStatement="JDBC" identity="true"/>-->
     </table>
     /////////////////////////////////////////////////////////////////////
     */
    @Test
    public void insert_noSetKeyGenerator_Test(){
        TbOrderPay item=new TbOrderPay();
        item.setPayId(400L);
        item.setOrderId(4000L);
        tbOrderPayMapper.insert(item);
    }
}
