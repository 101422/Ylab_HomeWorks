package ru.vladimirvorobev.ylabhomework.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import ru.vladimirvorobev.ylabhomework.config.SpringConfig;

/**
 * Аннотация включения стартера логгирования.
 **/
@Retention(RUNTIME)
@Target(TYPE)
@Import(SpringConfig.class)
public @interface EnableLogging {
}
