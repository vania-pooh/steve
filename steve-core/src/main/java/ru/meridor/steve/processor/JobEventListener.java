package ru.meridor.steve.processor;

import ru.meridor.steve.Job;

import java.util.Optional;

public interface JobEventListener {

    <I, O> void jobStarted(Job<I, O> job);

    <I, O> void jobFinished(Job<I, O> job, O outputValue);

    <I, O> void jobInterrupted(Job<I, O> job, Optional<Throwable> e);

}
