package com.ev.zookpeer;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Create By arrow 2020/03/05
 */
public class ZookeeperTestConnection implements Watcher {

    public static String ADDRESS = "127.0.0.1:2181";

    //
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
            System.out.println("ZookPeer 连接成功");
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(ADDRESS, 5000, new ZookeeperTestConnection());
        System.out.println(zooKeeper.getState());
        //等待
        countDownLatch.await();
        System.out.println(zooKeeper.getState());
    }

}
