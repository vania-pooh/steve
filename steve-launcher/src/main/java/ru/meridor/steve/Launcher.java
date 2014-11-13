package ru.meridor.steve;

public interface Launcher {

    <T, R> void launch(String jobId, T inputData, Class<R> returnDataType) throws SteveException;

    //TODO: use MBassador for event handling
    void subscribe(Object handler);

}
