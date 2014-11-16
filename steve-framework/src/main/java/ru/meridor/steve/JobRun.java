package ru.meridor.steve;

import java.io.Serializable;
import java.util.Map;

public class JobRun implements Serializable {

    private final JobSignature signature;

    private final Serializable data;

    private final Map<String, Serializable> metadata;


    public JobRun(JobSignature signature, Serializable data, Map<String, Serializable> metadata) {

        this.signature = signature;
        this.data = data;
        this.metadata = metadata;
    }

    public JobSignature getSignature() {
        return signature;
    }

    public Serializable getData() {
        return data;
    }

    public Map<String, Serializable> getMetadata() {
        return metadata;
    }
}
