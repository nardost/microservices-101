package info.akaki.usage.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @AfterThrowing(pointcut = "execution(* info.akaki.usage.service.*.*(..))", throwing = "exception")
    public void logServiceException(Exception exception) {
        log.error(exception.getMessage(), exception);
    }
}
