package com.ev.rmi.custom.client;

import com.ev.rmi.custom.model.RpcReqest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author arrow-zh
 * @create 2020/3/2
 */
public class TcpTransport {


    private String ip;
    private String port;

    public TcpTransport(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public Object sendRequst(RpcReqest reqest){
        Socket socket = null;
        try {
            socket = new Socket(ip, Integer.parseInt(port));

            //发送对象过去
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(reqest);
            oos.flush();

            //获取对面发送过来的数据
            ObjectInputStream bis = new ObjectInputStream(socket.getInputStream());
            Object object = bis.readObject();

            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
