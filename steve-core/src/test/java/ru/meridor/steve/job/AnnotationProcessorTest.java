package ru.meridor.steve.job;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
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
        assertTrue(annotationProcessor.getOnStartMethod().isPresent());
        assertThat(annotationProcessor.getOnStartMethod().get(), equalTo(getMethod("onStartMethod")));
    }

    @Test
    public void testGetInputMethod() throws NoSuchMethodException {
        assertTrue(annotationProcessor.getInputMethod().isPresent());
        assertThat(annotationProcessor.getInputMethod().get(), equalTo(getMethod("inputMethod", Integer.class)));
    }

    @Test
    public void testGetResultMethod() throws NoSuchMethodException {
        assertTrue(annotationProcessor.getOutputMethod().isPresent());
        assertThat(annotationProcessor.getOutputMethod().get(), equalTo(getMethod("outputMethod")));
    }

    @Test
    public void testGetOnFinishMethod() throws NoSuchMethodException {
        assertTrue(annotationProcessor.getOnFinishMethod().isPresent());
        assertThat(annotationProcessor.getOnFinishMethod().get(), equalTo(getMethod("onFinishMethod")));
    }

    @Test
    public void testGetOnInterruptMethods() throws NoSuchMethodException {
        assertTrue(annotationProcessor.getOnInterruptMethod().isPresent());
        assertThat(annotationProcessor.getOnInterruptMethod().get(), equalTo(getMethod("onInterruptMethod")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullClassPassed() {
        new AnnotationProcessor(null);
    }

    @Test
    public void testGetInputType() {
        assertThat(annotationProcessor.getInputType(), equalTo(Integer.class));
    }

    @Test
    public void testGetOutputType() {
        assertThat(annotationProcessor.getOutputType(), equalTo(String.class));
    }

    @Test
    public void testIsAsyncJob() {
        assertThat(annotationProcessor.isAsyncJob(), equalTo(true));
    }

}
