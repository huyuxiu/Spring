package com.spring;

public interface BeanPostProcesser {

    default Object postProcessBeforeInitialization(Object bean, String beanName) throws Throwable {
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
        return bean;
    }
}
