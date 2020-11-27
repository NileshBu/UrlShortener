package com.project.urlshortner.aspectj;

import com.project.urlshortner.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogInputOutputAspect {

  @Around("@annotation(LogInputOutput)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();
    MethodSignature ms = (MethodSignature) joinPoint.getStaticPart().getSignature();

    StringBuilder sb = new StringBuilder();
    for (int argIndex = 0; argIndex < args.length; argIndex++) {
      sb.append(args[argIndex]);
      if (argIndex + 1 != args.length) {
        sb.append(", ");
      }
    }
  
    if (args.length > 0) {
      log.info(Constant.METHOD_CALL_WITH_PARAMETERS, ms.getName(), sb.toString());
    } else {
      log.info(Constant.METHOD_CALL_WITHOUT_PARAMETERS, ms.getName());
    }

    final Object proceed = joinPoint.proceed();

    if (proceed != null) {
      log.info(Constant.METHOD_END_WITH_OUTPUT_PARAMETERS, ms.getName(), proceed.toString());
    } else {
      log.info(Constant.METHOD_END_WITHOUT_PARAMETERS, ms.getName());
    }

    return proceed;
  }

}
