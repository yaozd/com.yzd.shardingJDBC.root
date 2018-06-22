package com.yzd.shardingJDBC.demo2.dao.mapper;

import com.yzd.shardingJDBC.demo2.entity.TbOrderPay;

public interface TbOrderPayMapper {
    int insert(TbOrderPay record);

    int insertSelective(TbOrderPay record);

    TbOrderPay selectByPrimaryKey(Long payId);

    int updateByPrimaryKeySelective(TbOrderPay record);

    int updateByPrimaryKey(TbOrderPay record);
}