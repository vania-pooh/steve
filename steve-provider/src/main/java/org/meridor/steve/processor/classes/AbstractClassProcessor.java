package org.meridor.steve.processor.classes;

import org.meridor.steve.JobSignature;
import org.meridor.steve.ParameterInstanceProvider;
import org.meridor.steve.SteveException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractClassProcessor implements ClassProcessor {

    private final Map<JobSignature, Class> classes = new HashMap<>();

    protected void put(JobSignature jobSignature, Class jobClass) {
        classes.put(jobSignature, jobClass);
    }

    protected Object instantiateClass(JobSignature jobSignature, ParameterInstanceProvider parameterInstanceProvider) throws Exception {
        Class jobClass = classes.get(jobSignature);
        if (jobClass == null) {
            throw new NoSuchMethodException(String.format("Method for signature %s does not exist", jobSignature));
        }
        if (jobClass.getConstructors().length > 0) {
            Constructor[] constructors = jobClass.getConstructors();
            Map<Constructor, List<String>> missingParameterInstances = new HashMap<>();
            constructors:
            for (Constructor constructor : constructors) {
                if (constructor.getParameterCount() == 0) {
                    return jobClass.newInstance();
                }
                Parameter[] parameters = constructor.getParameters();
                List<Object> parameterInstances = new ArrayList<>();
                missingParameterInstances.put(constructor, new ArrayList<>());
                for (Parameter parameter : parameters) {
                    String parameterName = parameter.getName();
                    Class<?> parameterType = parameter.getType();
                    if (!parameterInstanceProvider.containsInstance(parameterType)) {
                        missingParameterInstances.get(constructor).add(parameterName);
                        continue constructors;
                    }
                    Object parameterInstance = parameterInstanceProvider.provide(parameterType);
                    parameterInstances.add(parameterInstance);
                }
                return constructor.newInstance(parameterInstances.toArray(new Object[parameterInstances.size()]));
            }
            throwConstructorsExhaustedException(jobClass, missingParameterInstances);
        }
        throw new SteveException(String.format(
                "Can't create a job : job class %s has no public constructors",
                jobClass.getCanonicalName()
        ));
    }

    private void throwConstructorsExhaustedException(Class jobClass, Map<Constructor, List<String>> missingParameterInstances) throws SteveException {
        StringBuilder missingParameterInstancesDescription = new StringBuilder();
        for (Constructor constructor : missingParameterInstances.keySet()) {
            String missingParameterNames = missingParameterInstances.get(constructor)
                    .stream().collect(Collectors.joining(", "));
            missingParameterInstancesDescription
                    .append(constructor)
                    .append(": ")
                    .append(missingParameterNames)
                    .append("\n");
        }
        throw new SteveException(String.format(
                "Can't create a job: job class %s has %d public constructors but the following parameter instances are missing for each of them: %s",
                jobClass.getCanonicalName(),
                missingParameterInstances.size(),
                missingParameterInstancesDescription.toString()
        ));
    }

}
