package ru.meridor.steve.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import ru.meridor.steve.LaunchStrategy;
import ru.meridor.steve.LauncherConfiguration;

public class Mock implements LauncherConfiguration {

    @Override
    public Module[] getConfiguration() {
        return new Module[]{
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(LaunchStrategy.class).to(MockLaunchStrategy.class);
                    }
                }
        };
    }
}
