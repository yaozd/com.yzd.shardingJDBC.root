package com.yzd.shardingJDBC.demo.dao.mapper;

import com.yzd.shardingJDBC.demo.entity.TbOrderItem;

import java.util.List;
import java.util.Map;

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

    /***
     *
     * @param params
     * @return
     */
    List<TbOrderItem> selectForJoin1(Map<String, Object> params);
    /***
     *
     * @param params
     * @return
     */
    List<TbOrderItem> selectForJoin2(Map<String, Object> params);
}