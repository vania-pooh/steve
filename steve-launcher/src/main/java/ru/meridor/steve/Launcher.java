package ru.meridor.steve;

public interface Launcher<T, R> {

    boolean jobExists(JobSignature jobSignature);

    void launch(JobSignature jobSignature, EventListener<T, R> eventListener) throws SteveException;

}
