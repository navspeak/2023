package javapractice.mt;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
// https://www.baeldung.com/java-concurrent-locks
public class Locking {
}

class Reader implements Runnable {

    ReentrantLock lock = new ReentrantLock();
    Condition c = lock.newCondition();

    @Override
    public void run() {

    }

    void readFromFile() throws InterruptedException {
        System.out.println("Thread + " + Thread.currentThread().getName() +
                " doing some prep work");
        Thread.sleep(10000);

    }

    void readFileNameFromDB() {
        System.out.println("Thread + " + Thread.currentThread().getName() +
                " trying to lock the DB");

        while(!Thread.currentThread().isInterrupted()){
            lock.lock();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        lock.lock();
        System.out.println("Thread + " + Thread.currentThread().getName() +
                " got the db lock");
        //Thread.sleep(10000);

    }
}