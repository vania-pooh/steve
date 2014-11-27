package ru.meridor.steve.impl;

import ru.meridor.steve.ParameterInstanceProvider;

import java.util.HashMap;
import java.util.Map;

public class EnumeratedParameterInstanceProvider implements ParameterInstanceProvider {

    private final Map<Class, Object> parameterInstancesMap = new HashMap<>();

    public EnumeratedParameterInstanceProvider(Object... parameterInstances) {
        for (Object parameterInstance : parameterInstances) {
            Class parameterClass = parameterInstance.getClass();
            parameterInstancesMap.put(parameterInstance.getClass(), parameterInstance);
            for (Class anInterface : parameterClass.getInterfaces()) {
                parameterInstancesMap.put(anInterface, parameterInstance);
            }
        }
    }

    @Override
    public boolean containsInstance(Class<?> parameterClass) {
        return parameterInstancesMap.containsKey(parameterClass);
    }

    @Override
    public <T> T provide(Class<T> parameterClass) {
        @SuppressWarnings("unchecked")
        T instance = (T) parameterInstancesMap.get(parameterClass);
        return instance;
    }
}
