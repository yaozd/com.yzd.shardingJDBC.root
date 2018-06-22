package com.yzd.shardingJDBC.demo2.dao;

import com.yzd.shardingJDBC.demo2.dao.mapper.TbOrderMapper;
import com.yzd.shardingJDBC.demo2.entity.TbOrder;
import com.yzd.shardingJDBC.demo2.fastJsonExt.FastJsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TbOrder_UnitTest extends _BaseUnitTest {

    @Autowired
    TbOrderMapper tbOrderMapper;

    /***
     DatabaseShardingAlgorithmForComplex->collection["db_order_1","db_order_2"]
     DatabaseShardingAlgorithmForComplex->shardingValueList[{"columnName":"order_id","logicTableName":"tb_order","values":[1000]},{"columnName":"user_id","logicTableName":"tb_order","values":[123456]}]
     selected db=db_order_2
     */
    @Test
    public void insert_Test() {
        TbOrder item = new TbOrder();
        item.setOrderId(2000L);
        item.setUserId(123456L);
        tbOrderMapper.insert(item);
    }

    /***
     //只有一个分片键的时候
     DatabaseShardingAlgorithmForComplex->collection["db_order_1","db_order_2"]
     DatabaseShardingAlgorithmForComplex->shardingValueList[{"columnName":"order_id","logicTableName":"tb_order","valueRange":{"empty":false}}]
     selected db=db_order_2
     */
    @Test
    public void selectForBetween1_Test(){
        Map<String,Object> params=new HashMap<>();
        params.put("beginVal",10L);
        params.put("endVal",1215876039572717568L);
        List<TbOrder> orderList=tbOrderMapper.selectForBetween(params);
        System.out.println(FastJsonUtil.serialize(orderList));
    }
}
