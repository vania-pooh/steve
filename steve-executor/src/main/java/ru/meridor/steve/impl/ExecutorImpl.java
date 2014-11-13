package ru.meridor.steve.impl;

import com.google.inject.Inject;
import ru.meridor.steve.Executor;
import ru.meridor.steve.Job;
import ru.meridor.steve.Provider;
import ru.meridor.steve.SteveException;

public class ExecutorImpl implements Executor {

    private final Provider jobProvider;

    @Inject
    public ExecutorImpl(Provider jobProvider) {
        this.jobProvider = jobProvider;
    }

    public Object execute(String jobId, Object data, Class<?> inputDataType, Class<?> returnDataType) throws SteveException {
        @SuppressWarnings("unchecked")
        Job<Object, Object> job = (Job<Object, Object>) jobProvider.get(jobId, inputDataType, returnDataType);
        try {
            return job.execute(data);
        } catch (Exception e) {
            throw new SteveException(e);
        }
    }

}
