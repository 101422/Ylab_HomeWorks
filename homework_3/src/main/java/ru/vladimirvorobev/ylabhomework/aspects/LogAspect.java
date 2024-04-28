package ru.vladimirvorobev.ylabhomework.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import ru.vladimirvorobev.ylabhomework.annotations.*;

@Aspect
public class LogAspect {

    @Pointcut("execution(* *(..))")
    public void methodsExecution() {
    }

    @Pointcut("@annotation(authorizationLoggable) && execution(* *(..))")
    public void annotatedByAuthorizationLoggable(AuthorizationLoggable authorizationLoggable) {
    }

    @Pointcut("@annotation(trainingCreationLoggable) && execution(* *(..))")
    public void annotatedByTrainingCreationLoggable(TrainingCreationLoggable trainingCreationLoggable) {
    }

    @Pointcut("@annotation(trainingEditionLoggable) && execution(* *(..))")
    public void annotatedByTrainingEditionLoggable(TrainingEditionLoggable trainingEditionLoggable) {
    }

    @Pointcut("@annotation(trainingDeletionLoggable) && execution(* *(..))")
    public void annotatedByTrainingDeletionLoggable(TrainingDeletionLoggable trainingDeletionLoggable) {
    }

    @Pointcut("@annotation(trainingGettingStatsLoggable) && execution(* *(..))")
    public void annotatedByTrainingGettingStatsLoggable(TrainingGettingStatsLoggable trainingGettingStatsLoggable) {
    }

    @Around("methodsExecution()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Calling method " + proceedingJoinPoint.getSignature());

        long start = System.currentTimeMillis();

        Object result = proceedingJoinPoint.proceed();

        long end = System.currentTimeMillis() - start;

        System.out.println("Execution of method " + proceedingJoinPoint.getSignature() + "finished. Execution time is " + end + " ms.");

        return result;
    }

    @Around("annotatedByAuthorizationLoggable(authorizationLoggable)")
    public Object around(ProceedingJoinPoint pjp,
                         AuthorizationLoggable authorizationLoggable) throws Throwable {
        System.out.println("Authorization performed!");

        return pjp.proceed();
    }

    @Around("annotatedByTrainingCreationLoggable(trainingCreationLoggable)")
    public Object around(ProceedingJoinPoint pjp,
                         TrainingCreationLoggable trainingCreationLoggable) throws Throwable {
        System.out.println("Training creation performed!");

        return pjp.proceed();
    }

    @Around("annotatedByTrainingEditionLoggable(trainingEditionLoggable)")
    public Object around(ProceedingJoinPoint pjp,
                         TrainingEditionLoggable trainingEditionLoggable) throws Throwable {
        System.out.println("Training edition performed!");

        return pjp.proceed();
    }

    @Around("annotatedByTrainingDeletionLoggable(trainingDeletionLoggable)")
    public Object around(ProceedingJoinPoint pjp,
                         TrainingDeletionLoggable trainingDeletionLoggable) throws Throwable {
        System.out.println("Training deletion performed!");

        return pjp.proceed();
    }

    @Around("annotatedByTrainingGettingStatsLoggable(trainingGettingStatsLoggable)")
    public Object around(ProceedingJoinPoint pjp,
                         TrainingGettingStatsLoggable trainingGettingStatsLoggable) throws Throwable {
        System.out.println("Getting stats performed!");

        return pjp.proceed();
    }

}