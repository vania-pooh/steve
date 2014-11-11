package ru.meridor.steve;

import com.google.inject.Guice;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class MemoryLaunchStrategyTest {

    @Test
    public void testLaunch() throws InterruptedException {
        LaunchStrategy launchStrategy = Guice
                .createInjector(new MemoryConfiguration().getConfiguration())
                .getInstance(LaunchStrategy.class);
        launchStrategy.launch(new JobSignature("test-job", String.class, Integer.class), "test");
        Thread.sleep(5000);
        assertThat(true, equalTo(true));
    }

}
