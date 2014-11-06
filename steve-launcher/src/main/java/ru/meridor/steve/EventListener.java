package ru.meridor.steve;

import java.util.Optional;

public interface EventListener<T, R> {

    void beforeJobStarted(JobSignature jobSignature, T inputData) throws Exception;

    void afterJobStarted(JobSignature jobSignature, T inputData) throws Exception;

    void beforeJobFinished(JobSignature jobSignature, T inputData, R returnData) throws Exception;

    void afterJobFinished(JobSignature jobSignature, T inputData, R returnData) throws Exception;

    void jobInterrupted(JobSignature jobSignature, Optional<Throwable> e) throws Exception;

}
