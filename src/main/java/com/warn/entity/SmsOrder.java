package com.warn.entity;

/**
 * 短信顺序 以及对应的时间
 * Created by netlab606 on 2017/6/10.
 */
public class SmsOrder implements Comparable<SmsOrder>{

    private Integer id;
    private Integer orderSms; //顺序
    private Integer timeSms;//对应的时间

    @Override
    public int compareTo(SmsOrder o) {
        return o.getOrderSms()-this.getOrderSms();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderSms() {
        return orderSms;
    }

    public void setOrderSms(Integer orderSms) {
        this.orderSms = orderSms;
    }

    public Integer getTimeSms() {
        return timeSms;
    }

    public void setTimeSms(Integer timeSms) {
        this.timeSms = timeSms;
    }

    @Override
    public String toString() {
        return "SmsOrder{" +
                "id=" + id +
                ", orderSms=" + orderSms +
                ", timeSms=" + timeSms +
                '}';
    }
}
