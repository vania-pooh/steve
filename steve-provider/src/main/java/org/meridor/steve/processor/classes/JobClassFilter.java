package org.meridor.steve.processor.classes;

import org.meridor.steve.ClassFilter;
import org.meridor.steve.Job;

public class JobClassFilter implements ClassFilter {

    @Override
    public boolean matches(Class someClass) {
        return someClass != null && Job.class.isAssignableFrom(someClass);
    }

}
