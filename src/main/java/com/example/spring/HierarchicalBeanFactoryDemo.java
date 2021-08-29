package com.example.spring;

import com.example.spring.entity.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bobo
 * @date 2021/8/29
 * @desc
 */
public class HierarchicalBeanFactoryDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext pApplicationContext=new AnnotationConfigApplicationContext(MyConfiguration2.class);
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(MyConfiguration.class);
        //父子容器
        applicationContext.setParent(pApplicationContext);
        //当前容器是否包含bean
        System.out.println(applicationContext.containsLocalBean("user"));
        System.out.println(applicationContext.containsLocalBean("user2"));
        //当前容器及其祖先容器是否包含bean
        System.out.println(applicationContext.containsBean("user2"));
    }

    @Configuration
    public static class MyConfiguration{
        @Bean
        public User user(){
            User user=new User();
            user.setId("1111");
            user.setName("波波");
            return user;
        }
    }
    @Configuration
    public static class MyConfiguration2{
        @Bean
        public User user2(){
            User user=new User();
            user.setId("1111");
            user.setName("波波");
            return user;
        }
    }
}
