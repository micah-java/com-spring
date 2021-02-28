package com.luban.demo.service;

import com.luban.demo.spring.BeanPostProcessor;
import com.luban.demo.spring.Component;

@Component
public class LubanBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("11111111111111111");
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("22222222222222222");
        return bean;
    }
}
