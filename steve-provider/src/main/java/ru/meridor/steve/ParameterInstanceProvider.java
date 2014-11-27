package ru.meridor.steve;

/**
 * Returns parameter instances to be injected to methods and classes when creating jobs
 */
public interface ParameterInstanceProvider {

    boolean containsInstance(Class<?> parameterClass);

    <T> T provide(Class<T> parameterClass);

}
