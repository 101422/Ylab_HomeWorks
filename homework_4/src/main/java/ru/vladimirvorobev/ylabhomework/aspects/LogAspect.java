package ru.vladimirvorobev.ylabhomework.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.vladimirvorobev.ylabhomework.annotations.*;
import ru.vladimirvorobev.ylabhomework.services.AuditInfoService;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Аспект для логгирования.
 **/
@Aspect
@Component
public class LogAspect {

    private final AuditInfoService auditInfoService;


    public LogAspect(AuditInfoService auditInfoService) {
        this.auditInfoService = auditInfoService;
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

    /**
     * Логгирование авторизации.
     *
     * @param proceedingJoinPoint Точка соединения (join point)
     * @param authorizationLoggable аннотация
     * @return результат соединения.
     **/
    @Around("annotatedByAuthorizationLoggable(authorizationLoggable)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint,
                         AuthorizationLoggable authorizationLoggable) throws Throwable {
        System.out.println("Authorization performed");

        long now = System.currentTimeMillis();

        Timestamp sqlTimestamp = new Timestamp(now);

        auditInfoService.save("Authorization performed", sqlTimestamp);

        return proceedingJoinPoint.proceed();
    }

    /**
     * Логгирование создания тренировки.
     *
     * @param proceedingJoinPoint Точка соединения (join point)
     * @param trainingCreationLoggable аннотация
     * @return результат соединения.
     **/
    @Around("annotatedByTrainingCreationLoggable(trainingCreationLoggable)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint,
                         TrainingCreationLoggable trainingCreationLoggable) throws Throwable {
        System.out.println("Training creation performed");

        long now = System.currentTimeMillis();

        Timestamp sqlTimestamp = new Timestamp(now);

        auditInfoService.save("Training creation performed", sqlTimestamp);

        return proceedingJoinPoint.proceed();
    }

    /**
     * Логгирование редактирования тренировки.
     *
     * @param proceedingJoinPoint Точка соединения (join point)
     * @param trainingEditionLoggable аннотация
     * @return результат соединения.
     **/
    @Around("annotatedByTrainingEditionLoggable(trainingEditionLoggable)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint,
                         TrainingEditionLoggable trainingEditionLoggable) throws Throwable {
        System.out.println("Training edition performed");

        long now = System.currentTimeMillis();

        Timestamp sqlTimestamp = new Timestamp(now);

        auditInfoService.save("Training edition performed", sqlTimestamp);

        return proceedingJoinPoint.proceed();
    }

    /**
     * Логгирование удаления тренировки.
     *
     * @param proceedingJoinPoint Точка соединения (join point)
     * @param trainingDeletionLoggable аннотация
     * @return результат соединения.
     **/
    @Around("annotatedByTrainingDeletionLoggable(trainingDeletionLoggable)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint,
                         TrainingDeletionLoggable trainingDeletionLoggable) throws Throwable {
        System.out.println("Training deletion performed");

        long now = System.currentTimeMillis();

        Timestamp sqlTimestamp = new Timestamp(now);

        auditInfoService.save("Training deletion performed", sqlTimestamp);

        return proceedingJoinPoint.proceed();
    }

    /**
     * Логгирование получения статистики.
     *
     * @param proceedingJoinPoint Точка соединения (join point)
     * @param trainingGettingStatsLoggable аннотация
     * @return результат соединения.
     **/
    @Around("annotatedByTrainingGettingStatsLoggable(trainingGettingStatsLoggable)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint,
                         TrainingGettingStatsLoggable trainingGettingStatsLoggable) throws Throwable {
        System.out.println("Getting stats performed");

        long now = System.currentTimeMillis();

        Timestamp sqlTimestamp = new Timestamp(now);

        auditInfoService.save("Getting stats performed", sqlTimestamp);

        return proceedingJoinPoint.proceed();
    }

}