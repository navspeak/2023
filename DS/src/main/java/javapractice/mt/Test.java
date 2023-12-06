package javapractice.mt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //int randomBetween8And11 = new Random().nextInt(11-8 +1) + 8;
//        ExecutorService service = Executors.newSingleThreadExecutor(); //LinkedBlockingQueue
//        ExecutorService service1 = Executors.newCachedThreadPool(); //SynchronusQueue
        ExecutorService executorThreadPool = Executors.newCachedThreadPool();
        List<Callable<String>> callableList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            callableList.add(new CountDownTask());
        }
//        List<Future<String>> futures = executorThreadPool.invokeAll(callableList);
//        // submit one more task to get the results
//        for (Future<String> future : futures){
//            final String result = future.get(); // serialize
//            System.out.println(result);
//        }

        ExecutorCompletionService executorCompletionService = new ExecutorCompletionService(executorThreadPool);
        List<Future<String>> futures = new ArrayList<>();
        for(Callable<String> task: callableList){
            Future<String> result = executorCompletionService.submit(task);
            futures.add(result);
        }
        executorThreadPool.shutdown();
//        for (int i = 0; i < 5; i++) {
//               Future<String> future = executorCompletionService.take();
//            System.out.println(future.get());
//        }
        while (!executorThreadPool.isTerminated()) {
            Future<String> future = executorCompletionService.take();
            System.out.println(future.get());
        }


        System.out.println("Main Ended");

    }
}


class CountDownTask implements Callable<String> {
    private static int count = 0;
    private int id;

    @Override
    public String call() {
        Long startTime = System.currentTimeMillis();
        //System.out.println("[" + Thread.currentThread().getName() + "] starting count down");
        for (int i=10; i>0; i--) {
            //System.out.println("<" + id + ">TICK TICK - " + i);

            try {
                TimeUnit.MILLISECONDS.sleep((long)(Math.random() * 1000)); // 0 to 1
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        System.out.println("***** <TASK-" + id + "> COMPLETED *****");
        Long timeTaken = (System.currentTimeMillis() - startTime)/1000;
        return "Task " + id + " took " + timeTaken + "secs";
    }

    public CountDownTask() {
        this.id = ++count;
    }
}