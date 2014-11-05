package ru.meridor.steve;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.meridor.steve.classes.JobClassProcessor;
import ru.meridor.steve.methods.FunctionMethodProcessor;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class JobExecutorTest {

    private final Class testClass;

    private final String jobId;

    private final String inputValue;

    private final Integer correctOutputValue;

    @Parameterized.Parameters(name = "{1} should return {3} for {2}")
    public static Iterable<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
                {TestCollection.class, "ru.meridor.steve.TestCollection#testLength", "testString", 10},
                {TestCollection.class, "test-length", "42", 2},
                {TestCollection.class, "test-collection#testLength", "test", 4},
                {TestCollection.class, "test-collection#test-length", "str", 3},
                {TestJobImplementation.class, "ru.meridor.steve.TestJobImplementation", "testMe", 6},
                {TestJobImplementation.class, "test-job-implementation", "test-it", 7}
        });
    }

    public JobExecutorTest(Class testClass, String jobId, String inputValue, Integer correctOutputValue) {
        this.testClass = testClass;
        this.jobId = jobId;
        this.inputValue = inputValue;
        this.correctOutputValue = correctOutputValue;
    }

    @Test
    public void testEndToEnd() throws SteveException {
        JobProvider jobProvider = new JobProvider(
                Arrays.asList(testClass),
                Arrays.asList(new FunctionMethodProcessor(), new JobClassProcessor())
        );
        JobExecutor jobExecutor = new JobExecutor(jobProvider);

        Integer outputValue = jobExecutor.execute(jobId, inputValue, String.class, Integer.class);
        assertThat(outputValue, equalTo(correctOutputValue));
    }
}
