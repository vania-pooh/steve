package ru.meridor.steve.processor.classes;

import ru.meridor.steve.Job;
import ru.meridor.steve.JobSignature;
import ru.meridor.steve.ParameterInstanceProvider;

import java.io.Serializable;
import java.util.List;

import static ru.meridor.steve.processor.classes.ClassUtils.twoParameterGenericClassToJobSignatures;

public class JobClassProcessor extends AbstractClassProcessor {

    @Override
    public boolean canProcess(Class someClass) {
        return Job.class.isAssignableFrom(someClass);
    }

    @Override
    public List<JobSignature> store(Class someClass) {
        List<JobSignature> jobSignatures = twoParameterGenericClassToJobSignatures(someClass);
        for (JobSignature jobSignature : jobSignatures) {
            put(jobSignature, someClass);
        }
        return jobSignatures;
    }

    @Override
    public <T extends Serializable, R extends Serializable> Job<T, R> createJob(JobSignature jobSignature, ParameterInstanceProvider parameterInstanceProvider) throws Exception {
        @SuppressWarnings("unchecked")
        Job<T, R> job = (Job<T, R>) instantiateClass(jobSignature, parameterInstanceProvider);
        return job;
    }
}
