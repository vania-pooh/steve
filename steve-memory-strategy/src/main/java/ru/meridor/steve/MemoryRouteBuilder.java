package ru.meridor.steve;

import org.apache.camel.builder.RouteBuilder;

public class MemoryRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("seda:jobs")
                .bean(ExecutorBean.class)
                .to("seda:results");
    }
}
