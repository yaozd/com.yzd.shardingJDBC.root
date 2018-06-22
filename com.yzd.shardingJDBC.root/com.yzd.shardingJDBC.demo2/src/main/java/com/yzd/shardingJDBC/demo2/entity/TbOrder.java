package com.yzd.shardingJDBC.demo2.entity;

import java.util.Date;

public class TbOrder {
    private Long orderId;

    private Long userId;

    private Date oCreateTime;

    private Date oUpdateTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getoCreateTime() {
        return oCreateTime;
    }

    public void setoCreateTime(Date oCreateTime) {
        this.oCreateTime = oCreateTime;
    }

    public Date getoUpdateTime() {
        return oUpdateTime;
    }

    public void setoUpdateTime(Date oUpdateTime) {
        this.oUpdateTime = oUpdateTime;
    }
}