package org.meridor.steve.events;

import org.meridor.steve.JobRun;

public class JobStartedEvent {

    private final JobRun jobRun;

    public JobStartedEvent(JobRun jobRun) {
        this.jobRun = jobRun;
    }

    public JobRun getJobRun() {
        return jobRun;
    }
}
