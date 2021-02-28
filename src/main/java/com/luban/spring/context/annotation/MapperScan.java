package com.luban.spring.context.annotation;

import com.luban.spring.context.LubanBeanDefinitionRegistrar;
import com.luban.spring.context.processor.LubanBeanFactoryPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(LubanBeanDefinitionRegistrar.class)
public @interface MapperScan {
    String value() default "";
}
