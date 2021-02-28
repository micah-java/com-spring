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

    private String name;

    public String getName() {
        return name;
    }

    public void getMapper() {
        System.out.println(userMapper);
        System.out.println(loginMapper);
    }
}
