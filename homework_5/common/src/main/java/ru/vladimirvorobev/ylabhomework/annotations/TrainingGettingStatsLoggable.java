package ru.vladimirvorobev.ylabhomework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация включения логгирования и аудита получения статистики тренировок.
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TrainingGettingStatsLoggable {

}
