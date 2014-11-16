package ru.meridor.steve;

import java.io.Serializable;

public interface Launcher {

    void launch(String jobId, Serializable inputData, Class<?> returnDataType);

    void subscribe(Object handler);

    void unsubscribe(Object handler);

}
