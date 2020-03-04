package com.ev.rmi.custom.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHelloService  {

    public String sayHello(String name) ;
}
