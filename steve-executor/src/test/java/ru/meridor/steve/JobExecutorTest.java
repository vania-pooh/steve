package ru.meridor.steve;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.meridor.steve.impl.EnumeratedClassesProvider;
import ru.meridor.steve.impl.EnumeratedParameterInstanceProvider;
import ru.meridor.steve.impl.ExecutorImpl;
import ru.meridor.steve.impl.ProviderImpl;
import ru.meridor.steve.processor.classes.JobClassProcessor;
import ru.meridor.steve.processor.methods.FunctionMethodProcessor;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class JobExecutorTest {

    private final String jobId;

    private final String inputValue;

    private final Integer correctOutputValue;

    @Parameterized.Parameters(name = "{0} should return {2} for {1}")
    public static Iterable<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
                {"ru.meridor.steve.TestCollection#testLength", "testString", 10},
                {"test-length", "42", 2},
                {"test-collection#testLength", "test", 4},
                {"test-collection#test-length", "str", 3},
                {"injected-parameters-test-length", "injected", 8},
                {"ru.meridor.steve.TestJobImplementation", "testMe", 6},
                {"test-job-implementation", "test-it", 7},
                {"injected-parameters-test-job-implementation", "inject-parameter", 16}
        });
    }

    public JobExecutorTest(String jobId, String inputValue, Integer correctOutputValue) {
        this.jobId = jobId;
        this.inputValue = inputValue;
        this.correctOutputValue = correctOutputValue;
    }

    @Test
    public void testExecute() throws SteveException {
        ProviderImpl jobProvider = new ProviderImpl(
                new EnumeratedClassesProvider(
                        TestCollection.class,
                        TestJobImplementation.class,
                        InjectedParametersTestCollection.class,
                        InjectedParametersTestJobImplementation.class
                ),
                new EnumeratedParameterInstanceProvider(
                        new LengthCalculatorImpl()
                ),
                new FunctionMethodProcessor(), new JobClassProcessor()
        );
        ExecutorImpl jobExecutor = new ExecutorImpl(jobProvider);

        JobSignature jobSignature = new JobSignature(jobId, String.class, Integer.class);
        JobRun jobRun = new JobRun(jobSignature, inputValue);

        JobResult actualJobResult = jobExecutor.execute(jobRun);
        JobResult expectedJobResult = new JobResult(jobSignature, correctOutputValue);

        assertThat(actualJobResult, equalTo(expectedJobResult));
    }
}
