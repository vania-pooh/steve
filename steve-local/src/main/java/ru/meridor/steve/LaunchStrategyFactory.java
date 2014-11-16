package ru.meridor.steve;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;

public class LaunchStrategyFactory implements CamelContextAware {

    private static CamelContext camelContext;

    public static LaunchStrategy createStrategy() {
        return camelContext.getRegistry().lookupByNameAndType("launchStrategy", LaunchStrategy.class);
    }

    @Override
    public void setCamelContext(CamelContext camelContext) {
        LaunchStrategyFactory.camelContext = camelContext;
    }

    @Override
    public CamelContext getCamelContext() {
        return camelContext;
    }
}
