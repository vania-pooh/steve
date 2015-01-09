package org.meridor.steve.impl;

import org.meridor.steve.Executor;
import org.meridor.steve.Job;
import org.meridor.steve.JobResult;
import org.meridor.steve.JobRun;
import org.meridor.steve.Provider;
import org.meridor.steve.SteveException;

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
            throw new SteveException(e);
        }
    }

}
