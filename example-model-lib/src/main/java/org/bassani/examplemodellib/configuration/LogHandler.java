package org.bassani.examplemodellib.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

import static br.com.example.purchasesimulatormodellib.util.Constants.END;
import static br.com.example.purchasesimulatormodellib.util.Constants.ERRO;
import static br.com.example.purchasesimulatormodellib.util.Constants.START;
import static br.com.example.purchasesimulatormodellib.util.Constants.TIME_TRACK;

@Aspect
@Component
@Slf4j
public class LogHandler {

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
    }

    @Pointcut("within(@org.springframework.stereotype.Controller *) || within(@org.springframework.web.bind" +
            ".annotation.RestController *)")
    protected void controller() {
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    protected void service() {
    }

    @Pointcut("within(@org.springframework.stereotype.Component *)")
    protected void component() {
    }

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    protected void repository() {
    }

    @Pointcut("within(br.com.example..*)")
    protected void projectPackage() {
    }

    @Pointcut("execution(public * *(..))")
    protected void loggingPublicOperation() {
    }

    @Pointcut("@annotation(br.com.example.purchasesimulatormodellib.constraints.LogAspect)")
    protected void logAspect() {
    }

    private String getValue(Object result) {
        String returnValue = null;
        if (Objects.nonNull(result)) {
            if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
                returnValue = ReflectionToStringBuilder.toString(result);
            } else {
                returnValue = result.toString();
            }
        }
        return returnValue;
    }

    //before -> Any resource annotated with @Controller annotation
    //and all method and function taking HttpServletRequest as first parameter
    @Before("allMethod() && projectPackage() && (controller() || service() || component() || repository() || logAspect())")
    public void logBefore(JoinPoint joinPoint) {
        log.info("event={} method={}#{} args={}", START, joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        //log.debug("Target class : " + joinPoint.getTarget().getClass().getName());
    }

    @Around("(controller() && projectPackage()) || logAspect()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint)
            throws
            Throwable {

        var start = LocalTime.now();
        Object proceed = joinPoint.proceed();
        var end = LocalTime.now();
        var duration = Duration.between(start, end);

        log.info("event={} method={}#{} start={} end={} duration={}", TIME_TRACK,
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), start, end,
                duration.toMinutesPart() + ":" + duration.toSecondsPart() + "." + duration.toMillisPart());

        return proceed;
    }

    //After -> Any method throws an exception ...Log it
    @AfterThrowing(pointcut = "allMethod() && projectPackage() && (controller() || service() || component() || logAspect())",
            throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint,
                                 Exception exception) {
        log.error("event={} method={}#{} cause={}", ERRO, joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), exception);
    }

    //After -> All method return a value
    @AfterReturning(pointcut = "allMethod() && projectPackage() && (controller() || service() || component() || repository() || logAspect())",
            returning = "result")
    public void logAfter(JoinPoint joinPoint,
                         Object result) {
        String returnValue = this.getValue(result);
        log.info("event={} method={}#{} result={}", END, joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), returnValue);
    }
}
