package org.meridor.steve;

import java.io.Serializable;

public class JobResult implements Serializable {

    private final JobSignature signature;

    private final Serializable result;

    public JobResult(JobSignature signature, Serializable result) {
        this.result = result;
        this.signature = signature;
    }

    public JobSignature getSignature() {
        return signature;
    }

    public Serializable getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobResult)) return false;

        JobResult jobResult = (JobResult) o;

        if (!result.equals(jobResult.result)) return false;
        if (!signature.equals(jobResult.signature)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = signature.hashCode();
        result1 = 31 * result1 + result.hashCode();
        return result1;
    }
}
