package com.yzd.shardingJDBC.demo.dao.mapper;

import com.yzd.shardingJDBC.demo.entity.TbOrderItem;

public interface TbOrderItemMapper {
    int insert(TbOrderItem record);

    int insertSelective(TbOrderItem record);

    TbOrderItem selectByPrimaryKey(Long itemId);

    int updateByPrimaryKeySelective(TbOrderItem record);

    int updateByPrimaryKey(TbOrderItem record);
    /***
     * 创建表
     * @return
     */
    int createTbOrderItemIfNotExistsTable();
}