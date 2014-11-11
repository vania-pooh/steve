package ru.meridor.steve;

import com.google.inject.Inject;
import org.apache.camel.CamelContext;

public class MemoryLaunchStrategy implements LaunchStrategy {

    private CamelContext camelContext;

    @Inject
    public MemoryLaunchStrategy(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public void launch(JobSignature jobSignature, Object data) {
        camelContext.createProducerTemplate().sendBodyAndHeader("seda:jobs", data, "JobSignature", jobSignature);
    }

}
