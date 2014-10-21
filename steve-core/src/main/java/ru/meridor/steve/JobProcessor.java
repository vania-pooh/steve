package ru.meridor.steve;

import ru.meridor.steve.processor.JobEventListener;

public interface JobProcessor<I, O> {

    O processSync(Job<I, O> job);

    void processAsync(Job<I, O> job, JobEventListener eventListener);

}
