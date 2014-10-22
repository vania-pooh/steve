package ru.meridor.steve.processing;

public interface Scheduler {

    <I, O> void schedule(String id);

}
