package com.ev.dobbo.service.imp;

import com.ev.dobbo.service.IHelloService;

import java.time.LocalDate;

/**
 * Create By arrow 2020/03/13
 */
public class HelloService implements IHelloService {

    public String sayHello(String name) {
        System.out.println("[" + LocalDate.now() + "] Hello " + name );
        return "hello " + name;
    }

}
