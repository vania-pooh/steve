package ru.meridor.steve;

import com.google.inject.ImplementedBy;

@ImplementedBy(JobProvider.class)
public interface Provider {

    Job<?, ?> get(String jobId, Class<?> inputDataType, Class<?> returnDataType) throws SteveException;

}
