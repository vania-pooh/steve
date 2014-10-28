package ru.meridor.steve;

import java.util.Optional;

public interface EventListener<T, R> {

    void jobStarted(String id, T data);

    void jobFinished(String id, JobResult<R> outputData);

    void jobInterrupted(String id, Optional<Throwable> e);

}
