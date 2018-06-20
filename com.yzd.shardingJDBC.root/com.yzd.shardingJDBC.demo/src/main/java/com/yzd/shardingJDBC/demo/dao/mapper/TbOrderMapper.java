package com.yzd.shardingJDBC.demo.dao.mapper;

import com.yzd.shardingJDBC.demo.entity.TbOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TbOrderMapper {
    int insert(TbOrder record);

    int insertSelective(TbOrder record);

    TbOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(TbOrder record);

    int updateByPrimaryKey(TbOrder record);
    /***
     * 创建表
     * @return
     */
    int createTbOrderIfNotExistsTable();

    /***
     *
     * @param orderIdList
     * @return
     */
    List<TbOrder> selectForIn1(@Param("orderIdList")String orderIdList);

    /***
     *
     * @param params
     * @return
     */
    List<TbOrder> selectForIn2(Map<String,Object> params);

    /***
     *
     * @param params
     * @return
     */
    //List<TbOrder> selectForBetween(@Param("params")Map<String,Object> params);
    List<TbOrder> selectForBetween(Map<String,Object> params);

    /***
     *
     * @param record
     * @return
     */
    List<TbOrder> selectSelective(TbOrder record);
}