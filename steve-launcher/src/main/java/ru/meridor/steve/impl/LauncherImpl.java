package ru.meridor.steve.impl;

import com.google.inject.Inject;
import ru.meridor.steve.*;
import ru.meridor.steve.EventListener;
import ru.meridor.steve.model.JobEntry;
import ru.meridor.steve.model.Jobs;

import javax.xml.bind.JAXB;
import java.net.URL;
import java.util.*;

public class LauncherImpl<T, R> implements Launcher<T, R> {

    private static final String JOBS_FILENAME = "jobs.xml";

    private final Set<JobSignature> availableJobs = new HashSet<>();

    private boolean loadedJobs = false;

    private LaunchStrategy<?, ?> launchStrategy;

    @Inject
    public LauncherImpl(LaunchStrategy launchStrategy) {
        this.launchStrategy = launchStrategy;
    }

    @Override
    public boolean jobExists(String jobId, Class<T> inputDataType, Class<R> returnDataType) throws SteveException {
        if (!loadedJobs) {
            availableJobs.addAll(getAvailableJobs());
            loadedJobs = true;
        }
        return availableJobs.contains(new JobSignature<>(jobId, inputDataType, returnDataType));
    }


    private List<JobSignature> getAvailableJobs() throws SteveException {
        List<JobSignature> availableJobs = new ArrayList<>();
        URL jobsXmlURL = getClass().getClassLoader().getResource(JOBS_FILENAME);
        if (jobsXmlURL == null) {
            throw new SteveException(JOBS_FILENAME + " file does not exist");
        }
        try {
            Jobs jobs = JAXB.unmarshal(jobsXmlURL, Jobs.class);
            for (JobEntry jobEntry : jobs.getJobEntries()) {
                try {
                    String jobId = jobEntry.getId();
                    String inputType = jobEntry.getInputType();
                    Class<?> inputClass = Class.forName(inputType);
                    String returnType = jobEntry.getReturnType();
                    Class<?> returnClass = Class.forName(returnType);
                    availableJobs.add(new JobSignature<>(jobId, inputClass, returnClass));
                } catch (ClassNotFoundException e) {
                    break;
                }
            }
            return availableJobs;
        } catch (Exception e) {
            throw new SteveException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void launch(String jobId, T inputData, Class<R> returnDataType, EventListener<T, R> eventListener) throws SteveException {
        JobSignature<T, R> jobSignature = new JobSignature<>(jobId, (Class<T>) inputData.getClass(), returnDataType);
        if (availableJobs.contains(jobSignature)){
            launchStrategy.launch(
                    (JobSignature) jobSignature,
                    (EventListener) eventListener
            );
        }
        throw new SteveException(String.format("Job not found: %s - %s - %s", jobId, inputData.toString(), returnDataType));
    }
}
