package io.changrea.restapiexample.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* *..*Controller.*(..))")
    public void controllerStartLog(JoinPoint jp) throws Throwable {
        logger.info("========================================= API CALL REQUEST: START =========================================");
        logger.info("Controller: " + jp.getSignature().toString());
    }

    @AfterReturning("execution(* *..*Controller.*(..))")
    public void controllerEndLog(JoinPoint jp) throws  Throwable {
        logger.info("=========================================  API CALL REQUEST: END  =========================================");
    }
}
