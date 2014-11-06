package ru.meridor.steve.impl;

import ru.meridor.steve.EventListener;
import ru.meridor.steve.JobSignature;
import ru.meridor.steve.Launcher;

public class LauncherImpl<T, R> implements Launcher<T, R> {

    @Override
    public void launch(JobSignature jobSignature, EventListener<T, R> eventListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean jobExists(JobSignature jobSignature) {
        throw new UnsupportedOperationException();
    }

}
