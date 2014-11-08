package ru.meridor.steve;

import java.util.Optional;

public interface EventListener<T, R> {

    void beforeJobStarted(JobSignature<T, R> jobSignature, T inputData) throws Exception;

    void afterJobStarted(JobSignature<T, R> jobSignature, T inputData) throws Exception;

    void beforeJobFinished(JobSignature<T, R> jobSignature, T inputData, R returnData) throws Exception;

    void afterJobFinished(JobSignature<T, R> jobSignature, T inputData, R returnData) throws Exception;

    void jobInterrupted(JobSignature<T, R> jobSignature, Optional<Throwable> e) throws Exception;

}
