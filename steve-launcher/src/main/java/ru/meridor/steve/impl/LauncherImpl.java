package ru.meridor.steve.impl;

import com.google.common.eventbus.EventBus;
import ru.meridor.steve.*;
import ru.meridor.steve.events.JobInterruptedEvent;
import ru.meridor.steve.events.JobStartedEvent;

import java.io.Serializable;
import java.util.Collections;

public class LauncherImpl implements Launcher {

    private final JobAware jobAware;

    private final LaunchStrategy launchStrategy;

    private final EventBus eventBus;

    public LauncherImpl(LaunchStrategy launchStrategy, JobAware jobAware, EventBus eventBus) {
        this.launchStrategy = launchStrategy;
        this.jobAware = jobAware;
        this.eventBus = eventBus;
    }

    @Override
    public void launch(String jobId, Serializable inputData, Class<?> returnDataType) {
        Class<?> inputDataType = inputData.getClass();
        JobSignature jobSignature = new JobSignature(jobId, inputDataType, returnDataType);
        JobRun jobRun = new JobRun(jobSignature, inputData, Collections.emptyMap()); //TODO: replace with real metadata
        try {
            eventBus.post(new JobStartedEvent(jobRun));
            if (!jobAware.jobExists(jobId, inputDataType, returnDataType)){
                throw new SteveException(String.format("Job not found: %s - %s - %s", jobId, inputDataType.getCanonicalName(), returnDataType.getCanonicalName()));
            }
            launchStrategy.launch(jobRun);
        } catch (SteveException e) {
            eventBus.post(new JobInterruptedEvent(jobRun, e));
        }
    }

    @Override
    public void subscribe(Object handler) {
        eventBus.register(handler);
    }

    @Override
    public void unsubscribe(Object handler) {
        eventBus.unregister(handler);
    }
}
