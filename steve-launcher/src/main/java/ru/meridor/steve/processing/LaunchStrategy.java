package ru.meridor.steve.processing;

import ru.meridor.steve.EventListener;
import ru.meridor.steve.JobResult;

public interface LaunchStrategy {

    String getId();

    boolean asyncJobSupported();

    JobResult<?> processSync(String jobId, Object data);

    void processAsync(String id, EventListener<?, ?> eventListener);

}
