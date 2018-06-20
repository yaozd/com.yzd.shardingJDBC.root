package com.yzd.shardingJDBC.demo.config;

import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;

public class DatabaseShardingAlgorithmForRange implements RangeShardingAlgorithm<Long> {
    /***
     * RangeShardingAlgorithm是可选的，用于处理BETWEEN AND分片
     * @param collection
     * @param rangeShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        return null;
    }
}
