package com.luban.spring.context;

import com.luban.spring.context.mapper.UserMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LubanFactoryBean implements FactoryBean {

    private Class mapperClass;

    public LubanFactoryBean(Class mapperClass) {
        this.mapperClass = mapperClass;
    }

    public Object getObject() throws Exception {
        Object object = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
        return object;
    }

    public Class<?> getObjectType() {
        return mapperClass;
    }
}
