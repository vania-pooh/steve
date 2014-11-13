package ru.meridor.steve;

//We use the first present launch strategy on classpath
//TODO: use launcher directly!!!
@Deprecated
public interface LaunchStrategy {

    void launch(JobSignature jobSignature, Object data);

}
