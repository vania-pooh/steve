package ru.meridor.steve;

// We use the first present launch strategy on classpath
public interface LaunchStrategy<T, R> {

    void launch(JobSignature<T, R> jobSignature, EventListener<T, R> eventListener);

}
