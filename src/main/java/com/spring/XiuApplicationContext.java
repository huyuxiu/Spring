package com.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;

public class XiuApplicationContext {
    private Class configClass;

    public XiuApplicationContext(Class configClass) {
        this.configClass = configClass;

        //解析配置类
        //Component注解---> 扫描路径 --->扫描
        ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value();//扫描路径
        System.out.println("path = " + path);//扫描路径 com.huyuxiu.service
        path.replace('.', '/');
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
                System.out.println(className);

                try {
                    Class<?> clazz = classLoader.loadClass(className);

                    if(clazz.isAnnotationPresent(Component.class)){
                        //表示当前类是一个Bean
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public Object getBean(String beanName) {
        return null;
    }
}
