package com.imooc.utils;

import com.imooc.domain.Result;

public class ResultUtil {

    /**
     * 成功,并返回参数
     * @param object
     * @return
     */
    public static Result success(Object object) {


        Result result = new Result();

        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    /**
     * 无参数返回
     * @return
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 失败,返回错误码及提示信息
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


}
