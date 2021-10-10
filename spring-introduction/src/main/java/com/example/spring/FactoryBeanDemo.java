package com.example.spring;

import com.example.spring.entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author bobo
 * @date 2021/8/28
 * @desc
 */
public class FactoryBeanDemo {
    public static void main(String[] args) {
        factoryBean_byXML();
        factoryBean_byBeanConfiguration();
    }

    public static void factoryBean_byXML(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean-factoryBean.xml");
        System.out.println(applicationContext.getBean("user-by-factory-bean"));
    }

    public static void factoryBean_byBeanConfiguration(){
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext();
        applicationContext.register(MyConfiguration.class);
        applicationContext.refresh();
        System.out.println(applicationContext.getBeansOfType(User.class));
        applicationContext.close();
    }


    @Configuration
    public static class MyConfiguration {
        @Bean
        public UserFactoryBean userFactoryBean(){
            return new UserFactoryBean("xx002","波波");
        }
    }
}
