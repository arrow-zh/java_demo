package com.ev.zookpeer;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Create By arrow 2020/03/05
 */
public class ZookeeperTestCRUD implements Watcher {

    public static String ADDRESS = "127.0.0.1:2181";

    //
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
            System.out.println("ZookPeer 连接成功");
        }
    }

    /**
     * 同步创建节点
     * @param zooKeeper
     *
     * CreateMode  PERSISTENT                持久节点
     *             PERSISTENT_SEQUENTIAL     持久序号节点
     *             EPHEMERAL                 临时节点
     *             EPHEMERAL_SEQUENTIAL      临时序号节点
     */
    public static void createNodeSync(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
    }

    /**
     * 获取某个节点下子节点
     */
    public static void getChildren(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        List<String> children =  zooKeeper.getChildren(path, false);
        System.out.println(children);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(ADDRESS, 5000, new ZookeeperTestCRUD());
        System.out.println(zooKeeper.getState());
        //等待
        countDownLatch.await();
        System.out.println(zooKeeper.getState());

//        createNodeSync(zooKeeper, "/test/a");
//        createNodeSync(zooKeeper, "/test/a");
//        createNodeSync(zooKeeper, "/test/a");

        getChildren(zooKeeper, "/test");
    }

}
