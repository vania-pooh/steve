package ru.meridor.steve.job;

import ru.meridor.steve.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnnotationProcessor {

    private final Class aClass;

    public AnnotationProcessor(Class aClass) {
        if (aClass == null) {
            throw new IllegalArgumentException("Class instance can't be null");
        }
        this.aClass = aClass;
    }

    public Optional<Method> getOnStartMethod() {
        return getMethodByAnnotation(OnStart.class, 0);
    }

    public Optional<Method> getOnFinishMethod() {
        return getMethodByAnnotation(OnFinish.class, 0);
    }

    public Optional<Method> getInputMethod() {
        return getMethodByAnnotation(Input.class, 1);
    }

    public Optional<Method> getOutputMethod() {
        return getMethodByAnnotation(Output.class, 0);
    }

    public Optional<Method> getOnInterruptMethod() {
        return getMethodByAnnotation(OnInterrupt.class, 0);
    }

    private Optional<Method> getMethodByAnnotation(Class<? extends Annotation> annotationClass, int parametersCount) {
        List<Method> methods = Arrays.asList(aClass.getMethods()).stream()
                .filter(
                        method ->
                                method.isAnnotationPresent(annotationClass) &&
                                (method.getParameterCount() == parametersCount)
                )
                .collect(Collectors.toList());
        if (methods.size() > 1) {
            throw new IllegalStateException("Only one method annotated with " + annotationClass.getName() + " annotation is allowed");
        }
        return methods.isEmpty() ?
                Optional.empty() :
                Optional.ofNullable(methods.get(0));
    }

    public Class<?> getInputType() {
        Optional<Method> method = getInputMethod();
        if (!method.isPresent()) {
            return Void.class;
        }
        return method.get().getParameters()[0].getType();
    }

    public Class<?> getOutputType() {
        Optional<Method> method = getOutputMethod();
        return method.isPresent() ?
                method.get().getReturnType() :
                Void.class;
    }

    private Job getJobAnnotation() {
        return (Job) aClass.getAnnotation(Job.class);
    }

    public boolean isAsyncJob() {
        return getJobAnnotation().async();
    }

}
