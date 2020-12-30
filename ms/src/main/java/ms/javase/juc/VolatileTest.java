package ms.javase.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create By arrow 2020/12/30
 */
public class VolatileTest {

    private static boolean ready;
    private static volatile AtomicInteger number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            if(Thread.currentThread().getId()  == 30)
                number.addAndGet(1);
            System.out.println(Thread.currentThread().getId() + ":" + Thread.currentThread().getName() + ":" + number.get());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        for (int i=1; i<1000;i++) {
            new ReaderThread().start();
        }

        number = new AtomicInteger(1);
        ready = true;
    }
}
