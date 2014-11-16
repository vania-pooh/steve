package ru.meridor.steve;

import java.io.Serializable;
import java.util.List;

public interface Processor<E> {

    boolean canProcess(E entity);

    List<JobSignature> store(E entity);

    <T extends Serializable, R extends Serializable> Job<T, R> createJob(JobSignature jobSignature) throws Exception;

}
