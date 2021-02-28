package com.luban.demo.service;

import com.luban.demo.spring.*;

@Component("userService")
public class UserService implements BeanNameAware , InitializingBean {

    @Autowired
    private User user;
    private String beanName;

    private String userName;

    public User getUser() {
        return user;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getName(){
        return beanName + "," + userName;
    }

    public void afterPropertiesSet() {
        this.userName = "user";
    }
}
