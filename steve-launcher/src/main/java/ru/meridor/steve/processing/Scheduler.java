package ru.meridor.steve.processing;

import ru.meridor.steve.JobSignature;

public interface Scheduler {

    <T, R> void schedule(JobSignature<T, R> jobSignature);

}
