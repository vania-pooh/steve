package org.meridor.steve;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class EventBusPostProcessor implements BeanPostProcessor {

    private final static Logger LOG = LoggerFactory.getLogger(EventBusPostProcessor.class);

    private final EventBus eventBus;

    public EventBusPostProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Subscribe.class)) {
                    eventBus.register(bean);
                    LOG.debug("Bean {} was subscribed to event notifications", beanName);
                    return bean;
                }
            }
        }

        return bean;
    }
}
