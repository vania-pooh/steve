package ru.meridor.steve;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LocalTest extends CamelSpringTestSupport {

    private static final Logger LOG = LoggerFactory.getLogger(LocalTest.class);

    private Launcher launcher;

    @Before
    public void beforeTest(){
        launcher = applicationContext.getBean(Launcher.class);
    }

    @Test
    public void testExample() throws Exception {
        LOG.info("Launching job!");
        launcher.launch("test-job-implementation", "some-string", Integer.class);
        Thread.sleep(5000);
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("META-INF/spring/launch-local-config-test.xml");
    }
}
