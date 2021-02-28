package com.luban.demo;

import com.luban.demo.service.UserService;
import com.luban.demo.spring.LubanApplicationContext;

public class App {
    public static void main(String[] args) throws ClassNotFoundException {
        //启动spring
        LubanApplicationContext lubanApplicationContext = new LubanApplicationContext(AppConfig.class);

        UserService userService = (UserService) lubanApplicationContext.getBean("userService");

        System.out.println("user: " + userService.getUser());
        System.out.println("name: " + userService.getName());

    }
}
