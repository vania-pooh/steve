package ru.meridor.steve;

import com.google.inject.ImplementedBy;

@ImplementedBy(JobExecutor.class)
public interface Executor {

    Object execute(String jobId, Object data, Class<?> inputDataType, Class<?> returnDataType) throws SteveException;

}
