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
    db_order_1(0L,201807L,"_1","订单库：存在2018年数据"),
    db_order_2(201807L,202000L,"_2",""),
    db_order_3(202000L,202107L,"_3","");
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
