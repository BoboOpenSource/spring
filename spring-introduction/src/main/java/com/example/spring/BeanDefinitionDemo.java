package com.example.spring;

import com.example.spring.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bobo
 * @date 2021/8/28
 */
public class BeanDefinitionDemo {

    public static void main(String[] args) {
        registerBeanDefinitions_byAnnotationWithImportSelector();
    }

    /**
     * @desc 通过API调用注入beanDefinition
     */
    public static void registerBeanDefinition_byApi() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.refresh();

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
//        builder.addPropertyValue("id","xx111");
//        builder.addPropertyValue("name","bobo");
        builder.addConstructorArgValue("xx111");
        builder.addConstructorArgValue("bobo");
        registerBeanDefinition(applicationContext, builder.getBeanDefinition(), "user");
        registerBeanDefinition(applicationContext, builder.getBeanDefinition(), "");
        //获取一组指定类型的bean，返回Map<String/*beanName*/,Bean>结构
        System.out.println(applicationContext.getBeansOfType(User.class));
        applicationContext.close();
    }

    private static void registerBeanDefinition(BeanDefinitionRegistry registry, AbstractBeanDefinition beanDefinition, String beanName) {
        if (StringUtils.hasText(beanName)) {
            registry.registerBeanDefinition(beanName, beanDefinition);
        } else {
            //用于没有指定beanName的场景
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
        }
    }

    /**
     * @desc 通过@Configuration注入beanDefinition
     */
    public static void registerBeanDefinition_byConfiguration() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MyConfiguration.class);
        applicationContext.refresh();

        System.out.println(applicationContext.getBean("user", User.class));
        applicationContext.close();
    }

    /**
     * @desc 通过@Import @Configuration 方式注入beanDefinition
     */
    public static void registerBeanDefinition_byImportConfiguration() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MyImport1.class);
        applicationContext.refresh();

        System.out.println(applicationContext.getBean("user", User.class));
        applicationContext.close();
    }

    /**
     * @desc 通过@Import普通类 方式注入beanDefinition
     */
    public static void registerBeanDefinition_byImportCommonClass() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MyImport2.class);
        applicationContext.refresh();

        applicationContext.getBean(UserService.class).test();
        applicationContext.close();
    }

    /**
     * @desc 通过带有@Import的@Enablexx注解类 方式注入beanDefinition
     */
    public static void registerBeanDefinition_byAnnotationWithImport() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MyImport1.class);
        applicationContext.register(UserServiceConfiguration.class);
        applicationContext.refresh();

        applicationContext.getBean(UserService.class).test();
        applicationContext.close();
    }

    /**
     * @desc 通过带有@Import({@link ImportSelector})的@Enablexx注解类 方式注入多个beanDefinition
     */
    public static void registerBeanDefinitions_byAnnotationWithImportSelector() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MyImport1.class);
        applicationContext.register(UserServicesConfiguration.class);
        applicationContext.refresh();

        applicationContext.getBean(UserService.class).test();
        applicationContext.getBean(UserService2.class).test();
        applicationContext.close();
    }

    @Import(MyConfiguration.class)
    public static class MyImport1 {
    }

    @Import({MyConfiguration.class, UserService.class})
    public static class MyImport2 {
    }

    @EnableUserService
    public static class UserServiceConfiguration {
    }

    @EnableUserServices
    public static class UserServicesConfiguration {
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Import(UserService.class)
    public @interface EnableUserService {
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Import(UserServicesImportSelector.class)
    public @interface EnableUserServices {
    }

    public static class UserServicesImportSelector implements ImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{UserService.class.getName(), UserService2.class.getName()};
        }
    }

    @Configuration
    public static class MyConfiguration {
        @Bean
        public User user() {
            User user = new User();
            user.setId("1111");
            user.setName("波波");
            return user;
        }
    }

    public static class UserService {
        @Autowired
        private User user;

        public void test() {
            System.out.println("UserService:" + user.toString());
        }
    }

    public static class UserService2 {
        @Autowired
        private User user;

        public void test() {
            System.out.println("UserService2:" + user.toString());
        }
    }

    public class MyBeanDefinitionRegister implements BeanDefinitionRegistryPostProcessor {

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        }
    }
}
