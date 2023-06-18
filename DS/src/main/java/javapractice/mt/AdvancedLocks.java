package javapractice.mt;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AdvancedLocks {

    public void test1(){
        ReentrantLock l1 = new ReentrantLock();
        ReentrantLock l2 = new ReentrantLock(true); //fairness - may reduce fairness
        l1.lock();
        l1.getHoldCount();
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

}
