package com.example.spring;

import com.example.spring.entity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author bobo
 * @date 2021/8/28
 * @desc {@link SingletonBeanRegistry}注册一个已经实例化完成的object到spring容器中，不会执行任何回调
 */
public class RegisterBeanDemo {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        applicationContext.refresh();
        UserService userService = new UserService();
        userService.setId("service-001");
        applicationContext.getBeanFactory().registerSingleton("userService", userService);
        System.out.println(applicationContext.getBean("userService"));
        applicationContext.close();
    }

    public static class UserService implements InitializingBean {

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("====执行userService初始化逻辑===");
        }

        @Override
        public String toString() {
            return "UserService{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
}
