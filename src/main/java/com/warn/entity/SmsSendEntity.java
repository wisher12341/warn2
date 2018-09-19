package com.warn.entity;

/**
 * 短信发送的实体类
 * Created by netlab606 on 2017/6/10.
 */
public class SmsSendEntity {

    private Integer id;
    private String phone;
    private Integer orderSms;//发送顺序

    private Integer timeSms;//该顺序对应的时间间隔 （单位：分钟）  另一张表维护

    @Override
    public String toString() {
        return "SmsSendEntity{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", orderSms=" + orderSms +
                ", time=" + timeSms +
                '}';
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTimeSms() {
        return timeSms;
    }

    public void setTimeSms(Integer timeSms) {
        this.timeSms = timeSms;
    }
}
