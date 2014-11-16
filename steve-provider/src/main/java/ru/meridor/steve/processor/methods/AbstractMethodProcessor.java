package ru.meridor.steve.processor.methods;

import ru.meridor.steve.JobSignature;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMethodProcessor implements MethodProcessor {

    private final Map<JobSignature, Method> methods = new HashMap<>();

    protected void put(JobSignature jobSignature, Method method) {
        methods.put(jobSignature, method);
    }

    protected Object invokeMethod(JobSignature jobSignature) throws Exception {
        Method method = methods.get(jobSignature);
        if (method == null) {
            throw new NoSuchMethodException(String.format("Method for signature %s does not exist", jobSignature));
        }
        return method.invoke(null);
    }

}
