package ru.meridor.steve.processor;

import ru.meridor.steve.Job;

public interface JobExecutor<I, O> {

    O executeSync(Job<I, O> job);

    void executeAsync(Job<I, O> job, JobEventListener eventListener);

}
