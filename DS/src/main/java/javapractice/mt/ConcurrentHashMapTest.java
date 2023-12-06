package javapractice.mt;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static java.util.Arrays.asList;
//https://www.baeldung.com/concurrenthashmap-reading-and-writing
public class ConcurrentHashMapTest {
    public ConcurrentHashMapTest() {
        frequencyMap.put(0, 0);
        frequencyMap.put(1, 0);
        frequencyMap.put(2, 0);
    }

    private Map<Integer, Integer> frequencyMap = new ConcurrentHashMap<>();
    private static void sleep(int timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //https://www.javainuse.com/java/javaConcurrentHashMap#:~:text=Depending%20upon%20the%20level%20of,Each%20Segment%20behaves%20independently.
    public void givenOneThreadIsWriting_whenAnotherThreadReads_thenGetCorrectValue() throws Exception {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(3);

        Runnable writeAfter1Sec = () -> frequencyMap.computeIfPresent(1, (k, v) -> {
            sleep(1);
            return frequencyMap.get(k) + 1;
        });

        Callable<Integer> readNow = () -> frequencyMap.get(1);
        Callable<Integer> readAfter1001Ms = () -> {
            TimeUnit.MILLISECONDS.sleep(1001);
            return frequencyMap.get(1);
        };

        threadExecutor.submit(writeAfter1Sec);
        List<Future<Integer>> results= threadExecutor.invokeAll(asList(readNow, readAfter1001Ms));

        System.out.println(results.get(0).get());
        System.out.println(results.get(1).get());


        if (threadExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
            threadExecutor.shutdown();
        }
    }
}
