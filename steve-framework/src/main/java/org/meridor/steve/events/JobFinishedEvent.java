package org.meridor.steve.events;

import org.meridor.steve.JobResult;

public class JobFinishedEvent {

    private final JobResult jobResult;

    public JobFinishedEvent(JobResult jobResult) {
        this.jobResult = jobResult;
    }

    public JobResult getJobResult() {
        return jobResult;
    }
}
