package ru.meridor.steve.processing;

import ru.meridor.steve.EventListener;

public interface LaunchStrategy {

    String getId();

    boolean asyncJobSupported();

    void processAsync(String id, EventListener<?, ?> eventListener);

}
