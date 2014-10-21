package ru.meridor.steve.processor;

import ru.meridor.steve.Job;

public interface Scheduler {

    <I, O> void schedule(Job<I, O> job);

}
