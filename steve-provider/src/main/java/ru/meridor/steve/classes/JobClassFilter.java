package ru.meridor.steve.classes;

import ru.meridor.steve.ClassFilter;
import ru.meridor.steve.Job;

public class JobClassFilter implements ClassFilter {

    @Override
    public boolean matches(Class someClass) {
        return someClass != null && Job.class.isAssignableFrom(someClass);
    }

}
