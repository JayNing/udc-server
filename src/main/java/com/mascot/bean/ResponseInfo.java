package com.mascot.bean;


/**
 * 返回信息
 * Created by liujinren on 2017/8/1.
 */
public class ResponseInfo {

    private Integer responseCode;
    private String responseMessage;
    private Object object;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
