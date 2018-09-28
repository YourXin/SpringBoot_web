#表单验证
1. 在属性上使用@Min(value=18, message="未成年")等注解
2. 在Controller方法参数上使用@Valid 验证参数,并使用BindingResult bindingResult 参数获取对应的message信息  
```
    @Min(value=18, message = "未成年少女禁止入内")
    private Integer age;
```
```
    @PostMapping(value = "/girls")
    public Girl girlAdd(@Valid Girl girl, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return null;
        }

        return girlRepository.save(girl);
    }
```

#AOP处理
Aspect Oriented Programming  
将通用逻辑从业务中划分出来    
用途:记录http请求/数据库操作

#异常处理
1. 抛出异常
2. 建立异常处理类,类使用@ControllerAdvice注解, 方法使用@Exception(value=Exception.class)

```
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e){
        if(e instanceof GirlException){
            GirlException girlException = (GirlException) e;
            return ResultUtil.error(girlException.getCode(), girlException.getMessage());
        }else{
            return ResultUtil.error(-1, "未知错误");
        }

    }

```
```
Result.java

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
```

```
//ResultUtil.java
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
```
1


