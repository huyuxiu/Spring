package com.huyuxiu.service;

import com.spring.BeanPostProcesser;
import com.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class XiuBeanPostProcesser implements BeanPostProcesser {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Throwable {
        if(beanName.equals("userService")){
            ((UserServiceImpl)bean).setName("huyuxiu");
        }
        System.out.printf("初始化前");
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
        System.out.println("初始化后");
        //实现aop
        if(beanName.equals("userService")){
            Object proxyInstance = Proxy.newProxyInstance(XiuBeanPostProcesser.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    System.out.println("代理逻辑");

                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }

        return bean;
    }
}
