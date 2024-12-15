package com.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class XiuApplicationContext {
    private Class configClass;

    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap  = new ConcurrentHashMap<>();
    public XiuApplicationContext(Class configClass) {
        this.configClass = configClass;

        //解析配置类
        //Component注解---> 扫描路径 --->扫描
        scan(configClass);
        Iterator<String> keyIterator = beanDefinitionMap.keySet().iterator();
        while(keyIterator.hasNext()) {
            String beanName = keyIterator.next();
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")) {
                Object bean = createBean(beanDefinition);//单例bean
                singletonObjects.put(beanName,bean);
            }
        }
    }

    public Object createBean(BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClazz();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    private void scan(Class configClass) {

        ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value();//扫描路径
        //扫描路径 com.huyuxiu.service
        path = path.replace('.', '/');
        //扫描
        // Bootstrap ---> jre/lib
        // Ext ---> jre/ext/lib
        // App --->classpath
        ClassLoader classLoader = XiuApplicationContext.class.getClassLoader();//app
        URL resource = classLoader.getResource(path);
        File file = new File(resource.getFile());
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f:files){
                String fileName = f.getAbsolutePath();
                if(!fileName.endsWith(".class")){
                    continue;
                }
                String className = fileName.substring(fileName.indexOf("com"),fileName.indexOf(".class")).replace("\\",".");

                try {
                    Class<?> clazz = classLoader.loadClass(className);

                    if(clazz.isAnnotationPresent(Component.class)){
                        //表示当前类是一个Bean
                        //解析类，判断当前bean是单例bean，还是prototype的bean
                        //BeanDefinition
                        Component componentAnnotation = clazz.getDeclaredAnnotation(Component.class);
                        String beanName = componentAnnotation.value();

                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setClazz(clazz);

                        if(clazz.isAnnotationPresent(Scope.class)){
                            Scope scopeAnnotation = clazz.getDeclaredAnnotation(Scope.class);
                            beanDefinition.setScope(scopeAnnotation.value());
                        } else{
                            beanDefinition.setScope("singleton");
                        }
                        beanDefinitionMap.put(beanName,beanDefinition);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public Object getBean(String beanName) {
        if(beanDefinitionMap.containsKey(beanName)){
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")){
                return singletonObjects.get(beanName);
            } else{
                // 创建Bean对象
                Object bean = createBean(beanDefinition);
                return bean;
            }
        }else{
            // 不存在对应的bean
            throw new NullPointerException();
        }

    }
}
