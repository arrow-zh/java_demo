package com.ev.rmi.custom.server;

import com.ev.rmi.custom.service.HelloServiceImp;
import com.ev.rmi.custom.service.IHelloService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author arrow-zh
 * @create 2020/3/2
 */
public class Server {

    //创建线程池
    private ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 利用ServerSocket发布服务
     * @param port
     * @param service
     */
    public void publishService(int port, Object service)  {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new SocketHandler(socket, service));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
             if(serverSocket != null) {
                 try {
                     serverSocket.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
        }
    }

    /**
     * 服务器主入口
     * @param args
     */
    public static void main(String[] args) {
        IHelloService helloService = new HelloServiceImp();

        //发布服务
        Server server = new Server();
        server.publishService(8080, helloService);
    }
}
