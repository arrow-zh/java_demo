package com.ev.rmi.jdk.client;

import com.ev.rmi.jdk.service.IHelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author arrow-zh
 * @create 2020/3/2
 */
public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        IHelloService service = (IHelloService) Naming.lookup("rmi://127.0.0.1:8080/Hello");
        String result = service.sayHello("arrow-zh");
        System.out.println(result);
    }
}
