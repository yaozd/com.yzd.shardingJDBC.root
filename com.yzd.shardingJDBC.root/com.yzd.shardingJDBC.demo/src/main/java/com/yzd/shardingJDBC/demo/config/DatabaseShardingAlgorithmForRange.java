package com.yzd.shardingJDBC.demo.config;

import com.google.common.collect.Range;
import com.yzd.shardingJDBC.demo.utils.betweenExt.StoreRegion;
import com.yzd.shardingJDBC.demo.utils.betweenExt.StoreRegionForDB;
import com.yzd.shardingJDBC.demo.utils.fastJsonExt.FastJsonUtil;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseShardingAlgorithmForRange implements RangeShardingAlgorithm<Long> {
    /***
     * RangeShardingAlgorithm是可选的，用于处理BETWEEN AND分片
     * @param collection
     * @param rangeShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        System.out.println("DatabaseShardingAlgorithmForRange->collection"+ FastJsonUtil.serialize(collection));
        System.out.println("DatabaseShardingAlgorithmForRange->rangeShardingValue"+FastJsonUtil.serialize(rangeShardingValue));
        System.out.println("DatabaseShardingAlgorithmForRange->rangeShardingValue:lowerEndpoint="+rangeShardingValue.getValueRange().lowerEndpoint()+";upperEndpoint="+rangeShardingValue.getValueRange().upperEndpoint());
        Range<Long> range=rangeShardingValue.getValueRange();
        StoreRegion storeRegion=new StoreRegion(range.lowerEndpoint(),range.upperEndpoint());
        List<String> suffixList= StoreRegionForDB.getCollection(storeRegion);
        if(suffixList.isEmpty()){
            throw new UnsupportedOperationException("DatabaseShardingAlgorithmForRange->suffixList.isEmpty()");
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
            throw new UnsupportedOperationException("DatabaseShardingAlgorithmForRange->shardingDB.isEmpty()");
        }
        return shardingDB;
    }
}
