package com.yzd.shardingJDBC.demo.dao;

import com.yzd.shardingJDBC.demo.dao.mapper.TbOrderItemMapper;
import com.yzd.shardingJDBC.demo.entity.TbOrderItem;
import com.yzd.shardingJDBC.demo.utils.fastJsonExt.FastJsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TbOrderItem_UnitTest extends _BaseUnitTest {
    @Autowired
    TbOrderItemMapper tbOrderItemMapper;
    @Test
    public void createTable() {
        tbOrderItemMapper.createTbOrderItemIfNotExistsTable();
    }
    /***
     * 测试插入自动生成主键
     */
    @Test
    public void insert_setKeyGenerator_Test() {
        TbOrderItem item = new TbOrderItem();
        item.setOrderId(123456L);
        tbOrderItemMapper.insert(item);
    }

    /***
     * 简单的join操作
     */
    @Test
    public void selectForJoin1_Test(){
        Map<String,Object> params=new HashMap<>();
        params.put("tbOrderItem_itemId",10L);
        params.put("tbOrder_itemId",100L);
        params.put("endVal",1215876039572717568L);
        List<TbOrderItem> orderList=tbOrderItemMapper.selectForJoin1(params);
        System.out.println(FastJsonUtil.serialize(orderList));
    }

    /***
     * Join使用别名 shardingJDBC路由是正常解析
     */
    @Test
    public void selectForJoin2_Test(){
        Map<String,Object> params=new HashMap<>();
        params.put("tbOrderItem_itemId",10L);
        params.put("tbOrder_itemId",100L);
        List<TbOrderItem> orderList=tbOrderItemMapper.selectForJoin2(params);
        System.out.println(FastJsonUtil.serialize(orderList));
    }
}
