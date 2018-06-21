package com.yzd.shardingJDBC.demo.config;

import com.yzd.shardingJDBC.demo.utils.betweenExt.StoreRegion;
import com.yzd.shardingJDBC.demo.utils.betweenExt.StoreRegionForDB;
import com.yzd.shardingJDBC.demo.utils.fastJsonExt.FastJsonUtil;
import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseShardingAlgorithmForPrecise implements PreciseShardingAlgorithm<Long> {
    /***
     * PreciseShardingAlgorithm是必选的，用于处理=和IN的分片
     * @param collection
     * @param preciseShardingValue
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        System.out.println("DemoDatabaseShardingAlgorithm->collection"+ FastJsonUtil.serialize(collection));
        System.out.println("DemoDatabaseShardingAlgorithm->preciseShardingValue"+FastJsonUtil.serialize(preciseShardingValue));
        return getShardingDB_2(collection, preciseShardingValue);
    }

    /***
     * 在分库分表的IN ,=操作就相当于StoreRegion t2=new StoreRegion(1L,1L);
     * order_id=1,等价于 order_id>=1 and order_id<=1;
     * 目前推荐Precise与range都使用StoreRegion方法，这样计算逻辑就统一了。
     * 同时在使用多分片键时也可以统一采用StoreRegion方法
     * @param collection
     * @param preciseShardingValue
     * @return
     */
    private String getShardingDB_2(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        StoreRegion storeRegion=new StoreRegion(preciseShardingValue.getValue(),preciseShardingValue.getValue());
        List<String> suffixList= StoreRegionForDB.getCollection(storeRegion);
        if(suffixList.isEmpty()){
            throw new UnsupportedOperationException("DatabaseShardingAlgorithmForPrecise->suffixList.isEmpty()");
        }
        if(suffixList.size()>1){
            throw new UnsupportedOperationException("DatabaseShardingAlgorithmForPrecise->suffixList.size()");
        }
        List<String> shardingDB = new ArrayList<>();
        for (String suffix:suffixList) {
            collection.forEach(x -> {
                if (x.endsWith(suffix)) {
                    shardingDB.add(x);
                }
            });
        }
        if(shardingDB.isEmpty()){
            throw new UnsupportedOperationException("DatabaseShardingAlgorithmForPrecise->shardingDB.isEmpty()");
        }
        if(shardingDB.size()>1){
            throw new UnsupportedOperationException("DatabaseShardingAlgorithmForPrecise->shardingDB.size()");
        }
        return shardingDB.get(0);
    }

    /***
     * 方法一：普通的通过简单计算来获得分片信息
     * @param collection
     * @param preciseShardingValue
     * @return
     */
    private String getShardingDB_1(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        String db_suffix=preciseShardingValue.getValue()>1L?"_1":"_2";
        for (String each : collection) {
            if(each.endsWith(db_suffix)){
                System.out.println("selected db="+each);
                return each;
            }
        }
        throw new UnsupportedOperationException("DatabaseShardingAlgorithmForPrecise");
    }
}
