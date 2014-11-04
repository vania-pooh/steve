package ru.meridor.steve;

public class JobExecutor {

    private final JobProvider jobProvider;

    public JobExecutor(JobProvider jobProvider) {
        this.jobProvider = jobProvider;
    }

    public <T, R> R execute(String jobId, T data, Class<T> inputDataType, Class<R> returnDataType) throws SteveException {
        Job<T, R> job = jobProvider.get(jobId, inputDataType, returnDataType);
        try {
            return job.execute(data);
        } catch (Exception e) {
            throw new SteveException(e);
        }
    }

}
