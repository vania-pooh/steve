package ru.meridor.steve;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextParameterInstanceProvider implements ParameterInstanceProvider, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public boolean containsInstance(Class<?> parameterClass) {
        return applicationContext.getBeanNamesForType(parameterClass).length > 0;
    }

    @Override
    public <T> T provide(Class<T> parameterClass) {
        return applicationContext.getBean(parameterClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
