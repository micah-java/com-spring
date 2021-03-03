package com.luban.spring;

import com.luban.spring.context.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println("name: " + userService.getBeanName());
        userService.getMapper();
    }
}
