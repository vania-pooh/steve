package ru.meridor.steve.job;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AnnotationProcessorTest {

    private AnnotationProcessor annotationProcessor;
    private final Class<JobClass> jobClass = JobClass.class;

    private Method getMethod(String name, Class... parameterTypes) throws NoSuchMethodException {
        return jobClass.getMethod(name, parameterTypes);
    }

    @Before
    public void initAnnotationProcessor() {
        annotationProcessor = new AnnotationProcessor(JobClass.class);
    }

    @Test
    public void testGetOnStartMethod() throws NoSuchMethodException {
        assertThat(annotationProcessor.getJobMethods(), hasSize(2));
    }

}
