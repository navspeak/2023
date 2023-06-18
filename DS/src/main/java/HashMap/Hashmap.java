package HashMap;

import java.util.HashMap;
import java.util.Map;

public class Hashmap {
    public static void main(String[] args) {
        Map<Integer, Integer> myHashMap = new HashMap<>();
        // myHashMap has integers for both keys and values

        myHashMap.put(4, 83);
        System.out.println(myHashMap.get(4)); // Prints 83
        System.out.println(myHashMap.containsKey(4)); // Prints true
        System.out.println(myHashMap.containsKey(854)); // Prints false

        System.out.println(myHashMap.put(8, 327)); //prints null
        System.out.println(myHashMap.put(8, 327)); //prints 327
        myHashMap.put(45, 82523);

        for (int key: myHashMap.keySet()) {
            System.out.println(String.format("%d: %d", key, myHashMap.get(key)));
        }
    }
}
