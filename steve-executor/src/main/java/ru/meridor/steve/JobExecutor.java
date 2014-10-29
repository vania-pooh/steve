package ru.meridor.steve;

import ru.meridor.stecker.PluginException;
import ru.meridor.stecker.PluginLoader;
import ru.meridor.stecker.PluginRegistry;

import ru.meridor.steve.annotations.Lib;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JobExecutor {

    public JobExecutor() throws SteveException {
        loadPlugins();
    }

    private void loadPlugins() throws SteveException {
        Path pluginDirectory = Paths.get("plugins");
        try {
            PluginRegistry pluginRegistry = PluginLoader
                    .withPluginDirectory(pluginDirectory)
                    .withExtensionPoints(Job.class, ru.meridor.steve.annotations.Job.class, Lib.class)
                    .load();
            //TODO: to be continued!
        } catch (PluginException e) {
            throw new SteveException(e);
        }
    }


}
