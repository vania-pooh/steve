package ru.meridor.steve;

import java.io.Serializable;

public interface Provider {

    boolean exists(JobSignature jobSignature);

    Job<Serializable, Serializable> get(JobSignature jobSignature) throws SteveException;

}
