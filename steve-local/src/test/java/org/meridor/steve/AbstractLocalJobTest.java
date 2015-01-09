package org.meridor.steve;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Before;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AbstractLocalJobTest extends CamelSpringTestSupport {

    protected Launcher launcher;

    @Before
    public void beforeTest() {
        launcher = applicationContext.getBean(Launcher.class);
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("META-INF/spring/launch-local-config-test.xml");
    }

}
