package com.huyuxiu.service;

import com.spring.Autowired;
import com.spring.BeanNameAware;
import com.spring.Component;
import com.spring.Scope;

import java.sql.SQLOutput;

@Component("userService")
@Scope("prototype")
public class UserService implements BeanNameAware {

    private String beanName;
    @Autowired
    private OrderService orderService;

    public void test() {
        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
        System.out.println(beanName);
    }
}
