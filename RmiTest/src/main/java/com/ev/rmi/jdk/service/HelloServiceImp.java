package com.ev.rmi.jdk.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImp extends UnicastRemoteObject implements IHelloService {

    public HelloServiceImp() throws RemoteException {
        super();
    }

    /**
     * 实现接口方法
     * @param name
     */
    public String sayHello(String name) throws RemoteException {
        System.out.println("服务器方法sayHello被调用.....");
        return "hello " + name;
    }
}
