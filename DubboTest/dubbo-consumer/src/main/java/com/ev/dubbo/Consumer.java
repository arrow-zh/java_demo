package com.ev.dubbo;

import com.ev.dobbo.service.IHelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Create By arrow 2020/03/13
 */
public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        IHelloService helloService = (IHelloService) context.getBean("providerService");
        String res = helloService.sayHello("arrow");

        System.out.println(res);
    }
}
