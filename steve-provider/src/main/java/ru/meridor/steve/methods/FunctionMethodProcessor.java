package ru.meridor.steve.methods;

import ru.meridor.steve.Job;
import ru.meridor.steve.JobSignature;

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
    public <T, R> Job<T, R> createJob(JobSignature jobSignature) throws Exception {
        @SuppressWarnings("unchecked")
        Function<T,R> function = (Function<T, R>) invokeMethod(jobSignature);
        return function::apply;
    }
}
