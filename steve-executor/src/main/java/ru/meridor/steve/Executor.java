package ru.meridor.steve;

public interface Executor {

    Object execute(String jobId, Object data, Class<?> inputDataType, Class<?> returnDataType) throws SteveException;

}
