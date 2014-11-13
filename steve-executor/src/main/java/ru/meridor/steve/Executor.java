package ru.meridor.steve;

import com.google.inject.ImplementedBy;
import ru.meridor.steve.impl.ExecutorImpl;

@ImplementedBy(ExecutorImpl.class)
public interface Executor {

    Object execute(String jobId, Object data, Class<?> inputDataType, Class<?> returnDataType) throws SteveException;

}
