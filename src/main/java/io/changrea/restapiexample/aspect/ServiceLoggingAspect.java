package io.changrea.restapiexample.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ServiceLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* *..*Service.*(..))")
    public Object serviceLog(ProceedingJoinPoint jp) throws Throwable {
        logger.info("Service start: " + jp.getSignature().toString());
        try {
            // 대상 메서드 실행
            Object result = jp.proceed();
            logger.info("Service end: " + jp.getSignature().toString());
            logger.info("Return value: " + result);
            return result;
        } catch (Exception e) {
            logger.error("Service error: " + jp.getSignature().toString());
            e.printStackTrace();
            throw e;
        }
    }
}
