package org.meridor.steve.impl;

import org.meridor.stecker.PluginException;
import org.meridor.stecker.PluginLoader;
import org.meridor.stecker.PluginRegistry;
import org.meridor.steve.ClassesProvider;
import org.meridor.steve.SteveException;
import org.meridor.steve.annotations.Job;
import org.meridor.steve.annotations.JobCollection;

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
                    .withExtensionPoints(Job.class, org.meridor.steve.Job.class, JobCollection.class)
                    .load();
            return new ArrayList<Class>() {
                {
                    addAll(pluginRegistry.getImplementations(org.meridor.steve.Job.class));
                    addAll(pluginRegistry.getImplementations(Job.class));
                    addAll(pluginRegistry.getImplementations(JobCollection.class));
                }
            };
        } catch (PluginException e) {
            throw new SteveException(e);
        }
    }
}
