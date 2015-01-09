package org.meridor.steve.processor.methods;

import org.meridor.steve.JobSignature;
import org.meridor.steve.ParameterInstanceProvider;
import org.meridor.steve.SteveException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMethodProcessor implements MethodProcessor {

    private final Map<JobSignature, Method> methods = new HashMap<>();

    protected void put(JobSignature jobSignature, Method method) {
        methods.put(jobSignature, method);
    }

    protected Object invokeMethod(JobSignature jobSignature, ParameterInstanceProvider parameterInstanceProvider) throws Exception {
        Method method = methods.get(jobSignature);
        if (method == null) {
            throw new NoSuchMethodException(String.format("Method for signature %s does not exist", jobSignature));
        }
        if (method.getParameterCount() > 0) {
            ArrayList<Object> parameterInstances = new ArrayList<>();
            for (Parameter parameter : method.getParameters()) {
                Class<?> parameterType = parameter.getType();
                String parameterName = parameter.getName();
                if (!parameterInstanceProvider.containsInstance(parameterType)) {
                    throw new SteveException(String.format(
                            "Can't create a job: no instance for parameter %s in method %s provided",
                            parameterName,
                            method.getDeclaringClass().getCanonicalName() + MethodUtils.METHOD_DELIMITER + method.getName()
                    ));
                }
                Object parameterInstance = parameterInstanceProvider.provide(parameterType);
                parameterInstances.add(parameterInstance);
            }

            return method.invoke(null, parameterInstances.toArray(new Object[parameterInstances.size()]));
        }
        return method.invoke(null);
    }

}
