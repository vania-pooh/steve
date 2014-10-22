package ru.meridor.steve.job;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class AnnotationProcessorTest {

    private AnnotationProcessor annotationProcessor;
    private final Class<JobClass> jobClass = JobClass.class;

    private Method getMethod(String name) throws NoSuchMethodException {
        return jobClass.getMethod(name);
    }

    @Before
    public void initAnnotationProcessor() {
        annotationProcessor = new AnnotationProcessor(JobClass.class);
    }

    @Test
    public void testGetJobMethods() throws NoSuchMethodException {
        assertThat(annotationProcessor.getJobMethods(), hasSize(2));
        assertThat(annotationProcessor.getJobMethods(), containsInAnyOrder(
                getMethod("syncJob"),
                getMethod("asyncJob")
        ));
    }

}
