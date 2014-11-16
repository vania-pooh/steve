package ru.meridor.steve;

import com.google.common.eventbus.Subscribe;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.meridor.steve.events.JobFinishedEvent;

import static org.hamcrest.Matchers.equalTo;

public class LocalTest extends CamelSpringTestSupport {

    private static final Logger LOG = LoggerFactory.getLogger(LocalTest.class);

    private Launcher launcher;

    @Before
    public void beforeTest(){
        launcher = applicationContext.getBean(Launcher.class);
    }

    @Test
    public void testExample() throws Exception {
        launcher.subscribe(new Object() {

            @Subscribe
            public void logResult(JobFinishedEvent jobFinishedEvent){
                Integer result = (Integer) jobFinishedEvent.getJobResult().getResult();
                LOG.info("Job result is {}", result);
                assertThat(result, equalTo(4));
            }

        });
        //TODO: spy and check that method is called
        launcher.launch("test-job-implementation", "test", Integer.class);
        Thread.sleep(5000);
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("META-INF/spring/launch-local-config-test.xml");
    }
}
