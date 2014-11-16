package ru.meridor.steve.processor.methods;

import ru.meridor.steve.JobSignature;
import ru.meridor.steve.annotations.Job;
import ru.meridor.steve.annotations.JobCollection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MethodUtils {

    public static final String METHOD_DELIMITER = "#";
    
    public static Class getMethodGenericParameter(Method method, int parameterIndex) {
        Type returnType = method.getGenericReturnType();

        if (returnType instanceof ParameterizedType){
            ParameterizedType type = (ParameterizedType) returnType;
            Type[] typeArguments = type.getActualTypeArguments();
            return (Class) typeArguments[parameterIndex];
        }
        return Void.class; //TODO: not sure whether this is safe
    }

    public static String[] methodToJobIds(Method method) {
        List<String> jobIds = new ArrayList<>();
        
        String className = method.getDeclaringClass().getCanonicalName();
        String methodName = method.getName();
        String jobId = className + METHOD_DELIMITER + methodName;
        jobIds.add(jobId);

        Optional<String> jobAlias = (method.isAnnotationPresent(Job.class)) ?
                Optional.ofNullable(method.getAnnotation(Job.class).id())
                : Optional.empty();

        boolean isJobAliasCorrect = jobAlias.isPresent() && !jobAlias.get().isEmpty();
        if (isJobAliasCorrect) {
            jobIds.add(jobAlias.get());
        }
        
        if (method.getDeclaringClass().isAnnotationPresent(JobCollection.class)) {
            Optional<String> collectionAlias = Optional.ofNullable(
                    method.getDeclaringClass().getAnnotation(JobCollection.class).id()
            );

            if (collectionAlias.isPresent() && !collectionAlias.get().isEmpty()) {
                String jobAliasFromCollection = collectionAlias.get() + METHOD_DELIMITER + methodName;
                jobIds.add(jobAliasFromCollection);
            }

            if (isJobAliasCorrect) {
                String collectionAndJobAliases = collectionAlias.get() + METHOD_DELIMITER + jobAlias.get();
                jobIds.add(collectionAndJobAliases);
            }
        }

        return jobIds.toArray(new String[jobIds.size()]);
    }

    public static List<JobSignature> twoParameterGenericToJobSignatures(Method method) {
        Class<?> jobInputType = getMethodGenericParameter(method, 0);
        Class<?> jobReturnType = getMethodGenericParameter(method, 1);
        return Arrays.stream(methodToJobIds(method))
                .map(jobId -> new JobSignature(jobId, jobInputType, jobReturnType))
                .collect(Collectors.toList());

    }

    public static boolean methodReturnTypeEquals(Method method, Class<?> cls) {
        return method.getReturnType().equals(cls);
    }

    public static boolean isMethodStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    private MethodUtils() {}
}
