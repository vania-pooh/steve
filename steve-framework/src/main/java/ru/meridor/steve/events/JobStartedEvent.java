package ru.meridor.steve.events;

import ru.meridor.steve.JobRun;

import java.io.Serializable;

public class JobStartedEvent {

    private final JobRun jobRun;

    public JobStartedEvent(JobRun jobRun) {
        this.jobRun = jobRun;
    }

    public JobRun getJobRun() {
        return jobRun;
    }
}
