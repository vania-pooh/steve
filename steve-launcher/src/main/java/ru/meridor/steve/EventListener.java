package ru.meridor.steve;

import java.util.Optional;

public interface EventListener<T> {

    void jobStarted(String id, Object data);

    void jobFinished(String id, JobResult<T> outputData);

    void jobInterrupted(String id, Optional<Throwable> e);

}
