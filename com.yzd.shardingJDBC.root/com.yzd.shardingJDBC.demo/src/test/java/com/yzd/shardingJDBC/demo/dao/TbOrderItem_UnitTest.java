package com.yzd.shardingJDBC.demo.dao;

import com.yzd.shardingJDBC.demo.dao.mapper.TbOrderItemMapper;
import com.yzd.shardingJDBC.demo.entity.TbOrderItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

}
