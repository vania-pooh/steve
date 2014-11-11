package ru.meridor.steve;

import com.google.inject.ImplementedBy;
import ru.meridor.steve.impl.LauncherImpl;

@ImplementedBy(LauncherImpl.class)
public interface Launcher {

    <T, R> void launch(String jobId, T inputData, Class<R> returnDataType) throws SteveException;

    //TODO: use MBassador for event handling
    void subscribe(Object handler);

}
