package com.huyuxiu;

import com.spring.XiuApplicationContext;

public class Test {
    public static void main(String[] args) {
        XiuApplicationContext applicationContext = new XiuApplicationContext(AppConfig.class);
        Object userService = applicationContext.getBean("userService");
    }
}
