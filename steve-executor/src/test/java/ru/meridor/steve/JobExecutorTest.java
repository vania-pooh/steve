package ru.meridor.steve;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.meridor.steve.processor.classes.JobClassProcessor;
import ru.meridor.steve.impl.ExecutorImpl;
import ru.meridor.steve.impl.ProviderImpl;
import ru.meridor.steve.processor.methods.FunctionMethodProcessor;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class JobExecutorTest {

    private final String jobId;

    private final String inputValue;

    private final Integer correctOutputValue;

    @Parameterized.Parameters(name = "{0} should return {3} for {2}")
    public static Iterable<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
                {"ru.meridor.steve.TestCollection#testLength", "testString", 10},
                {"test-length", "42", 2},
                {"test-collection#testLength", "test", 4},
                {"test-collection#test-length", "str", 3},
                {"ru.meridor.steve.TestJobImplementation", "testMe", 6},
                {"test-job-implementation", "test-it", 7}
        });
    }

    public JobExecutorTest(String jobId, String inputValue, Integer correctOutputValue) {
        this.jobId = jobId;
        this.inputValue = inputValue;
        this.correctOutputValue = correctOutputValue;
    }

    @Test
    public void testEndToEnd() throws SteveException {
        ProviderImpl jobProvider = new ProviderImpl(
                new EnumeratedClassesProvider(TestCollection.class, TestJobImplementation.class),
                new FunctionMethodProcessor(), new JobClassProcessor()
        );
        ExecutorImpl jobExecutor = new ExecutorImpl(jobProvider);

        JobRun jobRun = new JobRun(new JobSignature(jobId, String.class, Integer.class), inputValue);

        Object outputValue = jobExecutor.execute(jobRun);
        assertThat(outputValue, equalTo(correctOutputValue));
    }
}
