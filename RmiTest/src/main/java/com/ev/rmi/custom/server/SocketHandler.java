package com.ev.rmi.custom.server;

import com.ev.rmi.custom.model.RpcReqest;
import com.ev.rmi.custom.tool.RpcTools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

/**
 * @author arrow-zh
 * @create 2020/3/2
 */
public class SocketHandler implements  Runnable {

    private Socket socket;
    private Object service;

    public SocketHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    public void run() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            RpcReqest rpcReqest = (RpcReqest) ois.readObject();

            //利用反射调用获取结果
            Object result = RpcTools.invoke(service, rpcReqest);

            //将调用结果写回客户端
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if(ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
