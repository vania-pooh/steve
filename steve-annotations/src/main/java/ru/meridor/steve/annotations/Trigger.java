package ru.meridor.steve.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Something that triggers actions.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Trigger {

    /**
     * Check trigger condition using the schedule
     * @return
     */
    String schedule() default "";

}
