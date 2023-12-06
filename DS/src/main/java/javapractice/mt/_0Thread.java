package javapractice.mt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class _0Thread {
    // interrupts -java threads are cooperative. Runnable Task must check interrupt
    // Java Concurrency Interview Question: How to timeout a thread? :https://www.youtube.com/watch?v=_RSAS-gIjGo
    // ThreadPool.shutdonwNow(), future.cancet(true) sendss thread.interupt() for running threads. The task must habdle otherwise no use:
    /*
            () ->{
              while(!Thread.currentThread().isInterrupted()){

            }
      How to use Volatile / Atomic Boolean to stop thread.
      https://youtu.be/_RSAS-gIjGo?t=463 - however doesn't work for IO operation https://youtu.be/_RSAS-gIjGo?t=538
      Deadlock: https://www.youtube.com/watch?v=B4IVu-2hCos
     */
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
       /* Object class has:
        public final native void notify();
        public final native void notifyAll();
        public final native void wait(long timeoutMillis) throws InterruptedException;
        public final void wait() throws InterruptedException {
            wait(0L);
        See Thread.join

        */
        Thread t = new Thread(()-> {
            System.out.println(Thread.currentThread().getName() + " Will do something bad in 10 sec...");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < i; j++) {
                    System.out.print(".");
                }
                System.out.println();
                sleep(1);
            }
            sleep(1);
            int x = 0;
            int y = 10/x;});
        t.setUncaughtExceptionHandler((Thread th, Throwable e) -> {
                System.out.println("Exception in " + th.getName() + " " + e.getMessage());
            });
        t.start();



    }
    private static void sleep(int timeout) {
        try {
                TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
                throw new RuntimeException(e);
        }
    }

    public static void GC() {
        /*
        // System.out.println(); => println is synchronized
        // Vector, StringBuffer, HashTable are sych'd (so use StringBuilder)
        // CMS GC - pld pre JDK 9, G1 GC - JDC 9 onwards, ZGC - 11 onwards
        // GC Defaults = JVM tries to reserve 25% of RAM, -XX:InitialRAMPercentage
        //-XX:MaxRAMPercentage, -XX:MinRAMPercentage
        // Async Logging - or logging to a file
        // Use connection pool judiciously (like HikariCP instead of Tomcat
         */
    }

}


