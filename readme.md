
[SpringBoot web进阶慕课网](https://www.imooc.com/learn/810)   
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
1. 在处理AOP业务的**类**上使用@Aspect和@Component注解
2. 在方法上使用@After/@Before/@AfterRunning等注解

方法时进行注解的两种方式
1. 每个方法都使用execution(public * com.imooc.**Controller.method(..))
2. 使用@PointCut 来注解一个方法,其后使用@After("methodName")来注解其他方法

```
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    //第一种写法
    //@Before("execution(public * com.imooc.controller.GirlController.girlList(..))")
    //@Before("execution(public * com.imooc.controller.GirlController.*(..))")
//    public void log(){
//        System.out.println("-------------before-----------");
//    }

//    @After("execution(public * com.imooc.controller.GirlController.* (..))")
//    public void doAfter(){
//        System.out.println("-------------------After----------------");
//    }

    //第二种写法,定义一个方法,使用@PointCut注解
    @Pointcut("execution(public * com.imooc.controller.GirlController.*(..))")
    public void log2() {
    }


    //使用方法
    @Before("log2()")
    public void doBefore(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //url
        logger.info("url={}", request.getRequestURL());
        //method
        logger.info("method={}", request.getMethod());
        //ip
        logger.info("ip={}", request.getRemoteAddr());
        //类的方法
        logger.info("class_name={}", joinPoint.getSignature().getDeclaringTypeName() + "."
                +joinPoint.getSignature().getName());
        //参数
        logger.info("args={}", joinPoint.getArgs());

        logger.info("----------doBefore2--------------");
    }

    @After("log2()")
    public void doAfter() {
        logger.info("------------after2--------------");
    }


    //返回的信息
    @AfterReturning(returning = "object",pointcut = "log2()")
    public void doAfterReturning(Object object){
        logger.info("response={}", object);
    }

}


```


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

#单元测试     
1. Service测试  
2. Controller测试  

```
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GirlControllerTest {


    /**
     * 针对Controller进行测试
     */

    @Autowired
    private MockMvc mvc;

    @Autowired
    private GirlController girlController;

    @Test
    public void girlList() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/girls213"))
                .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("abc"));
    }


```
1. 打包测试
`mvn clean package`  
使用命令进行打包的时候,会进行所有用例的测试  
2. 打包时跳过测试
```
mvn clean package -Dmaven.test.skip=true

```
