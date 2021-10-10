package com.example.spring;

import org.springframework.context.support.GenericApplicationContext;

/**
 * @author bobo
 * @date 2021/10/10
 * @desc
 */
public class ApplicationContextDemo {
    public static void main(String[] args) {
        GenericApplicationContext applicationContext=new GenericApplicationContext();
        applicationContext.refresh();


        applicationContext.close();
    }
}
