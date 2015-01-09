package org.meridor.steve.processor.classes;

import org.meridor.steve.ClassFilter;
import org.meridor.steve.annotations.JobCollection;

public class CollectionClassFilter implements ClassFilter {

    @Override
    public boolean matches(Class someClass) {
        return someClass != null && someClass.isAnnotationPresent(JobCollection.class);
    }

}
