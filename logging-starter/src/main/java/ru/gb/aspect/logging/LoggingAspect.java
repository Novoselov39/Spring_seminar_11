package ru.gb.aspect.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j // Slf4j - Simple logging facade for java
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

  private final LoggingProperties properties;

  @Pointcut("@annotation(ru.gb.aspect.logging.Logging)") // method
  public void loggingMethodsPointcut() {}

  @Pointcut("@within(ru.gb.aspect.logging.Logging)") // class
  public void loggingTypePointcut() {}

  @Around(value = "loggingMethodsPointcut() || loggingTypePointcut()")
  public Object loggingMethod(ProceedingJoinPoint pjp) throws Throwable {
    String methodName = pjp.getSignature().getName();
    String typeArgs = pjp.getArgs()[0].getClass().toString();

    String argsValue = pjp.getArgs()[0].toString();
    if(properties.getPrintArgs()){
      log.info("After -> TimesheetService#{}({} = {})", methodName, typeArgs, argsValue);
    }else {log.info("After -> TimesheetService#{}", methodName);}

//    log.info("Before -> TimesheetService#{}({} = {})", methodName, typeArgs, argsValue);
    try {
     return pjp.proceed();
    } finally {
      log.info("Before -> TimesheetService#{}", methodName);

    }
  }

}
