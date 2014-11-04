package ru.meridor.steve;

public interface Provider {

    <T, R> Job<T, R> get(String jobId, Class<T> inputDataType, Class<R> returnDataType) throws SteveException;

}
