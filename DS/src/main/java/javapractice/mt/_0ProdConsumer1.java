package javapractice.mt;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.*;

public class _0ProdConsumer1 {
    //https://www.concretepage.com/java/reentrantlock-java-example-with-lock-unlock-trylock-lockinterruptibly-isheldbycurrentthread-and-getholdcount
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        //ConcurrentHashMap -https://www.javainuse.com/java/javaConcurrentHashMap
        // https://stackoverflow.com/questions/16105554/concurrenthashmap-read-and-write-locks
        //Collections.synchronizedList() is still fail fast - throws ConcurrentModificationException
        //Executors.newSingleThreadExecutor() - backed by a LinkedBlockingQueue. Helps run tasks in order
        // Executors.newScheduledThreadPool() - DelayedWorkQueue
        // https://youtu.be/Dma_NmOrp1c?t=351
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        Runnable producer = ()-> {
            Integer producedElement = ThreadLocalRandom
                    .current()
                    .nextInt();
            try {
                queue.put(producedElement); //Adds the specified element to this queue, waiting if necessary for another thread to receive
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        Runnable consumer = () -> {
            try {
                Integer consumedElement = queue.take();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        service.execute(producer);
        service.execute(consumer);

        service.awaitTermination(500, TimeUnit.MILLISECONDS);
        service.shutdown(); // https://youtu.be/Dma_NmOrp1c?t=764
        /*
 Attempts to stop all actively executing tasks, halts the processing of waiting tasks, and returns a list of the tasks
 that were awaiting execution.
This method does not wait for actively executing tasks to terminate. Use awaitTermination to do that.
There are no guarantees beyond best-effort attempts to stop processing actively executing tasks. For example, typical implementations will cancel via Thread.interrupt, so any task that fails to respond to interrupts may never terminate.
Returns:
list of tasks that never commenced execution
         */
        List<Runnable> runnables = service.shutdownNow();
        //service.isShutdown();
        /*
 returns true if all tasks have completed following shut down. Note that isTerminated is never true unless either shutdown or
 shutdownNow was called first.
Returns:
true if all tasks have completed following shut down
         */
        service.isTerminated();

        /**/
        ExecutorService svc = new ThreadPoolExecutor(10,100,120, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(300), new CustomRejectionHandler());
    }

    static class CustomRejectionHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        }
    }
}



