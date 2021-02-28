package com.luban.spring.context.config;

import com.luban.spring.context.annotation.MapperScan;
import com.luban.spring.context.processor.LubanBeanFactoryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@MapperScan("com.luban.spring.context.mapper")
public class LubanMapperScan {
}
