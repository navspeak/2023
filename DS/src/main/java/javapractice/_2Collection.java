package javapractice;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class _2Collection {

    public static void iteratorDemo (){
        List<Integer> listOfInt = new ArrayList<>();
        listOfInt.addAll(Arrays.asList(1,2,3));
        listOfInt.set(0, 0); // makes list as [0,2,3]
        Iterator<Integer> iterator = listOfInt.iterator();
        //listOfInt.add(4); -> doing this will cause CME at line 17 hasNext
        int index = 0;
        while (iterator.hasNext()){
            Integer next = iterator.next();
            System.out.println(next);
            if (next == 3)
                iterator.remove();
            //listOfInt.remove(index); // CME
            index++;
        }
        System.out.println(Arrays.toString(listOfInt.toArray()));

        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.addAll(Arrays.asList(1,2,3));
        copyOnWriteArrayList.add(4);
        Iterator<Integer> iterator1 = copyOnWriteArrayList.iterator();
        copyOnWriteArrayList.add(5); // this will not appear while printing
        index = 0;
        while (iterator1.hasNext()){
            Integer next = iterator1.next();
            System.out.println(next);
            if (next == 2)
                copyOnWriteArrayList.remove(index);
            //iterator1.remove(); // not supported
            index++;
        }
        System.out.println(Arrays.toString(copyOnWriteArrayList.toArray()));
//==========
        //https://www.digitalocean.com/community/tutorials/java-util-concurrentmodificationexception
        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("1", "1");
        myMap.put("2", "2");
        myMap.put("3", "3");

        Iterator<String> it1 = myMap.keySet().iterator();
        while (it1.hasNext()) {
            String key = it1.next();
            System.out.println("Map Value:" + myMap.get(key));
            if (key.equals("2")) {
                myMap.put("1", "4"); // No CME
                //myMap.put("4", "4"); CME as modcount is changed
            }
        }

        Deque<Integer> deque = new ArrayDeque<>();
        deque.add(0);
        deque.add(1);
        deque.add(2);
        deque.add(3);
        deque.remove(); // removes 0 = removeFirst() [1,2,3]
        deque.removeLast(); // remove 3 [1,2]
        System.out.println(deque.poll()); // 1 => [2]
        deque.add(5); // [2,5]
        deque.push(1); // [1,2,5]
        System.out.println(deque.pop()); // 1=> [2,5]
        System.out.println(Arrays.toString(deque.toArray()));


    }

    public static void main(String[] args) {
        iteratorDemo();
        Map<Integer, Integer> myHashMap = new HashMap<>();
        // myHashMap has integers for both keys and values

        System.out.println(myHashMap.put(4, 83)); // null
        System.out.println(myHashMap.put(4, 83)); // 83
        System.out.println(myHashMap.get(4)); // Prints 83
        System.out.println(myHashMap.containsKey(4)); // Prints true
        System.out.println(myHashMap.containsKey(854)); // Prints false

        System.out.println(myHashMap.put(8, 327)); //prints null
        System.out.println(myHashMap.put(8, 327)); //prints 327
        myHashMap.put(45, 82523);

        for (int key: myHashMap.keySet()) {
            System.out.println(String.format("%d: %d", key, myHashMap.get(key)));
        }

        Integer a = Integer.valueOf(1);
        Double b = Double.valueOf(1);
        a.equals(b);
        Set<MyClass> s = new HashSet<>();
        System.out.println(s.add(new MyClass("Nav", "jjj")));
        System.out.println(s.add(new MyClass("Navn", "jjjh")));
        System.out.println(s.add(new MyClass("Navn", "jjjjj")));
        System.out.println(s.size());
        System.out.println(s);
        System.out.println(MyClass.equalsCalled); // 0 if hashcode implemented properly, otherwise 3

    }


    @AllArgsConstructor
    @ToString
    static class MyClass {
        String name;
        String xyz;
        static Integer equalsCalled = 0;

        @Override
        public boolean equals(Object o) {
            equalsCalled++;
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyClass myClass = (MyClass) o;
            return Objects.equals(name, myClass.name) && Objects.equals(xyz, myClass.xyz);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, xyz);
        }
    }
}
/*
https://bulldogjob.com/readme/how-not-to-break-your-app-with-hashcode-and-equals

hashCode() and equals() contract
The basic rule of the contract states that if two objects are equal to each other based on equals() method, then the hash code must be the same,
but if the hash code is the same, then equals() can return false.
To ensure that the contract is fulfilled, the methods should use the same fields and always be overridden together.
- If hashcode is implemented correctly
   in set.put
 */