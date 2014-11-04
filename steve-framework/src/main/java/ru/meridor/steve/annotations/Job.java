package ru.meridor.steve.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Some actions to do. Job is executed on the executor side (in some cases remotely).
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Job {

    String id() default "";

    /**
     * Execute job using the schedule automatically
     * @return
     */
    String schedule() default "";
}
