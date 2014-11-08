package ru.meridor.steve;

import com.google.inject.ImplementedBy;
import ru.meridor.steve.impl.LauncherImpl;

@ImplementedBy(LauncherImpl.class)
public interface Launcher<T, R> {

    boolean jobExists(String jobId, Class<T> inputDataType, Class<R> returnDataType) throws SteveException;

    void launch(String jobId, T inputData, Class<R> returnDataType, EventListener<T, R> eventListener) throws SteveException;

}
