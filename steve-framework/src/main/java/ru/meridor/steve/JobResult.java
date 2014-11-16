package ru.meridor.steve;

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
}
