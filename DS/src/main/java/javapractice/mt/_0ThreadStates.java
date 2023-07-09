package javapractice.mt;

import java.util.concurrent.TimeUnit;

public class _0ThreadStates {
    public static void threadPoolSize(){
        var n = Runtime.getRuntime().availableProcessors();
        //https://medium.com/@techashishgupta/determine-the-ideal-thread-pool-size-java-concurrency-766d11e1fa79
        // CPU intensive - nodejs not good for such use case - can't have thread more than cores
        //in case of IO bound tasks with one core CPU, you can increase the number of threads and could gain the maximum utilization of CPU.
        //Ideal thread Count= Number of Cores * [ 1+ (wait time/CPU time)] => wait time ~ 0 for CPU bound
        // Synch block is Reentrant
        /*
              public synchronized foo(){
                bar(); <- thread A
              }
              public synchronized bar(){ <- thread A (a thread can't prevent itself from entering a criical section
              }
              Note: all primitive assignments except to that of long and double are atomic. Long & double have two 32 bits
               operation. If we use Volatile it becomes atomic
         */
    }
    public static void main(String[] args) throws InterruptedException {
        //https://github.com/navspeak/2021/blob/07e57a8c127e527562509ecde8e52e232ce4e62d/algo/src/javastuff/BasicThreadStuff.java
        //https://www.baeldung.com/java-thread-lifecycle
        // For IO bound, while waiting on IO CPU is free so even with 1 core, we could have more threads do more work
        // ThreadPoolSize = (No of cores)(1 + wait_time/cpu_time), for IO bound wait_time = 0=> Amdahl's law
        /*
           wait() called within synch block and must check condition in while loop
           synch(lock){
                while(cond){
                    lock.wait()
                }
                do something
                notify()
           }
           Thread.sleep (static method, doesn't relinquish lock, wakes up after time specified or Interrupted WAITING/TIMED_WAIT
           Object.wait() - relinquishes lock, woken by interupts or notify ->  WAITING/TIMED_WAIT
           (join also sends to WAITING)
         */
        // use jps + jstack pid to get thread dump or kill -3 unix, also jconsole, jvisualvm
        newState();
        runnableState();
        blockedState();
        /*
        A thread is in WAITING state when it's waiting for some other thread to perform a particular action.
        According to JavaDocs, any thread can enter this state by calling any one of the following three methods:
            object.wait()
            thread.join() or
            LockSupport.park()
         */
        // TIMED_WAITING

        Thread.currentThread().interrupted(); // static, returns interrupted status and sets to true;
        Thread.currentThread().isInterrupted(); // non static, just returns status;
        Thread.currentThread().interrupt(); // interrupts the thread
        /*
        try {
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // clears interrupt
            e.printStackTrace();
        }
         */

        // Project Loom, fibers
        /*
        https://dzone.com/articles/why-do-we-need-threadcurrentthreadinterrupt-in-int
        As a rule of thumb, after catching  InterruptedException, do not forget to restore the interrupt by calling
        Thread.currentThread().interrupt(). //https://github.com/PacktPublishing/Java-Coding-Problems/tree/master/Chapter11/P213_ThreadInterruption
         */
    }

    private static void newState() {
        Thread t = new Thread(()-> System.out.println("New Thread"));
        System.out.println(t.getState()); // NEW
    }

    private static void runnableState() throws InterruptedException {
        Thread t = new Thread(()-> System.out.println("New Thread"));
        t.start();
        //TimeUnit.SECONDS.sleep(10); -> this will cause below to show TERMINATED
        System.out.println(t.getState()); // RUNNABLE
    }

    private static void blockedState() throws InterruptedException {
        class HeavyTask implements  Runnable{
            @Override
            public void run() {
               task();
            }

             synchronized void task(){
                System.out.println(Thread.currentThread().getName() + " is executing");
                while (true){
                    Thread.currentThread().getState();
                }
            }
        };
        Thread t1 = new Thread(new HeavyTask(), "Thread-1");
        Thread t2 = new Thread(new HeavyTask(), "Thread-2");
        t1.start();

        TimeUnit.SECONDS.sleep(10);
        t2.start();
        TimeUnit.SECONDS.sleep(10);
        System.out.println(t2.getState());
        //System.out.println(t2.getState());
    }


}
