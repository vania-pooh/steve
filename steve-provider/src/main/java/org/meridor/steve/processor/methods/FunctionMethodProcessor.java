package org.meridor.steve.processor.methods;

import org.meridor.steve.Job;
import org.meridor.steve.JobSignature;
import org.meridor.steve.ParameterInstanceProvider;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

public class FunctionMethodProcessor extends AbstractMethodProcessor {

    @Override
    public boolean canProcess(Method method) {
        return MethodUtils.isMethodStatic(method) && MethodUtils.methodReturnTypeEquals(method, Function.class);
    }

    @Override
    public List<JobSignature> store(Method method) {
        List<JobSignature> jobSignatures = MethodUtils.twoParameterGenericToJobSignatures(method);
        for (JobSignature jobSignature : jobSignatures) {
            put(jobSignature, method);
        }
        return jobSignatures;
    }

    @Override
    public <T extends Serializable, R extends Serializable> Job<T, R> createJob(JobSignature jobSignature, ParameterInstanceProvider parameterInstanceProvider) throws Exception {
        @SuppressWarnings("unchecked")
        Function<T, R> function = (Function<T, R>) invokeMethod(jobSignature, parameterInstanceProvider);
        return function::apply;
    }
}
