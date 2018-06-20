package com.yzd.shardingJDBC.demo.config;

import com.yzd.shardingJDBC.demo.utils.FastJsonUtil;
import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

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
        for (String each : collection) {
            String db_suffix=preciseShardingValue.getValue()>1L?"_1":"_2";
            if(each.endsWith(db_suffix)){
                System.out.println("selected db="+each);
                return each;
            }
        }
        throw new UnsupportedOperationException("DatabaseShardingAlgorithmForPrecise");
    }
}
