package com.example.spring;

import com.example.spring.entity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @author bobo
 * @date 2021/8/28
 * @desc {@link AutowireCapableBeanFactory}创建一个bean，会执行其完整的生命周期，包括各类回调方法，注意并不会将bean注入到spring容器中
 * @desc {@link AnnotationConfigApplicationContext}创建bean并将其注册到spring容器中，会执行其完整的生命周期，包括各类回调方法
 */
public class CreateBeanDemo {

    public static void main(String[] args) {
//        justCreate();//error
        createAndRegister();
    }

    public static void justCreate(){
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext();
        applicationContext.register(MyConfiguration.class);
        applicationContext.refresh();
        AutowireCapableBeanFactory autowireCapableBeanFactory=applicationContext.getAutowireCapableBeanFactory();
        UserService userService=autowireCapableBeanFactory.createBean(UserService.class);
        applicationContext.getBean(UserService.class).test();
        applicationContext.close();
    }

    public static void createAndRegister(){
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext();
        applicationContext.register(MyConfiguration.class);
        applicationContext.register(UserService.class);
        applicationContext.refresh();
        applicationContext.getBean(UserService.class).test();
        applicationContext.close();
    }

    public static class UserService implements InitializingBean {
        @Autowired
        private User user;

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("====执行userService初始化逻辑===");
        }

        public void test(){
            System.out.println("userService:"+user.toString());
        }
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
}
