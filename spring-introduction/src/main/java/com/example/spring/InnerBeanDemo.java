package com.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.SystemProperties;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author bobo
 * @date 2021/8/29
 * @desc
 */
public class InnerBeanDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.refresh();

        //处理外部化配置和profiles
        Environment environment = applicationContext.getBean("environment", Environment.class);
//        environment.getActiveProfiles();
//        environment.getProperty("key");
//        System.out.println(environment.resolvePlaceholders("=========${java.library.path}"));;
//        System.out.println(environment);

        //处理java系统属性
        Properties properties = applicationContext.getBean("systemProperties", Properties.class);

        //处理操作系统环境变量
        Map systemEnvironment = applicationContext.getBean("systemEnvironment", Map.class);

        //处理spring中所有Lifecycle bean的启停
        LifecycleProcessor lifecycleProcessor = applicationContext.getBean("lifecycleProcessor", LifecycleProcessor.class);

        //处理事件监听、广播
        ApplicationEventMulticaster applicationEventMulticaster = applicationContext.getBean("applicationEventMulticaster", ApplicationEventMulticaster.class);
        applicationContext.close();
    }

}
