package ru.meridor.steve;

import com.google.common.eventbus.EventBus;
import ru.meridor.steve.events.JobFinishedEvent;

public class LocalResultProcessor {

    private final EventBus eventBus;

    public LocalResultProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void process(JobResult jobResult) {
        eventBus.post(new JobFinishedEvent(jobResult));
    }

}
