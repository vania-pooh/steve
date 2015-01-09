package org.meridor.steve.events;

import org.meridor.steve.JobRun;

public class JobInterruptedEvent {

    private final JobRun jobRun;

    private final Exception exception;

    public JobInterruptedEvent(JobRun jobRun, Exception exception) {
        this.jobRun = jobRun;
        this.exception = exception;
    }

    public JobRun getJobRun() {
        return jobRun;
    }

    public Exception getException() {
        return exception;
    }
}
