package com.ev.rmi.jdk.server;

import com.ev.rmi.jdk.service.HelloServiceImp;
import com.ev.rmi.jdk.service.IHelloService;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author arrow-zh
 * @create 2020/3/2
 */
public class Server {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        IHelloService helloService = new HelloServiceImp();
        Registry registry = LocateRegistry.createRegistry(8080);
        registry.bind("Hello", helloService);

//      Naming.rebind("rmi://127.0.0.1:8080/Hello", helloService);
        System.out.println("服务启动成功.....");

    }
}
