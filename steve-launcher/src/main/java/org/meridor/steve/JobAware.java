package org.meridor.steve;

public interface JobAware {

    boolean jobExists(String jobId, Class<?> inputDataType, Class<?> returnDataType);

}
