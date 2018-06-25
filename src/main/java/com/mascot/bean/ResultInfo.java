package com.mascot.bean;



import java.io.Serializable;

/**
 * Created by j on 2018/3/1.
 */
public class ResultInfo implements Serializable{
    private static final long serialVersionUID = -1418031281886709162L;
    private Integer code= 200;
    private String msg="操作成功";
    private Object result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
