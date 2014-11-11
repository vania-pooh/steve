package ru.meridor.steve;

import com.google.inject.Guice;

public final class Launchers {

    public static Launcher newLauncher(LauncherConfiguration configurator) throws SteveException {
        try {
            return Guice
                    .createInjector(configurator.getConfiguration())
                    .getInstance(Launcher.class);
        } catch (Exception e) {
            throw new SteveException(e);
        }
    }

    public static Launcher newLauncher(Class<? extends LauncherConfiguration> configuratorClass) throws SteveException {
        try {
            LauncherConfiguration configurator = configuratorClass.newInstance();
            return Guice
                    .createInjector(configurator.getConfiguration())
                    .getInstance(Launcher.class);
        } catch (Exception e) {
            throw new SteveException(e);
        }
    }

    private Launchers(){}

}
