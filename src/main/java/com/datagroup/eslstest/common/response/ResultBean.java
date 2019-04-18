package com.datagroup.eslstest.common.response;

import lombok.Data;

import java.io.Serializable;

@Data
public  class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int SUCCESS = 1;

    public static final int FAIL = 0;

    public static final int NO_PERMISSION = 2;
    private String msg = "success";
    private int code = SUCCESS;
    private T data;
    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }
    public ResultBean(T data,int code) {
        super();
        this.data = data;
        this.code=code;
    }
    public ResultBean(T data,String msg) {
        super();
        this.data = data;
        this.msg=msg;
    }
    public ResultBean(String msg,int code) {
        super();
        this.msg = msg;
        this.code=code;
    }
    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = FAIL ;
    }
    public static ResultBean error(String msg){
        return new ResultBean(msg, ResultBean.FAIL);
    }
    public static ResultBean success(Object msg){
        return new ResultBean(msg);
    }
}