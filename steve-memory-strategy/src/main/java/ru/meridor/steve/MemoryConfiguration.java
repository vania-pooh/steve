package ru.meridor.steve;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.apache.camel.guice.CamelModuleWithRouteTypes;


public class MemoryConfiguration implements LauncherConfiguration {

    @Override
    public Module[] getConfiguration() {
        return new Module[]{
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(LaunchStrategy.class).to(MemoryLaunchStrategy.class);
                    }
                },
                new CamelModuleWithRouteTypes(
                        //TODO: insert route types here
                )
        };
    }

}

