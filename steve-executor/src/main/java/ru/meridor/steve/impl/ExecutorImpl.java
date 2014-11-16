package ru.meridor.steve.impl;

import ru.meridor.steve.*;

import java.io.Serializable;

public class ExecutorImpl implements Executor {

    private final Provider jobProvider;

    public ExecutorImpl(Provider jobProvider) {
        this.jobProvider = jobProvider;
    }

    public Serializable execute(JobRun jobRun) throws SteveException {
        Job<Serializable, Serializable> job = jobProvider.get(jobRun.getSignature());
        try {
            return job.execute(jobRun.getData());
        } catch (Exception e) {
            throw new SteveException(e);
        }
    }

}
