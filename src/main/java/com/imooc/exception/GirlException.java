package com.imooc.exception;


import com.imooc.enums.ResultEnums;

/**
 * Spring只对RuntimeException进行事务回滚
 */
public class GirlException extends RuntimeException{


    private Integer code;


    public GirlException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 通过枚举来实现code和message
     * @param resultEnums
     */
    public GirlException(ResultEnums resultEnums){
        super(resultEnums.getMsg());
        this.code = resultEnums.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
