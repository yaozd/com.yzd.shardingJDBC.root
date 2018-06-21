package com.yzd.shardingJDBC.demo.entity;

import java.util.Date;

public class TbOrderItem {
    private Long itemId;

    private Long orderId;

    private Date oCreateTime;

    private Date oUpdateTime;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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