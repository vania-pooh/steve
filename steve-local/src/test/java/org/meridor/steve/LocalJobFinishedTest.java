package org.meridor.steve;

import com.google.common.eventbus.Subscribe;
import org.junit.Test;
import org.meridor.steve.events.JobFinishedEvent;

import static org.hamcrest.Matchers.equalTo;

public class LocalJobFinishedTest extends AbstractLocalJobTest {

    private Integer result;

    @Test
    public void testJobFinished() throws Exception {
        launcher.subscribe(new Object() {

            @Subscribe
            public void saveResult(JobFinishedEvent jobFinishedEvent) {
                result = (Integer) jobFinishedEvent.getJobResult().getResult();
            }

        });
        assertNull(result);
        launcher.launch("test-job-implementation", "test", Integer.class);
        Thread.sleep(5000);
        assertThat(result, equalTo(4));
    }

}
