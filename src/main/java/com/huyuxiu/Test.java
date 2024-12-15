package com.huyuxiu;

import com.huyuxiu.service.UserService;
import com.spring.XiuApplicationContext;

public class Test {
    public static void main(String[] args) {
        XiuApplicationContext applicationContext = new XiuApplicationContext(AppConfig.class);
        // 单例bean map<beanName,bean对象>
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.test();
    }
}
