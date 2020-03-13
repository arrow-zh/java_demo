package com.ev.dobbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Create By arrow 2020/03/13
 */
public class Bootstrp {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/provider.xml");
        context.start();
        System.in.read();
    }
}
