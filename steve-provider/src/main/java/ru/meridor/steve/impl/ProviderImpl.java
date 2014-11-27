package ru.meridor.steve.impl;

import ru.meridor.steve.*;
import ru.meridor.steve.processor.classes.ClassProcessor;
import ru.meridor.steve.processor.classes.CollectionClassFilter;
import ru.meridor.steve.processor.classes.JobClassFilter;
import ru.meridor.steve.processor.methods.MethodProcessor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

//TODO: be able to create jobs from annotations
public class ProviderImpl implements Provider {

    private final Map<JobSignature, Job> alreadyRequestedJobs = new HashMap<>();

    private final Map<JobSignature, Processor> jobProcessors = new HashMap<>();
    private final List<MethodProcessor> methodProcessors = new ArrayList<>();
    private final List<ClassProcessor> classProcessors = new ArrayList<>();

    private final Set<Class> classesToScan = new HashSet<>();

    private boolean scanned = false;

    private final ParameterInstanceProvider parameterInstanceProvider;

    public ProviderImpl(ClassesProvider classesProvider, ParameterInstanceProvider parameterInstanceProvider, Processor... processors) throws SteveException {
        classesToScan.addAll(classesProvider.provide());
        this.parameterInstanceProvider = parameterInstanceProvider;
        for (Processor processor : processors) {
            addProcessor(processor);
        }
    }

    private void addProcessor(Processor processor) {
        if (processor instanceof MethodProcessor) {
            methodProcessors.add((MethodProcessor) processor);
        } else if (processor instanceof ClassProcessor) {
            classProcessors.add((ClassProcessor) processor);
        }
    }

    private void scan() {
        processCollectionClasses();
        processJobClasses();
    }

    private void processCollectionClasses() {
        CollectionClassFilter collectionClassFilter = new CollectionClassFilter();
        Set<Class> jobCollections = classesToScan.stream()
                .filter(collectionClassFilter::matches)
                .collect(Collectors.toSet());
        for (Class jobCollection : jobCollections) {
            processCollectionClass(jobCollection);
        }
    }

    private void processCollectionClass(Class<?> collectionClass) {
        Method[] classMethods = collectionClass.getMethods();
        for (Method classMethod : classMethods) {
            for (MethodProcessor processor : methodProcessors) {
                if (processor.canProcess(classMethod)) {
                    List<JobSignature> jobSignatures = processor.store(classMethod);
                    for (JobSignature jobSignature : jobSignatures) {
                        jobProcessors.put(jobSignature, processor);
                    }
                    break;
                }
            }
        }
    }

    private void processJobClasses() {
        JobClassFilter jobClassFilter = new JobClassFilter();
        Set<Class> jobs = classesToScan.stream()
                .filter(jobClassFilter::matches)
                .collect(Collectors.toSet());
        for (Class job : jobs) {
            processJobClass(job);
        }
    }

    private void processJobClass(Class<?> jobClass) {
        for (ClassProcessor classProcessor : classProcessors) {
            if (classProcessor.canProcess(jobClass)) {
                List<JobSignature> jobSignatures = classProcessor.store(jobClass);
                for (JobSignature jobSignature : jobSignatures) {
                    jobProcessors.put(jobSignature, classProcessor);
                }
            }
        }
    }

    @Override
    public boolean exists(JobSignature jobSignature) {
        if (!scanned){
            scan();
            scanned = true;
        }
        return jobProcessors.containsKey(jobSignature);
    }

    public Job<Serializable, Serializable> get(JobSignature jobSignature) throws SteveException {

        if (alreadyRequestedJobs.containsKey(jobSignature)) {
            return alreadyRequestedJobs.get(jobSignature);
        }
        if (exists(jobSignature)) {
            try {
                Job job = jobProcessors
                        .get(jobSignature)
                        .createJob(jobSignature, parameterInstanceProvider);
                alreadyRequestedJobs.put(jobSignature, job);
                return job;
            } catch (Exception e) {
                throw new SteveException(e);
            }
        }
        throw new SteveException(String.format(
                "No job found for id = %s, inputType = %s, returnType = %s",
                jobSignature.getId(),
                jobSignature.getInputDataType().getCanonicalName(),
                jobSignature.getReturnDataType().getCanonicalName()
        ));
    }

}
