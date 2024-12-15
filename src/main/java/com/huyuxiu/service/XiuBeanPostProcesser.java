package com.huyuxiu.service;

import com.spring.BeanPostProcesser;
import com.spring.Component;

@Component
public class XiuBeanPostProcesser implements BeanPostProcesser {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Throwable {
        if(beanName.equals("userService")){
            ((UserService)bean).setName("huyuxiu");
        }
        System.out.printf("初始化前");
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
        System.out.println("初始化后");
        return bean;
    }
}
