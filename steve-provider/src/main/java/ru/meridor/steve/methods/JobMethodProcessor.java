package ru.meridor.steve.methods;

import ru.meridor.steve.Job;
import ru.meridor.steve.JobSignature;

import java.lang.reflect.Method;
import java.util.List;

public class JobMethodProcessor extends AbstractMethodProcessor {

    @Override
    public boolean canProcess(Method method) {
        return MethodUtils.isMethodStatic(method) && MethodUtils.methodReturnTypeEquals(method, Job.class);
    }

    @Override
    public List<JobSignature> store(Method method) {
        return MethodUtils.twoParameterGenericToJobSignatures(method);
    }

    @Override
    public <T, R> Job<T, R> createJob(JobSignature jobSignature) throws Exception {
        @SuppressWarnings("unchecked")
        Job<T, R> job = (Job<T, R>) invokeMethod(jobSignature);
        return job;
    }
}