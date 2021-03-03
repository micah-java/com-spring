package com.luban.spring.context.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class LubanBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if("userService".equals(beanName) && "com.luban.spring.context.service.UserService".equals(clazz.getName())){
            try {
                Field name = clazz.getDeclaredField("beanName");
                name.setAccessible(true);
                name.set(bean,"userService");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bean;
    }
}
