package com.mascot.exception;



/**
 * Created by j on 2018/3/1.
 */
public class ParamsException extends  RuntimeException {
    private String msg= "操作失败";
    private Integer code=300;

    public ParamsException() {
        super("操作失败");
    }

    public ParamsException(String message) {
        super(message);
        this.msg=message;
    }

    public ParamsException(String msg, Integer code) {
        super(msg);
        this.msg=msg;
        this.code=code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
