package com.yzd.shardingJDBC.demo2.dao.mapper;

import com.yzd.shardingJDBC.demo2.entity.TbOrderItem;

public interface TbOrderItemMapper {
    int insert(TbOrderItem record);

    int insertSelective(TbOrderItem record);

    TbOrderItem selectByPrimaryKey(Long itemId);

    int updateByPrimaryKeySelective(TbOrderItem record);

    int updateByPrimaryKey(TbOrderItem record);
}