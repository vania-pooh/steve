package ru.meridor.steve.impl;

import ru.meridor.stecker.PluginException;
import ru.meridor.stecker.PluginLoader;
import ru.meridor.stecker.PluginRegistry;
import ru.meridor.steve.ClassesProvider;
import ru.meridor.steve.SteveException;
import ru.meridor.steve.annotations.Job;
import ru.meridor.steve.annotations.JobCollection;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//TODO: move this to a separate module
public class SteckerClassesProvider implements ClassesProvider {

    @Override
    public List<Class> provide() throws SteveException {

        try {
            final PluginRegistry pluginRegistry = PluginLoader
                    .withPluginDirectory(Paths.get("plugins"))
                    .withExtensionPoints(Job.class, ru.meridor.steve.Job.class, JobCollection.class)
                    .load();
            return new ArrayList<Class>(){
                {
                    addAll(pluginRegistry.getImplementations(ru.meridor.steve.Job.class));
                    addAll(pluginRegistry.getImplementations(Job.class));
                    addAll(pluginRegistry.getImplementations(JobCollection.class));
                }
            };
        } catch (PluginException e) {
            throw new SteveException(e);
        }
    }
}
