package ru.meridor.steve;

import org.apache.camel.Body;
import org.apache.camel.Header;

public class ExecutorBean {

    public void execute(@Header("JobSignature") JobSignature jobSignature, @Body Object data) {

    }

}
