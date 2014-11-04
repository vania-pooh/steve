package ru.meridor.steve.classes;

import ru.meridor.steve.JobSignature;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ClassUtils {

    public static boolean hasNoArgsConstructor(Class<?> jobClass) {
        Constructor<?>[] constructors = jobClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    public static String[] classToJobIds(Class<?> someClass) {
        return new String[]{someClass.getCanonicalName()}; //Add more ways to
    }

    public static List<JobSignature> twoParameterGenericClassToJobSignatures(Class<?> genericClass) {
        Type[] interfaces = genericClass.getGenericInterfaces();
        for (Type anInterface : interfaces) {
            if (anInterface instanceof ParameterizedType) {
                Class<?> inputDataType = (Class)((ParameterizedType) anInterface).getActualTypeArguments()[0];
                Class<?> returnDataType = (Class)((ParameterizedType) anInterface).getActualTypeArguments()[1];
                String[] jobIds = classToJobIds(genericClass);
                return Arrays.stream(jobIds)
                        .map(id -> new JobSignature(id, inputDataType, returnDataType))
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    private ClassUtils() {}

}
