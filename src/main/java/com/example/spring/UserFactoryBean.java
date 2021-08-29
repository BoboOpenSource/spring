package com.example.spring;

import com.example.spring.entity.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author bobo
 * @date 2021/8/28
 * @desc
 */
public class UserFactoryBean implements FactoryBean<User> {
    private String id;
    private String name;

    public UserFactoryBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public User getObject() throws Exception {
        return new User(id, name);
    }

    @Override
    public Class<User> getObjectType() {
        return User.class;
    }
}
