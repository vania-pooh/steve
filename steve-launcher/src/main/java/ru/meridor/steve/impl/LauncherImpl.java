package ru.meridor.steve.impl;

import ru.meridor.steve.JobSignature;
import ru.meridor.steve.LaunchStrategy;
import ru.meridor.steve.Launcher;
import ru.meridor.steve.SteveException;
import ru.meridor.steve.model.JobEntry;

import javax.xml.bind.JAXB;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LauncherImpl implements Launcher {

    private static final String JOBS_FILENAME = "jobs.xml";

    private final Set<JobSignature> availableJobs = new HashSet<>();

    private boolean loadedJobs = false;

    private LaunchStrategy launchStrategy;

    public LauncherImpl(LaunchStrategy launchStrategy) {
        this.launchStrategy = launchStrategy;
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
                    availableJobs.add(new JobSignature(jobId, inputClass, returnClass));
                } catch (ClassNotFoundException e) {
                    break;
                }
            }
            return availableJobs;
        } catch (Exception e) {
            throw new SteveException(e);
        }
    }

    @Override
    public <T, R> void launch(String jobId, T inputData, Class<R> returnDataType) throws SteveException {
        if (!loadedJobs) {
            availableJobs.addAll(getAvailableJobs());
            loadedJobs = true;
        }
        JobSignature jobSignature = new JobSignature(jobId, inputData.getClass(), returnDataType);
        if (availableJobs.contains(jobSignature)){
            launchStrategy.launch(
                    jobSignature,
                    inputData
            );
        }
        throw new SteveException(String.format("Job not found: %s - %s - %s", jobId, inputData.toString(), returnDataType));
    }

    @Override
    public void subscribe(Object handler) {

    }
}
