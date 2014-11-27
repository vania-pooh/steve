package ru.meridor.steve.impl;

import ru.meridor.steve.ClassesProvider;
import ru.meridor.steve.SteveException;

import java.util.Arrays;
import java.util.List;

public class EnumeratedClassesProvider implements ClassesProvider {

    private final Class[] classes;

    public EnumeratedClassesProvider(Class... classes) {
        this.classes = classes;
    }


    @Override
    public List<Class> provide() throws SteveException {
        return Arrays.asList(classes);
    }
}
