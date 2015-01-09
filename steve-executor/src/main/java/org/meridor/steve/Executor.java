package org.meridor.steve;

public interface Executor {

    JobResult execute(JobRun jobRun) throws SteveException;

}
