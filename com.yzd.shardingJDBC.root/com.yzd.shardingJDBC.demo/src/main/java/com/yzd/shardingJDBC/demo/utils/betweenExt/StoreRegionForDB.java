package com.yzd.shardingJDBC.demo.utils.betweenExt;

import java.util.ArrayList;
import java.util.List;
/**
 * 订单数据库的配置信息
 * 目前的数据库的配置信息
 * 假定每个存储块的大小为：1年半的容量
 * Created by zd.yao on 2017/11/2.
 */
public enum StoreRegionForDB {
    ////新纪元--此值一定要与IdGenerator.SJDBC_EPOCH保持相同，目前2016-11-01是当当，当前定义的,是可以改的。
    ////IdGenerator.SJDBC_EPOCH=2016-11-01 00:00:00=1477929600000L
    db_order_1(0L,154377230745599999L,"_1","订单库2017数据：2017年最大值=154377230745599999"),
    db_order_2(154377230745600000L,286648801689599999L,"_2","订单库2018数据：2018年最小值154377230745600000至2018年最大值=286648801689599999"),
    db_order_3(286648801689600000L,418920372633599999L,"_3","订单库2019数据：2019年最小值286648801689600000至2019年最大值=418920372633599999");
    StoreRegionForDB (Long begin,Long end,String suffix,String des){
        this.region=new StoreRegion(begin,end);
        this.suffix=suffix;
        this.des=des;
    }
    StoreRegion region;
    String des;
    String suffix;
    public static List<String> getCollection(StoreRegion first){
        List<String> list=new ArrayList<>();
        for (StoreRegionForDB e : StoreRegionForDB.values()){
            boolean isInclude= isInRegion(first,e.region);
            if(isInclude){
                list.add(e.suffix);
            }
        }
        return list;
    }
    //region 判断两个区间是否重叠
    private static boolean isInRegion(StoreRegion first,StoreRegion second){
        return isInRegion(first.begin,first.end,second.begin,second.end);
    }
    private static boolean isInRegion(long firstBegin,long firstEnd,long secondBegin,long secondEnd){
        long begin =Math.max(firstBegin,secondBegin);
        long end =Math.min(firstEnd, secondEnd);
        long len = end - begin;
        return len>=0;
    }
    //endregion
}
