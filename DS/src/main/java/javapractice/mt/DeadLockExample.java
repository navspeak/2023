package javapractice.mt;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;

public class DeadLockExample {
    //https://www.youtube.com/watch?v=B4IVu-2hCos
    //Run jstack pid
    // see output:
    /*
    Found one Java-level deadlock:
=============================
"Thread-0":
  waiting to lock monitor 0x0000021b2ee114a0 (object 0x0000000715018b80, a java.lang.Object),
  which is held by "Thread-1"

"Thread-1":
  waiting to lock monitor 0x0000021b2ee11900 (object 0x0000000715018b70, a java.lang.Object),
  which is held by "Thread-0"

     */

    /*
     = circular wait - not always possible to avoid
     Below causes dead locl
     Thread 1:                      Thread 2
     ----                           ----
       1. lock(A)
                                       2. lock B
                                       3. lock A - try
         3. lock(B) -try

     solution - enfrce a strict order on lock acquistion
              - tryLock with timeout
              - Watchdog detection - JMXBean
              - Thread Interruption (not possible)
     */
    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainAThread = new Thread(new TrainA(intersection));
        Thread trainBThread = new Thread(new TrainB(intersection));
        Thread watchDog = new Thread(DeadLockExample::detectDeadLock);
        trainAThread.start();
        trainBThread.start();
        watchDog.start();

    }

    private static void detectDeadLock() {
        while(!Thread.currentThread().isInterrupted()) {
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            long[] threadIds = threadBean.findDeadlockedThreads();
            boolean deadLock = threadIds != null && threadIds.length > 0;
            System.out.println("Deadlock found " + deadLock);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public static class TrainA implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainA(Intersection intersection){
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true){
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
                intersection.takeRoadA();
            }
        }
    }

    public static class TrainB implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainB(Intersection intersection){
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true){
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
                intersection.takeRoadB();
            }
        }
    }
    public static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        public void takeRoadA(){
            synchronized (roadA){
                System.out.println("Road A is locked by Train " + Thread.currentThread().getName());
                System.out.println("Road A intersection reached by Train " + Thread.currentThread().getName());
                synchronized (roadB){
                    System.out.println("Train is passing thru road A");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                       // e.printStackTrace();
                    }
                }
            }
        }

        public void takeRoadB(){
            synchronized (roadB){
                System.out.println("Road B is locked by Train " + Thread.currentThread().getName());
                synchronized (roadA){
                    System.out.println("Train is passing thru road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // e.printStackTrace();
                    }
                }
            }
        }
    }
}
