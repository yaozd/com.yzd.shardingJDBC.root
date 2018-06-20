package com.yzd.shardingJDBC.demo.dao;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.yzd.shardingJDBC.demo.dao.mapper.TbOrderMapper;
import com.yzd.shardingJDBC.demo.entity.TbOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TbOrder_UnitTest extends _BaseUnitTest {
    @Autowired
    TbOrderMapper tbOrderMapper;

    /***
     * 创建分库分表的表：db_order_1.tb_order,db_order_2.tb_order-createTbOrderIfNotExistsTable
     */
    @Test
    public void createTable() {
        tbOrderMapper.createTbOrderIfNotExistsTable();
    }

    /***
     * 测试插入自动生成主键
     */
    @Test
    public void insert_setKeyGenerator_Test() {
        TbOrder item = new TbOrder();
        //item.setOrderId(123456L);
        tbOrderMapper.insert(item);
    }

    @Test
    public void insert_orderInfo_Test() {
        TbOrder item = new TbOrder();
        item.setUserId(Long.parseLong(RandomUtil.randomNumbers(4)));
        item.setoCreateTime(DateUtil.date());
        item.setoUpdateTime(DateUtil.date());
        tbOrderMapper.insert(item);
    }
    @Test
    public void selectByPrimaryKey_Test(){
        TbOrder item= tbOrderMapper.selectByPrimaryKey(1L);
    }

    /***
     * IN操作方式一：直接使用变量的方式，shardingJDBC是无法解析，preciseShardingValue的值是字符串类型，所以会报异常
     *
     */
    @Test
    public void selectForIn1_Test(){
        tbOrderMapper.selectForIn1("'1,2'");
    }

    /***
     * IN操作方式二：直接使用foreach的方式，shardingJDBC是可以正常解析，preciseShardingValue的值是Long类型
     */
    @Test
    public void selectForIn2_Test(){
        List<Long> orderIdList=new ArrayList<>();
        orderIdList.add(1L);
        orderIdList.add(2L);
        Map<String,Object> params=new HashMap<>();
        params.put("orderIdList",orderIdList);
        tbOrderMapper.selectForIn2(params);
    }
    @Test
    public void updateByPrimaryKey_Test(){
        TbOrder item=new TbOrder();
        item.setOrderId(1L);
        item.setUserId(Long.parseLong(RandomUtil.randomNumbers(4)));
        int n=tbOrderMapper.updateByPrimaryKey(item);
    }
}
