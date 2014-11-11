package ru.meridor.steve;

import com.google.inject.Guice;
import org.apache.camel.Body;
import org.apache.camel.Header;

public class ExecutorBean {

    public Object execute(@Header("JobSignature") JobSignature jobSignature, @Body Object data) throws SteveException {
        Executor executor = Guice.createInjector().getInstance(Executor.class);
        Class<?> returnDataType = jobSignature.getReturnDataType();
        return returnDataType.cast(
                executor.execute(jobSignature.getId(), data, jobSignature.getInputDataType(), returnDataType)
        );
    }

}
