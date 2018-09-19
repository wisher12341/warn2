package com.warn.dto;

/**
 * 短信模板参数（可变）
 * Created by netlab606 on 2017/7/14.
 */
public class Sms_template {
    private String APP_KEY;
    private String SMS_SIGN;
    private String EXTEND;
    private String SMS_TYPE;
    private String SMS_TEMPLATE_CODE;
    private String SECRET;

    public String getAPP_KEY() {
        return APP_KEY;
    }

    public void setAPP_KEY(String APP_KEY) {
        this.APP_KEY = APP_KEY;
    }

    public String getSMS_SIGN() {
        return SMS_SIGN;
    }

    public void setSMS_SIGN(String SMS_SIGN) {
        this.SMS_SIGN = SMS_SIGN;
    }

    public String getEXTEND() {
        return EXTEND;
    }

    public void setEXTEND(String EXTEND) {
        this.EXTEND = EXTEND;
    }

    public String getSMS_TYPE() {
        return SMS_TYPE;
    }

    public void setSMS_TYPE(String SMS_TYPE) {
        this.SMS_TYPE = SMS_TYPE;
    }

    public String getSMS_TEMPLATE_CODE() {
        return SMS_TEMPLATE_CODE;
    }

    public void setSMS_TEMPLATE_CODE(String SMS_TEMPLATE_CODE) {
        this.SMS_TEMPLATE_CODE = SMS_TEMPLATE_CODE;
    }

    public String getSECRET() {
        return SECRET;
    }

    public void setSECRET(String SECRET) {
        this.SECRET = SECRET;
    }
}
