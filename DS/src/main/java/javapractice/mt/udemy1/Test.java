package javapractice.mt.udemy1;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) {
        //int randomBetween8And11 = new Random().nextInt(11-8 +1) + 8;
        ExecutorService service = Executors.newSingleThreadExecutor(); //LinkedBlockingQueue
        ExecutorService service1 = Executors.newCachedThreadPool(); //SynchronusQueue



    }
}


class LoopTaskA implements Runnable {
    private static int count = 0;
    private volatile int id;

    @Override
    public void run() {
        System.out.println("##### <TASK-" + id + "> STARTING #####");

        for (int i=10; i>0; i--) {
            System.out.println("<" + id + ">TICK TICK - " + i);

            try {
                TimeUnit.MILLISECONDS.sleep((long)(Math.random() * 1000)); // 0 to 1
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("***** <TASK-" + id + "> COMPLETED *****");
    }

    public LoopTaskA() {
        this.id = ++count;
    }
}