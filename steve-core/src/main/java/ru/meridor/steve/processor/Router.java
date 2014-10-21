package ru.meridor.steve.processor;

import ru.meridor.steve.Job;

public interface Router {

    <I, O> JobExecutor<I, O> getExecutor(Job<I, O> job);

}
