package ru.meridor.steve;

import java.io.Serializable;

public interface Executor {

    Serializable execute(JobRun jobRun) throws SteveException;

}
