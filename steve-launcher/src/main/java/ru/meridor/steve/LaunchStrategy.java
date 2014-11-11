package ru.meridor.steve;

//TODO: we use the first present launch strategy on classpath
public interface LaunchStrategy {

    void launch(JobSignature jobSignature, Object data);

}
