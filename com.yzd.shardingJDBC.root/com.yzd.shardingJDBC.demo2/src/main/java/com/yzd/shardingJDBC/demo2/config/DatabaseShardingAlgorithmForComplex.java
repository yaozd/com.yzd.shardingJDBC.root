package com.yzd.shardingJDBC.demo2.config;

import com.yzd.shardingJDBC.demo2.fastJsonExt.FastJsonUtil;
import io.shardingjdbc.core.api.algorithm.sharding.ListShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.ShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseShardingAlgorithmForComplex implements ComplexKeysShardingAlgorithm {
    @Override
    public Collection<String> doSharding(Collection<String> collection, Collection<ShardingValue> complexShardingValue) {
        System.out.println("DatabaseShardingAlgorithmForComplex->collection"+ FastJsonUtil.serialize(collection));
        System.out.println("DatabaseShardingAlgorithmForComplex->shardingValueList"+FastJsonUtil.serialize(complexShardingValue));
        Collection<String> suffixListForUserId=getShardingValueForUserId(complexShardingValue);
        Collection<String> suffixListForOrderId=getShardingValueForOrderId(complexShardingValue);
        String db_suffix="_2";
        List<String> shardingDB = new ArrayList<>();
        for(String suffixForUserId:suffixListForUserId){
            for(String suffixForOrderId:suffixListForOrderId){
                String fullSuffix=String.format("_%s_%s",suffixForUserId,suffixForOrderId);
                collection.forEach(x -> {
                    if (x.endsWith(fullSuffix)) {
                        System.out.println("selected db="+x);
                        shardingDB.add(x);
                    }
                });
            }
        }
        if(shardingDB.isEmpty()){
            throw new UnsupportedOperationException("DatabaseShardingAlgorithmForComplex->shardingDB.isEmpty()");
        }
        return shardingDB;
    }
    /***
     * 伪代码=>
     */
    private Collection<String> getShardingValueForUserId(Collection<ShardingValue> shardingValues) {
        Collection<ShardingValue> shardingValuesList=shardingValues.stream().filter(item->item.getColumnName().equals("user_id")).collect(Collectors.toList());
        for (ShardingValue shardingValue:shardingValuesList) {
            if (shardingValue instanceof ListShardingValue){
                System.out.println("IN ,=操作");
                getShardingValueForPreciseByUserId((ListShardingValue) shardingValue);
            }
            if (shardingValue instanceof RangeShardingValue){
                System.out.println("BETWEEN AND操作");
                getShardingValueForPreciseByRangeByUserId((RangeShardingValue) shardingValue);
            }
        }
        return null;
    }
    private Collection<String> getShardingValueForPreciseByUserId(ListShardingValue shardingValue){
        //具本分片规则
        return null;
    }
    private Collection<String> getShardingValueForPreciseByRangeByUserId(RangeShardingValue shardingValue){
        //具本分片规则
        return null;
    }
    private Collection<String> getShardingValueForOrderId(Collection<ShardingValue> shardingValues) {
        Collection<ShardingValue> shardingValuesList=shardingValues.stream().filter(item->item.getColumnName().equals("order_id")).collect(Collectors.toList());
        for (ShardingValue shardingValue:shardingValuesList) {
            if (shardingValue instanceof ListShardingValue){
                System.out.println("IN ,=操作");
                getShardingValueForPreciseByOrderId((ListShardingValue) shardingValue);
            }
            if (shardingValue instanceof RangeShardingValue){
                System.out.println("BETWEEN AND操作");
                getShardingValueForPreciseByRangeByOrderId((RangeShardingValue) shardingValue);
            }
        }
        return null;
    }
    private Collection<String> getShardingValueForPreciseByOrderId(ListShardingValue shardingValue){
        //具本分片规则
        return null;
    }
    private Collection<String> getShardingValueForPreciseByRangeByOrderId(RangeShardingValue shardingValue){
        //具本分片规则
        return null;
    }
}
