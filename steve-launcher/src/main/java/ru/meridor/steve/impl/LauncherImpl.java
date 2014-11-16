package ru.meridor.steve.impl;

import ru.meridor.steve.*;

import java.io.Serializable;
import java.util.Collections;

public class LauncherImpl implements Launcher {


    private final JobAware jobAware;

    private final LaunchStrategy launchStrategy;

    public LauncherImpl(LaunchStrategy launchStrategy, JobAware jobAware) {
        this.launchStrategy = launchStrategy;
        this.jobAware = jobAware;
    }

    @Override
    public <R> void launch(String jobId, Serializable inputData, Class<R> returnDataType) throws SteveException {
        Class<?> inputDataType = inputData.getClass();
        if (!jobAware.jobExists(jobId, inputDataType, returnDataType)){
            throw new SteveException(String.format("Job not found: %s - %s - %s", jobId, inputDataType.getCanonicalName(), returnDataType.getCanonicalName()));
        }
        JobSignature jobSignature = new JobSignature(jobId, inputDataType, returnDataType);
        launchStrategy.launch(new JobRun(jobSignature, inputData, Collections.emptyMap())); //TODO: replace with real metadata
    }

    @Override
    public void subscribe(Object handler) {
        //TODO: to be implemented!
    }
}
