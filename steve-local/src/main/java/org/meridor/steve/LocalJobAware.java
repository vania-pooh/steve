package org.meridor.steve;

public class LocalJobAware implements JobAware {

    private final Provider provider;

    public LocalJobAware(Provider provider) {
        this.provider = provider;
    }

    @Override
    public boolean jobExists(String jobId, Class<?> inputDataType, Class<?> returnDataType) {
        return provider.exists(new JobSignature(jobId, inputDataType, returnDataType));
    }

}
