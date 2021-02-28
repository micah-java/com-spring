package com.luban.spring.context;

import com.luban.spring.context.annotation.MapperScan;
import com.luban.spring.context.mapper.LoginMapper;
import com.luban.spring.context.mapper.UserMapper;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

public class LubanBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        List<Class> list = new ArrayList<Class>();

        MultiValueMap<String, Object> allAnnotationAttributes = importingClassMetadata.getAllAnnotationAttributes(MapperScan.class.getName());
        List<Object> value = allAnnotationAttributes.get("value");
        System.out.println("value: " + value);


        list.add(UserMapper.class);
        list.add(LoginMapper.class);
        for (Class clazz : list) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            constructorArgumentValues.addGenericArgumentValue(clazz);
            beanDefinition.setBeanClass(LubanFactoryBean.class);
            //注册BeanDefinition
            registry.registerBeanDefinition(clazz.getName(),beanDefinition);
        }
    }
}
