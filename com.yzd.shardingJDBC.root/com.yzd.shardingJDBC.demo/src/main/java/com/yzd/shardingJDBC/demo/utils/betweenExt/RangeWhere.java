package com.yzd.shardingJDBC.demo.utils.betweenExt;

public class RangeWhere {
    public Long getBeginValue() {
        return beginValue;
    }

    public void setBeginValue(Long beginValue) {
        this.beginValue = beginValue;
    }

    public Long getEndValue() {
        return endValue;
    }

    public void setEndValue(Long endValue) {
        this.endValue = endValue;
    }

    private Long beginValue;
    private Long endValue;

    public RangeWhere(Long beginValue, Long endValue) {
        if(beginValue>endValue){
            throw new IllegalStateException("beginValue必须小于等于endValue，当前值：beginValue="+"beginValue="+beginValue+";endValue="+endValue);
        }
        this.beginValue = beginValue;
        this.endValue = endValue;
    }
}
