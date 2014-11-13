package ru.meridor.steve;

public interface Provider {

    Job<?, ?> get(String jobId, Class<?> inputDataType, Class<?> returnDataType) throws SteveException;

}
