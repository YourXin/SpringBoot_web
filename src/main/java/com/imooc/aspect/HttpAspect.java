package com.imooc.aspect;


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


    @AfterReturning(returning = "object",pointcut = "log2()")
    public void doAfterReturning(Object object){
        logger.info("response={}", object);
    }

}
