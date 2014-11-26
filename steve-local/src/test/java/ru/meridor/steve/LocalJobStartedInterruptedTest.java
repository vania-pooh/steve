package ru.meridor.steve;

import com.google.common.eventbus.Subscribe;
import org.junit.Before;
import org.junit.Test;
import ru.meridor.steve.events.JobInterruptedEvent;
import ru.meridor.steve.events.JobStartedEvent;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class LocalJobStartedInterruptedTest extends AbstractLocalJobTest {

    private JobRun jobRun;
    private Exception exception;

    private static final String JOB_NAME = "incorrect-job-name";
    private static final String JOB_DATA = "test";

    @Before
    public void resetState() {
        this.jobRun = null;
        this.exception = null;
    }

    @Test
    public void testJobStarted() throws Exception {
        launcher.subscribe(new Object() {

            @Subscribe
            public void saveJobRun(JobStartedEvent jobStartedEvent) {
                jobRun = jobStartedEvent.getJobRun();
            }

        });
        assertNull(jobRun);
        launcher.launch(JOB_NAME, JOB_DATA, Integer.class);
        Thread.sleep(1000);
        assertThat(jobRun, equalTo(correctJobRun()));
    }

    @Test
    public void testJobInterrupted() throws Exception {
        launcher.subscribe(new Object() {

            @Subscribe
            public void saveJobRunAndException(JobInterruptedEvent jobInterruptedEvent) {
                jobRun = jobInterruptedEvent.getJobRun();
                exception = jobInterruptedEvent.getException();
            }

        });
        assertNull(jobRun);
        assertNull(exception);
        launcher.launch(JOB_NAME, JOB_DATA, Integer.class);
        Thread.sleep(1000);
        assertThat(jobRun, equalTo(correctJobRun()));
        assertThat(exception, instanceOf(SteveException.class));
    }

    private JobRun correctJobRun() {
        return new JobRun(
                new JobSignature(JOB_NAME, String.class, Integer.class),
                JOB_DATA
        );
    }

}
