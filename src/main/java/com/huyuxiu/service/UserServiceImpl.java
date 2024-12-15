package com.huyuxiu.service;

import com.spring.*;

@Component("userService")
@Scope("prototype")
public class UserServiceImpl implements BeanNameAware, InitializingBean,UserService {

    private String beanName;

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    @Autowired
    private OrderService orderService;

    public void test() {
        System.out.println("执行test方法");
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
        System.out.println(beanName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 可以验证某个属性是否为空，也可以为某个属性赋值
        System.out.println("初始化");
    }
}
