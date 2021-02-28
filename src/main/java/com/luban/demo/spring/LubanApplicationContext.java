package com.luban.demo.spring;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LubanApplicationContext {

    //Bean的定义
    private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
    //单例池
    private Map<String,Object> singletonMap = new ConcurrentHashMap<String, Object>();
    //BeanPostProcessor集合
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<BeanPostProcessor>();

    public LubanApplicationContext(Class clazz) throws ClassNotFoundException {

        //扫描路径
        List<Class> clazzList = classScan(clazz);

        //将class转换成BeanDefinition
        for (Class cl : clazzList) {

            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setClazz(cl);

            Component componentAnnotation = (Component) cl.getAnnotation(Component.class);
            //Component注解的value值即为beanName
            String beanName = componentAnnotation.value();

            if(cl.isAnnotationPresent(Scope.class)){
                Scope scopeAnnotation = (Scope) cl.getAnnotation(Scope.class);
                beanDefinition.setScope(scopeAnnotation.value());
            }else{
                //默认是单例
                beanDefinition.setScope("singleton");
            }
            beanDefinitionMap.put(beanName,beanDefinition);

            //判断class是否实现BeanPostProcessor接口
            if (BeanPostProcessor.class.isAssignableFrom(cl)) {
                try {
                    BeanPostProcessor beanPostProcessor = (BeanPostProcessor) cl.getDeclaredConstructor().newInstance();
                    beanPostProcessorList.add(beanPostProcessor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //基于class创建单例实例
        instanceSingletonBean();
    }

    private void instanceSingletonBean() {
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if("singleton".equals(beanDefinition.getScope())){
                //单例池没有该beanName则创建
                if(!singletonMap.containsKey(beanName)){
                    Object bean = createBean(beanName, beanDefinition);
                    singletonMap.put(beanName,bean);
                }
            }
        }
    }

    public Object getBean(String beanName){
        Object bean = null;
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if("singleton".equals(beanDefinition.getScope())){
            //单例池中获取单例
            bean = singletonMap.get(beanName);
            if(bean == null){
                //如果没有找到，创建并放入
                bean = createBean(beanName, beanDefinition);
                singletonMap.put(beanName,bean);
            }
        }else if("prototype".equals(beanDefinition.getScope())){
            //创建一个Bean
            bean = createBean(beanName, beanDefinition);
        }
        return bean;
    }

    public Object createBean(String beanName,BeanDefinition beanDefinition){

        Object bean = null;
        try {
            //1 实例化
            Class clazz = beanDefinition.getClazz();
            bean = clazz.getDeclaredConstructor().newInstance();
            //2 属性填充
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if(declaredField.isAnnotationPresent(Autowired.class)){
                    //对@Autowired注解属性赋值
                    declaredField.setAccessible(true);
                    declaredField.set(bean,singletonMap.get(declaredField.getName()));
                }
            }
            //3 aware
            if(bean instanceof BeanNameAware){
                ((BeanNameAware)bean).setBeanName(beanName);
            }
            // init before
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                bean = beanPostProcessor.postProcessBeforeInitialization(bean,beanName);
            }
            //4 init
            if(bean instanceof InitializingBean){
                ((InitializingBean)bean).afterPropertiesSet();
            }
            // init after
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                bean = beanPostProcessor.postProcessAfterInitialization(bean,beanName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 扫描class文件得到class
     */
    private List<Class> classScan(Class clazz) throws ClassNotFoundException {

        List<Class> clazzList = new ArrayList<Class>();
        //判断是否有ComponentScan注解
        if(clazz.isAnnotationPresent(ComponentScan.class)){
            ComponentScan componentScanAnnotation = (ComponentScan) clazz.getAnnotation(ComponentScan.class);
            //获取注解的value值，即扫描包路径
            String path = componentScanAnnotation.value();//com.luban.demo.service
            path = path.replace(".", "/");//com/luban/demo/service
            //获取类加载器
            ClassLoader classLoader = LubanApplicationContext.class.getClassLoader();
            //classpath:D:\workspace\com-spring\target\classes
            URL url = classLoader.getResource(path);
            File file = new File(url.getFile());//com/luban/demo/service文件夹
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    String absolutePath = f.getAbsolutePath();
                    absolutePath = absolutePath.substring(absolutePath.indexOf("com\\"),absolutePath.indexOf(".class"));
                    absolutePath = absolutePath.replace("\\", ".");
                    //通过全限定名获取class对象
                    Class<?> loadClass = classLoader.loadClass(absolutePath);
                    if(loadClass.isAnnotationPresent(Component.class)){
                        //过滤有Component注解的类才需要spring实例化
                        clazzList.add(loadClass);
                    }
                }
            }
        }
        return clazzList;
    }

}
