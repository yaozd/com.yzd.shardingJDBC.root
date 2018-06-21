package com.yzd.shardingJDBC.demo.dao.mapper;

import com.yzd.shardingJDBC.demo.entity.TbOrderPay;

public interface TbOrderPayMapper {
    int insert(TbOrderPay record);

    int insertSelective(TbOrderPay record);

    TbOrderPay selectByPrimaryKey(Long payId);

    int updateByPrimaryKeySelective(TbOrderPay record);

    int updateByPrimaryKey(TbOrderPay record);

    int createTbOrderPayIfNotExistsTable();
}