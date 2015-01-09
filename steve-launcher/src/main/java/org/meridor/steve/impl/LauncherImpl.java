package org.meridor.steve.impl;

import com.google.common.eventbus.EventBus;
import org.meridor.steve.JobAware;
import org.meridor.steve.JobRun;
import org.meridor.steve.JobSignature;
import org.meridor.steve.LaunchStrategy;
import org.meridor.steve.Launcher;
import org.meridor.steve.SteveException;
import org.meridor.steve.events.JobInterruptedEvent;
import org.meridor.steve.events.JobStartedEvent;

import java.io.Serializable;

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
        JobRun jobRun = new JobRun(jobSignature, inputData);
        try {
            eventBus.post(new JobStartedEvent(jobRun));
            if (!jobAware.jobExists(jobId, inputDataType, returnDataType)) {
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
