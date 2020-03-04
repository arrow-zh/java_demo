package com.ev.rmi.custom.tool;

import com.ev.rmi.custom.client.TcpTransport;
import com.ev.rmi.custom.model.RpcReqest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author arrow-zh
 * @create 2020/3/2
 */
public class RpcProxyUtils {

    public   <T> T clientProxy(Class<?> interfaces, String ip, String port){
        return (T) Proxy.newProxyInstance(interfaces.getClassLoader(), new Class[]{interfaces}, new RemoteInvocationHandler(ip, port));
    }


    private  class RemoteInvocationHandler implements  InvocationHandler{

        private String ip;
        private String port;

        public RemoteInvocationHandler(String ip, String port) {
            this.ip = ip;
            this.port = port;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcReqest reqest = new RpcReqest();
            reqest.setClassName(method.getClass().getName());
            reqest.setMethodName(method.getName());
            reqest.setArgs(args);

            return new TcpTransport(ip, port).sendRequst(reqest);
        }
    }

}
