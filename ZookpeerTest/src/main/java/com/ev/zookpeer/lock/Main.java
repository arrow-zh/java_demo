package com.ev.zookpeer.lock;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

/**
 * Create By arrow 2020/03/06
 */
public class Main {
    public static void main(String[] args) {
        for(int i=0; i <10; i ++) {
            new Thread(){
                @Override
                public void run() {
                    Lock lock = null;
                    try {
                        lock = new DistributeLock("127.0.0.1:2181");
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + "处理具体业务逻辑");
                        Thread.sleep(2000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }.start();
        }
    }
}
