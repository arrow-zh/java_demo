package com.ev.rmi.custom.client;

import com.ev.rmi.custom.service.IHelloService;
import com.ev.rmi.custom.tool.RpcProxyUtils;

/**
 * @author arrow-zh
 * @create 2020/3/2
 */
public class Client {

    /**
     * 客户端主入口
     * @param args
     */
    public static void main(String[] args) {
        RpcProxyUtils rpcProxyUtils = new RpcProxyUtils();

        IHelloService service = rpcProxyUtils.clientProxy(IHelloService.class, "127.0.0.1", "8080");
        String result = service.sayHello("arrow-zh");
        System.out.println(result);
    }
}
