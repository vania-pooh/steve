package ru.meridor.steve;

import java.util.List;

public interface Processor<E> {

    boolean canProcess(E entity);

    List<JobSignature> store(E entity);

    <T, R> Job<T, R> createJob(JobSignature jobSignature) throws Exception;

}
