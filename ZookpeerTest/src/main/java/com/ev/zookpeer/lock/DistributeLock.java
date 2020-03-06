package com.ev.zookpeer.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Create By arrow 2020/03/06
 */
public class DistributeLock implements Lock, Watcher {

    private String ROOT_LOCK = "/lock";


    private String currentNode = "";
    private String waitNode = "";

    private ZooKeeper zk = null;
    private CountDownLatch countDownLatch = null;

    //构造方法创建连接
    public DistributeLock(String address) throws IOException, InterruptedException, KeeperException {
        //获取连接
        zk = new ZooKeeper(address, 5000, this);
        Thread.sleep(1000);

        //判断根节点是否存在，如果不存在则创建根节点
        Stat stat = zk.exists(ROOT_LOCK, false);
        if(stat == null) {
            zk.create(ROOT_LOCK, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    //尝试获取锁
    public void lock() {
        if(tryLock()) {
            System.out.println(Thread.currentThread().getName()  + currentNode +  "的到了锁");
            return;
        } else {
            waitLock(waitNode, 5000);
        }
    }

    //等待锁
    private boolean waitLock(String prev,long waitTime) {
        if(zk != null) {
            try {
                Stat stat = zk.exists(ROOT_LOCK + "/" + prev, true);
                if(stat != null) {
                    countDownLatch = new CountDownLatch(1);
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getName() + " 等到了锁");
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {
        try {
            currentNode = zk.create(ROOT_LOCK + "/" + "zk","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("创建节点" + currentNode);

            //获取所有节点
            List<String> children = zk.getChildren(ROOT_LOCK,false);
            Collections.sort(children);
            if(currentNode.equals(ROOT_LOCK + "/" + children.get(0))) {
                return true;
            } else {
                String prevNode = currentNode.substring(currentNode.lastIndexOf("/") + 1);
                waitNode = children.get(Collections.binarySearch(children, prevNode) - 1);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        try {
            if (this.tryLock()) {
                return true;
            }
            return waitLock(waitNode,time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void unlock() {
        System.out.println("释放了锁"  + currentNode);
        try {
            zk.delete(currentNode, -1);
            currentNode = null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public Condition newCondition() {
        return null;
    }

    public void process(WatchedEvent watchedEvent) {
        if (this.countDownLatch != null) {
            this.countDownLatch.countDown();
        }
    }
}
