package ru.meridor.steve;

import ru.meridor.steve.classes.ClassProcessor;
import ru.meridor.steve.classes.CollectionClassFilter;
import ru.meridor.steve.classes.JobClassFilter;
import ru.meridor.steve.methods.MethodProcessor;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class JobProvider implements Provider {

    private final Map<JobSignature, Job<?, ?>> alreadyRequestedJobs = new HashMap<>();

    private final Map<JobSignature, Processor> jobProcessors = new HashMap<>();
    private final List<MethodProcessor> methodProcessors = new ArrayList<>();
    private final List<ClassProcessor> classProcessors = new ArrayList<>();

    private final Set<Class> classesToScan = new HashSet<>();

    private boolean scanned = false;

    public JobProvider(List<Class> classes, List<Processor> processors) {
        classesToScan.addAll(classes);
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

    public <T, R> Job<T, R> get(String jobId, Class<T> inputDataType, Class<R> returnDataType) throws SteveException {
        JobSignature jobSignature = new JobSignature(jobId, inputDataType, returnDataType);

        if (!scanned){
            scan();
            scanned = true;
        }

        if (alreadyRequestedJobs.containsKey(jobSignature)) {
            @SuppressWarnings("unchecked")
            Job<T, R> job = (Job<T, R>) alreadyRequestedJobs.get(jobSignature);
            return job;
        }
        if (jobProcessors.containsKey(jobSignature)) {
            try {
                @SuppressWarnings("unchecked")
                Job<T, R> job = jobProcessors
                        .get(jobSignature)
                        .createJob(jobSignature);
                alreadyRequestedJobs.put(jobSignature, job);
                return job;
            } catch (Exception e) {
                throw new SteveException(e);
            }
        }
        throw new SteveException(String.format("No job found for id = %s, inputType = %s, returnType = %s", jobId, inputDataType.getCanonicalName(), returnDataType.getCanonicalName()));
    }

}
