package ru.meridor.steve;

import com.google.inject.Inject;

public class JobExecutor implements Executor {

    private final Provider jobProvider;

    @Inject
    public JobExecutor(Provider jobProvider) {
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
