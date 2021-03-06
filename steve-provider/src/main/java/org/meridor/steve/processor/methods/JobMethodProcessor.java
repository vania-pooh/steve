package org.meridor.steve.processor.methods;

import org.meridor.steve.Job;
import org.meridor.steve.JobSignature;
import org.meridor.steve.ParameterInstanceProvider;

import java.io.Serializable;
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
    public <T extends Serializable, R extends Serializable> Job<T, R> createJob(JobSignature jobSignature, ParameterInstanceProvider parameterInstanceProvider) throws Exception {
        @SuppressWarnings("unchecked")
        Job<T, R> job = (Job<T, R>) invokeMethod(jobSignature, parameterInstanceProvider);
        return job;
    }
}
