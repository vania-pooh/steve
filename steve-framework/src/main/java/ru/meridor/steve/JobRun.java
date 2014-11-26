package ru.meridor.steve;

import java.io.Serializable;
import java.util.Map;

public class JobRun implements Serializable {

    private final JobSignature signature;

    private final Serializable data;

    public JobRun(JobSignature signature, Serializable data) {

        this.signature = signature;
        this.data = data;
    }

    public JobSignature getSignature() {
        return signature;
    }

    public Serializable getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobRun)) return false;

        JobRun jobRun = (JobRun) o;

        if (!data.equals(jobRun.data)) return false;
        if (!signature.equals(jobRun.signature)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = signature.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }
}
