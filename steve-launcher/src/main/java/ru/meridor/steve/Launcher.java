package ru.meridor.steve;

import ru.meridor.steve.processing.LaunchStrategy;

import java.util.List;

public interface Launcher {

    JobResult<?> launchSync(String jobId, Object data);

    void launchAsync(String id, EventListener<?> eventListener);

    List<LaunchStrategy> getLaunchStrategies();

}
