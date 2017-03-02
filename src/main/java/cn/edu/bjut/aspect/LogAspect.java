package cn.edu.bjut.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/3/2.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* cn.edu.bjut.controler.*Controller.*(..))")
    public void before(JoinPoint joinPoint){
        StringBuffer sb = new StringBuffer();
        for(Object arg : joinPoint.getArgs()){
            sb.append(arg.toString() + "|");
        }
        logger.info("Before:" + sb.toString());
    }

    @After("execution(* cn.edu.bjut.controler.*Controller.*(..))")
    public void after(JoinPoint joinPoint){
        StringBuffer sb = new StringBuffer();
        for(Object arg : joinPoint.getArgs()){
            sb.append(arg.toString() + "|");
        }
        logger.info("After:" + sb.toString());
    }

}
