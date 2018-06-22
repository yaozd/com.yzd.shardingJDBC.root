package com.yzd.shardingJDBC.demo2.dao.mapper;

import com.yzd.shardingJDBC.demo2.entity.TbOrder;

import java.util.List;
import java.util.Map;

public interface TbOrderMapper {
    int insert(TbOrder record);

    int insertSelective(TbOrder record);

    TbOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(TbOrder record);

    int updateByPrimaryKey(TbOrder record);

    List<TbOrder> selectForBetween(Map<String,Object> params);
}