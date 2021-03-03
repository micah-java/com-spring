package com.luban.spring.context.service;

import com.luban.spring.context.mapper.LoginMapper;
import com.luban.spring.context.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    LoginMapper loginMapper;

    private String beanName;

    public String getBeanName() {
        return beanName;
    }

    public void getMapper() {
        System.out.println("111:" + userMapper);
        System.out.println("222:" + loginMapper);
    }
}
