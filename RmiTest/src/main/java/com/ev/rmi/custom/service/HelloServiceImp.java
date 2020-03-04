package com.ev.rmi.custom.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImp  implements IHelloService {

    /**
     * 实现接口方法
     * @param name
     */
    public String sayHello(String name)  {
        System.out.println("服务器方法sayHello被调用.....");
        return "hello " + name;
    }
}
