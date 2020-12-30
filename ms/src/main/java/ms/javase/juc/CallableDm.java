package ms.javase.juc;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create By arrow 2020/12/30
 */
public class CallableDm {




    private Callable callable = new Callable() {
        public Object call() {
            return  "callabel:" + Thread.currentThread().getName() + ":" + Thread.currentThread().getId();
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("runnable:" + Thread.currentThread().getName() + ":" + Thread.currentThread().getId());
        }
    };

    @Test
    public void testCallable() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.submit(runnable);
        Future f2 = pool.submit(callable);
        System.out.println(f2.get().toString());
    }


    @Test
    public void testExecutor(){
        //创建newCahchedExecutor  不限制大小  缓存线程
        ExecutorService poolCached = Executors.newCachedThreadPool();
        for (int i=1;i<100;i++) {
            poolCached.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Cached:" + Thread.currentThread().getName() + ":" + Thread.currentThread().getName());
                }
            });
        }


        //newFixedThreadPool 固定大小线程池  不会回收 指直销毁
        ExecutorService poolFixed = Executors.newFixedThreadPool(10);
        for (int i=1;i<100;i++) {
            poolFixed.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Fixed" + Thread.currentThread().getName() + ":" + Thread.currentThread().getName());
                }
            });
        }

        //newScheduledThreadPool
        ScheduledExecutorService poolTime = Executors.newScheduledThreadPool(10);
        for (int i=1;i<100;i++) {
            poolTime.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("schedule:"+Thread.currentThread().getName() + ":" + Thread.currentThread().getName());
                }
            },3, TimeUnit.SECONDS);
        }

        //newSingalThredPool
        ExecutorService poolSingle = Executors.newSingleThreadExecutor();
        for (int i=1;i<100;i++) {
            poolSingle.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("single:"+Thread.currentThread().getName() + ":" + Thread.currentThread().getName());
                }
            });
        }
    }


}
