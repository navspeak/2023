package javapractice.mt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class AdvancedLocks {

    public void test1(){
        System.out.println();
        ReentrantLock l1 = new ReentrantLock();
       // ReentrantLock l2 = new ReentrantLock(true); //fairness - may reduce fairness
        l1.lock();
        System.out.println(l1.getHoldCount());
        //l1.getWaitQueueLength();
        l1.getQueueLength();
        l1.tryLock();
        try {
            l1.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try{
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            l1.unlock();
        }
    }
    static class Task {
        ReentrantLock l;
        Task(ReentrantLock l){
           this.l = l;
        }
        void doTask(){
            try {
                System.out.println(Thread.currentThread().getName() + " going to acquire lock " + l.getHoldCount()  + " " +  l.getQueueLength());
                l.lock();
                System.out.println(Thread.currentThread().getName() + " acquired lock " + l.getHoldCount()  + " " +  l.getQueueLength());
                IntStream.rangeClosed(0,9000)
                        .boxed()
                        .forEach(i -> {
                            try {
                                System.out.println("...");
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                l.unlock();
                System.out.println(Thread.currentThread().getName() + " unlocked " + l.getHoldCount() + " " + l.getQueueLength());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        ReentrantLock l = new ReentrantLock();
        final Task task = new Task(l);
        Runnable Task1 = () -> {
            task.doTask();
        };

        Runnable Task2 = () -> {
            task.doTask();
        };

        service.submit(Task1);
        service.submit(Task2);

        service.shutdownNow();

        service.awaitTermination(1, TimeUnit.DAYS);

    }

}
