package ru.meridor.steve;

public interface Executor<I, O> {

    O execute(Job<I, O> job);

}
