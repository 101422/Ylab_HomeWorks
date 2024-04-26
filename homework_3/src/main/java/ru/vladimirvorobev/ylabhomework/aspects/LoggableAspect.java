package ru.vladimirvorobev.ylabhomework.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class LoggableAspect {
    
    @Pointcut("execution(* *(..))")
    public void annotatedByLoggable() {
    }

    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Calling method " + proceedingJoinPoint.getSignature());

        long start = System.currentTimeMillis();

        Object result = proceedingJoinPoint.proceed();

        long end = System.currentTimeMillis() - start;

        System.out.println("Execution of method " + proceedingJoinPoint.getSignature() + "finished. Execution time is " + end + " ms.");

        return result;
    }

/*

    @AfterReturning(value = "methodExecuting()", returning = "returningValue")
    public void recordSuccessfulExecution(JoinPoint joinPoint, Object returningValue) {
        if (returningValue != null) {
            System.out.printf("Успешно выполнен метод - %s, класса- %s, с результатом выполнения - %s\n",
                    joinPoint.getSignature().getName(),
                    joinPoint.getSourceLocation().getWithinType().getName(),
                    returningValue);
        }
        else {
            System.out.printf("Успешно выполнен метод - %s, класса- %s\n",
                    joinPoint.getSignature().getName(),
                    joinPoint.getSourceLocation().getWithinType().getName());
        }
    }

    @AfterThrowing(value = "methodExecuting()", throwing = "exception")
    public void recordFailedExecution(JoinPoint joinPoint, Exception exception) {
        System.out.printf("Метод - %s, класса- %s, был аварийно завершен с исключением - %s\n",
                joinPoint.getSignature().getName(),
                joinPoint.getSourceLocation().getWithinType().getName(),
                exception);
    }*/
}