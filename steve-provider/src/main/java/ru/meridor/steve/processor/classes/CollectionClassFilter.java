package ru.meridor.steve.processor.classes;

import ru.meridor.steve.ClassFilter;
import ru.meridor.steve.annotations.JobCollection;

public class CollectionClassFilter implements ClassFilter {

    @Override
    public boolean matches(Class someClass) {
        return someClass != null && someClass.isAnnotationPresent(JobCollection.class);
    }

}
