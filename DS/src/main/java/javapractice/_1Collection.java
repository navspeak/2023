package javapractice;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class _1Collection {
    public static void main(String[] args) {
        //https://www.baeldung.com/concurrenthashmap-reading-and-writing

        Deque<Integer> deque = new ArrayDeque<>(); //LinkedList<>();
        /* queue */
        deque.add(1); //addLast, can use offer and poll
        deque.add(2);
        deque.add(3);
        deque.add(4);
        deque.addFirst(0);
        int x = deque.poll(); // returns null if nothing
        int y = deque.remove(); //removeFirst = 0, throws no such element
        deque.isEmpty();
        System.out.println(x);
        deque.stream().forEach(System.out::println);

        Deque<Integer> stack = new ArrayDeque<>(); //LinkedList<>();
        stack.push(0); //addFirst
        stack.push(1);
        System.out.println(stack.peek()); // 0 getFirst
        stack.stream().forEach(System.out::println);
        System.out.println(stack.pop());//0 removeFirst
        String a = "a";
        System.out.println(Character.isUpperCase('a'));

        SortedMap<Integer, String> sortedMap = new TreeMap<>();
        sortedMap.put(1, "One");
        sortedMap.put(10, "Ten");
        sortedMap.put(5, "five");
        sortedMap.put(6, "Six");
        sortedMap.entrySet().stream().forEach(e-> System.out.println(e.getKey() + "->" + e.getValue()));//1,5,6,10
        sortedMap.headMap(5).entrySet().stream().forEach(e-> System.out.println(e.getKey() + "->" + e.getValue())); //only 1 strictly less than 5
        System.out.println(((TreeMap) sortedMap).floorEntry(5).getValue()); // five for (1->"five) strictly less than
        System.out.println(((TreeMap) sortedMap).ceilingEntry(5).getValue()); // five for (1->"five) >=5


        List<String> myList = new ArrayList<>(List.of(new String[]{"1", "2", "3", "4", "5"}));
        Iterator<String> it = myList.iterator();
        while (it.hasNext()) {
            String value = it.next(); // Throws CME here after remove
            System.out.println("List Value:" + value);
            if (value.equals("3")) {
                //myList.remove(value); - Don't do this because next it.next() will throw CME
                // Option it.remove(), for loop, COWArrayList
                it.remove(); // Use this
            }
        }

        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("1", "1");
        myMap.put("2", "2");
        myMap.put("3", "3");

        Iterator<String> it1 = myMap.keySet().iterator();
        while (it1.hasNext()) {
            String key = it1.next(); //Key Iterator
            System.out.println("Map Value:" + myMap.get(key));
            if (key.equals("2")) {
                myMap.put("1", "4"); // no error
                myMap.remove("4"); // no error
            }
        }

        Iterator<Map.Entry<String, String>> iterator = myMap.entrySet().iterator(); //Entry Iterator
        while(iterator.hasNext()){
            Map.Entry next = iterator.next();
            if (next.getKey().equals("2")){
                myMap.remove("2"); // Throws CME
            }
        }


        List<String> myList2 = new CopyOnWriteArrayList<>(new String[]{"1","2","3","4","5"});

        Iterator<String> it2 = myList.iterator();
        while (it.hasNext()) {
            String value = it.next();
            System.out.println("List Value:" + value);
            if (value.equals("3")) {
                myList.remove("4"); // works in COWItr but itr.remove() will throw unsupported op exception
                myList.add("6");
                myList.add("7");
            }
        }
        System.out.println("List Size:" + myList.size());

        /*
       You will get ConcurrentModificationException if you will try to modify the structure of the original list
       with subList. Let’s see this with a simple example.
         */

        List<String> names = new ArrayList<>();
        names.add("Java");
        names.add("PHP");
        names.add("SQL");
        names.add("Angular 2");

        List<String> first2Names = names.subList(0, 2);

        System.out.println(names + " , " + first2Names);

        names.set(1, "JavaScript");
        // check the output below. :)
        System.out.println(names + " , " + first2Names);

        // Let's modify the list size and get ConcurrentModificationException
        names.add("NodeJS");
        System.out.println(names + " , " + first2Names); // this line throws exception
    }

    private static void listCOWOperations() {
        // TimeComplexity of an ArrayList.add() - O(1) : copies at end (O(n) when need t grow in size
        // CopyOnWriteArrayList.add - O(n) as this method is sync'd & adds to a copy of original array (see impl)
        List<Integer> numbers
                = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});
        Iterator<Integer> iterator = numbers.iterator();
        numbers.add(10);

        // For arrayList the below will give concurrent modification exception
        iterator.forEachRemaining(System.out::println);

        // For COWArrayList: (For ArrayListIterator remove is supported.)
        // 1,3,5,8 only because Iterator gets immutable copy of original list which did not have 10 added later
        // The CopyOnWriteArrayList was created to allow for the possibility of safe iterating over elements even when
        // the underlying list gets modified. Because of the copying mechanism, the remove() operation on the returned
        // Iterator is not permitted – resulting with UnsupportedOperationException:
        Iterator<Integer> iterator2 = numbers.iterator();
//        while (iterator2.hasNext()){
//            iterator2.remove(); //UnsupportedOperationException
//        }
    }
}
// @Autowired with required = false
// circular dependency spring - @Lazy
interface A {
    void foo();
    default void bar(){
        System.out.println("A's bar");
    }

}

interface B {
    void foo();
    default void bar(){
        System.out.println("A's bar");
    }

}

class AB implements A,B{

    public void foo() {
        System.out.println("kkk");
    }

    @Override
    public void bar() {
        A.super.bar();
    }

}