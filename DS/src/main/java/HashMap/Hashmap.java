package HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.*;

public class Hashmap {
    public static void main(String[] args) {
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