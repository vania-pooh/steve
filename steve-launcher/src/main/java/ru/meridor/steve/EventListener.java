package ru.meridor.steve;

import java.util.Optional;

public interface EventListener {

    void beforeJobStarted(JobSignature jobSignature, Object inputData) throws Exception;

    void afterJobStarted(JobSignature jobSignature, Object inputData) throws Exception;

    void beforeJobFinished(JobSignature jobSignature, Object returnData) throws Exception;

    void afterJobFinished(JobSignature jobSignature, Object returnData) throws Exception;

    void jobInterrupted(JobSignature jobSignature, Optional<Throwable> e) throws Exception;

}
