package ru.meridor.steve;

import java.io.Serializable;

public interface Launcher {

    <R> void launch(String jobId, Serializable inputData, Class<R> returnDataType) throws SteveException;

    //TODO: use MBassador for event handling
    void subscribe(Object handler);

}
