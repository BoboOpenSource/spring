package com.example.spring;

import com.example.spring.entity.User;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author bobo
 * @date 2021/8/29
 * @desc {@link org.springframework.beans.factory.ObjectProvider}相比传统的依赖查找、注入，提供更为宽松的机制
 */
public class ObjectProviderDemo {

    public static void main(String[] args) {
        lookup();
    }

    /**
    * @desc 通过 {@link ObjectProvider}实现依赖注入
    */
    public static void injection(){
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext();
        applicationContext.register(ObjectProviderDemo.class);
        applicationContext.register(UserService.class);
        applicationContext.refresh();
        applicationContext.getBean(UserService.class).test();
        applicationContext.close();
    }

    /**
     * @desc 通过 {@link ObjectProvider}实现依赖查找
     */
    public static void lookup(){
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext();
        applicationContext.register(ObjectProviderDemo.class);
        applicationContext.refresh();
        ObjectProvider<User> objectProvider= applicationContext.getBeanProvider(User.class);
        objectProvider.orderedStream().forEach(System.out::println);
        applicationContext.close();
    }


    @Bean
    public User user() {
        return new User("xx0001", "波波");
    }
    @Bean
    public User user2() {
        return new User("xx0002", "波波");
    }
    @Bean
    public User user3() {
        return new User("xx0003", "波波");
    }

    public static class UserService{
        private User user;

        public UserService(ObjectProvider<User> objectProvider) {
//            objectProvider.getIfUnique();
//            objectProvider.getIfAvailable();
//            objectProvider.ifAvailable(user->this.user=user);
            objectProvider.orderedStream().forEach(user->{
                System.out.println(user);
                if(user.getId().equals("xx0001")){
                    this.user=user;
                }
            });
        }

        public void test(){
            System.out.println("UserService:"+user.toString());
        }
    }

}
