package com.yzd.shardingJDBC.demo2.dao;

import com.yzd.shardingJDBC.demo2.dao.mapper.TbOrderPayMapper;
import com.yzd.shardingJDBC.demo2.entity.TbOrderPay;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TbOrderPay_UnitTest extends _BaseUnitTest {
    @Autowired
    TbOrderPayMapper tbOrderPayMapper;
    @Test
    public void insert_Test(){
        /***
         DatabaseShardingAlgorithmForComplex->collection["db_order_1","db_order_2"]
         DatabaseShardingAlgorithmForComplex->shardingValueList[{"columnName":"order_id","logicTableName":"tb_order_pay","values":[4000]}]
         */
        TbOrderPay item=new TbOrderPay();
        item.setPayId(500L);
        item.setOrderId(5000L);
        tbOrderPayMapper.insert(item);
    }
}
