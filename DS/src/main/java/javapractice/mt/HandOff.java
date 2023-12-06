package javapractice.mt;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HandOff {
    AtomicInteger sharedState = new AtomicInteger();
    SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
    CountDownLatch countDownLatch = new CountDownLatch(1);
    Runnable producerCountdownlatchVer = () -> {
        Integer producedElement = ThreadLocalRandom
                .current()
                .nextInt();
        sharedState.set(producedElement);
        System.out.println("produced " + producedElement);
        countDownLatch.countDown();
    };
    Runnable consumerCountDownLatchVer = () -> {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var consumedElement = sharedState.get();
        System.out.println("Consumed " + consumedElement);
        countDownLatch.countDown();
    };

    Runnable producerSynQ = () -> {
        Integer producedElement = ThreadLocalRandom
                .current()
                .nextInt();
        try {
            synchronousQueue.put(producedElement);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("PProduced " + producedElement);
    };
    Runnable consumerSynQ = () -> {
        Integer consumedElement = null;
        try {
            Thread.sleep(1000*10);
            consumedElement = synchronousQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CConsumed " + consumedElement);
    };

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        HandOff handOff = new HandOff();
        service.execute(handOff.producerCountdownlatchVer);
        service.execute(handOff.consumerCountDownLatchVer);

        service.execute(handOff.producerSynQ);
        service.execute(handOff.consumerSynQ);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
    }
}
/*
produced -2138103559
Consumed -2138103559
CConsumed 250942300 - this comes first because put() bloccks till take() is called
PProduced 250942300
 */
