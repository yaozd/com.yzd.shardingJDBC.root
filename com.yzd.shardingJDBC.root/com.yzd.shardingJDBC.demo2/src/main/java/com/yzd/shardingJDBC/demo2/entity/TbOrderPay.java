package com.yzd.shardingJDBC.demo2.entity;

import java.util.Date;

public class TbOrderPay {
    private Long payId;

    private Long orderId;

    private Date oCreateTime;

    private Date oUpdateTime;

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
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