package ru.meridor.steve.impl;

import ru.meridor.steve.*;

import java.io.Serializable;

public class ExecutorImpl implements Executor {

    private final Provider jobProvider;

    public ExecutorImpl(Provider jobProvider) {
        this.jobProvider = jobProvider;
    }

    public JobResult execute(JobRun jobRun) throws SteveException {
        Job<Serializable, Serializable> job = jobProvider.get(jobRun.getSignature());
        try {
            Serializable result = job.execute(jobRun.getData());
            return new JobResult(jobRun.getSignature(), result);
        } catch (Exception e) {
            //TODO: think about exception handling with events!
            throw new SteveException(e);
        }
    }

}
