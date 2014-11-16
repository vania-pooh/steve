package ru.meridor.steve.processor.classes;

import ru.meridor.steve.Job;
import ru.meridor.steve.JobSignature;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractClassProcessor implements ClassProcessor {

    private final Map<JobSignature, Class> classes = new HashMap<>();

    protected void put(JobSignature jobSignature, Class jobClass) {
        classes.put(jobSignature, jobClass);
    }

    protected Object instantiateClass(JobSignature jobSignature) throws Exception {
        Class jobClass = classes.get(jobSignature);
        if (jobClass == null) {
            throw new NoSuchMethodException(String.format("Method for signature %s does not exist", jobSignature));
        }
        return (Job<?, ?>) jobClass.newInstance();
    }

}
