package ru.meridor.steve;

import ru.meridor.steve.processing.LaunchStrategy;

import java.util.List;

public interface Launcher<T, R> {

    void launch(String id, EventListener<T, R> eventListener);

    List<LaunchStrategy> getLaunchStrategies();

}
