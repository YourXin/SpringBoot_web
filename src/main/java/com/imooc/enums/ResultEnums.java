package com.imooc.enums;

public enum ResultEnums {

    PRIMARY_SCHOOL(100,"上小学中"),
    MIDDLE_SCHOLL(101,"上高中")
    ;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
