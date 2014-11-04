package ru.meridor.steve.methods;

import ru.meridor.steve.JobSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class MethodUtils {

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
        String className = method.getDeclaringClass().getCanonicalName();
        String methodName = method.getName();
        //TODO: add more aliases support here
        return new String[]{
                className + "#" + methodName
        };
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
