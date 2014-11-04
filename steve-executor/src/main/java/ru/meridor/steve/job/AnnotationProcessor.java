package ru.meridor.steve.job;

import ru.meridor.steve.annotations.Job;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated
public class AnnotationProcessor {

    private final Class aClass;

    public AnnotationProcessor(Class aClass) {
        if (aClass == null) {
            throw new IllegalArgumentException("Class instance can't be null");
        }
        this.aClass = aClass;
    }
    
    public List<Method> getJobMethods() {
        return getMethodsByAnnotation(Job.class);
    }
    
    private List<Method> getMethodsByAnnotation(Class<? extends Annotation> annotationClass) {
        return Arrays.asList(aClass.getMethods()).stream()
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }

    private Jobs getClassJobAnnotation() {
        return (Jobs) aClass.getAnnotation(Job.class);
    }

}
