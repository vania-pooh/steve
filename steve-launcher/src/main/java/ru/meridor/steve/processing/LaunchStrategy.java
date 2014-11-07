package ru.meridor.steve.processing;

import ru.meridor.steve.EventListener;
import ru.meridor.steve.JobSignature;

public interface LaunchStrategy<T, R> {

    void launch(JobSignature jobSignature, EventListener<T, R> eventListener);

}
