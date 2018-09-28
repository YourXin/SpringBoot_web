package com.imooc.domain;


/**
 * http请求返回的最外层对象
 */
public class Result<T> {

    /**
     * 错误码，0为成功，1为失败
     */
    private Integer code;


    /**
     * 返回的提示信息
     */
    private String msg;

    /**
     * 具体的数据内容
     */
    private T data;

    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
