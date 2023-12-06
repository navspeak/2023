package javapractice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;


public class _2Stream {
    public static String getMyDefault() {
        System.out.println("Getting Default Value");
        return "Default Value";
    }
    @Data @AllArgsConstructor
    static class Person {
        String name;
        int age;
    }
    //https://www.youtube.com/watch?v=pGroX3gmeP8&t=5553s
    public static void collectorsDemo(){
        List<Person> people = createPeople();
        //https://www.baeldung.com/java-duplicate-keys-when-producing-map-using-stream#:~:text=Using%20the%20toMap()%20Method,values%20associated%20with%20duplicate%20keys.
        try {
            Map<String, Integer> collect = people.stream()
    //                .collect(Collectors.toMap(p -> p.getName(), p -> p.age))
                    .collect(toMap(Person::getName, Person::getAge));
        } catch (Exception e) { // will throw Illegal State exception coz of Dup key
            e.printStackTrace();
        }
        Map<String, List<Person>> c1= people.stream()
                        .collect(groupingBy(Person::getName));

        Map<String, Person> collect5 = people.stream()
                .collect(toMap(Person::getName,
                        Function.identity(),
                        (p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2));



        people.stream().map(Person::getAge).collect(toUnmodifiableList());
        people.stream()
                .filter(p->p.getAge() > 30)
                .map(Person::getName)
                .collect(joining(",","{","}"));

        //======================================================
        // Collector<T,A,R> T-> object u r dealing with, A -> accumulator, R -> collect
        // split in group of even and odd age
        Map<Boolean, List<Person>> collect1 = people.stream()
                .collect(partitioningBy(p -> p.getAge() % 2 == 0));
        // false -> Jack - 3, Jill - 11 ... | true -> Sara 20 Sara 22

        Map<Boolean, String> collect6 = people.stream()
                .collect(partitioningBy(p -> p.getAge() % 2 == 0,
                        mapping(Person::getName,
                                joining(":"))));
        //=========================================================

        Map<String, List<Person>> collect2 = people.stream()
                .collect(groupingBy(Person::getName));

        // if we want age of person not the person
        Map<String, List<Integer>> collect3 = people.stream()
                .collect(groupingBy(Person::getName,
                        mapping(Person::getAge, toList())));

        Map<String, Long> countByName = people.stream()
                .collect(groupingBy(Person::getName,
                        counting()));

        Map<String, Integer> collect4 = people.stream()
                .collect(groupingBy(Person::getName,
                        collectingAndThen(counting(), Long::intValue)));

        int sumOfAges = people.stream()
                .mapToInt(Person::getAge)
                .sum();
        OptionalInt min = people.stream()
                .mapToInt(Person::getAge)
                .min();
        System.out.println(min.orElse(-10));
        Optional<Person> oldest = people.stream()
                .collect(maxBy(comparing(Person::getAge)));
        System.out.println(oldest.map(Person::getName).orElse(""));

        String olderPerson = people.stream()
                .collect(collectingAndThen(
                        maxBy(comparing(Person::getAge)),
                        //p -> p.get().getName()
                        p->p.map(Person::getName).orElse("")
                ));
        System.out.println(olderPerson);

        Map<Integer, String> namesByAge = people.stream()
                .collect(groupingBy(Person::getAge,
                        mapping(Person::getName, joining(","))));
        System.out.println(namesByAge);

        Map<Integer, List<String>> collect = people.stream()
                .collect(groupingBy(Person::getAge,
                        mapping(Person::getName, filtering(name -> name.length() > 4, toList()))));

        Map<Integer, String> x = people.stream()
                .collect(groupingBy(Person::getAge,
                        mapping(Person::getName, filtering(name -> name.length() > 4, joining(",")))));
        System.out.println(x);

        // CollectingAndThen (Collector, Function)
        // GroupingAndThen (Collector, Function)


    }
    public static void flatMapDemo(){
        /*
        If you have a one-to-one, use map to go from Stream<T> => Stream<R>
        If you have a one-to-many, use map to go from Stream<T> => Stream<Collection<R>>
        If you have a one-to-many, use flatmap to go from Stream<T> => Stream<R>
         */
        List<List<Integer>> collect = IntStream.range(1, 3)
                .boxed()
                .map(e -> List.of(e - 1, e + 1))
                .collect(toList());


        System.out.println(Arrays.deepToString(collect.toArray())); //[[0, 2], [1, 3]]

        Set<Integer> collect1 = IntStream.range(0, 3)
                .boxed()
                .flatMap(e -> List.of(e - 1, e + 1).stream())
                .collect(toSet());
        System.out.println(collect1);


    }
    public static List<Person> createPeople(){
        return List.of(
                new Person("Sara", 20),
                new Person("Sara", 22),
                new Person("Nancy", 22),
                new Person("Bob", 20),
                new Person("Paula", 32),
                new Person("Paul", 32),
                new Person("Jack", 3),
                new Person("Bill", 71),
                new Person("Jill", 11)
                );
    }
    public static void main(String[] args) {
        flatMapDemo();
        collectorsDemo();
        String name = null;
        //Optional<String> str = Optional.of(name); // NPE
        Optional<String> str = Optional.ofNullable(name); // NPE
        str.ifPresentOrElse(System.out::println, ()-> System.out.println("Not Present"));
        System.out.println(str.isPresent()); //false
        System.out.println(str.isEmpty());//true
        System.out.println(str.orElse("DEFAULT")); // if str has not Null Value, returns that otherwise DEFAULT

        // as str wraps null both orElse and orElseGet calls getMyDefault
        System.out.println(str.orElse(getMyDefault()));
        System.out.println(str.orElseGet(() -> getMyDefault()));
        System.out.println("---------");
        str = Optional.of("str");
        System.out.println(str.orElse(getMyDefault())); // returns "str" but redundantly calls getMyDefault()
        System.out.println(str.orElseGet(() -> getMyDefault())); // does make redundant getMyDefaulr
        System.out.println("-------------");
        System.out.println(str.orElse("lll"));
        System.out.println(str.orElseGet(()-> "lll")); // This lambda will be not be called.

        Optional<Optional<String>> s1 = Optional
                .ofNullable("string")
                .map(s -> Optional.of("STRING"));
        Optional<String> s2 = Optional
                .ofNullable("string")
                .flatMap(s -> Optional.of("STRING"));

        List<Integer> list_ = new ArrayList();
        Optional<Integer> v_ = list_.isEmpty()? Optional.empty(): Optional.of(list_.get(0));
            v_.ifPresentOrElse(System.out::println, ()-> System.out.println("DEF"));

//
//        String nullName = null;
//
//        String name0 = Optional.ofNullable(nullName)
//                .orElseThrow(NullPointerException::new);
        //=============================

        Stream.of("dog", "cat")              // stream of 2 Strings
                // s= [dog, cat]
               // .map(s -> s.length())    // stream of 2 Integers: [3, 3]
                .mapToInt(s -> s.length())
                .forEach(System.out::println);

        Stream.of("dog", "cat")             // stream of 2 Strings
                .flatMapToInt(s -> s.chars())   // stream of 6 ints:      [d, o, g, c, a, t]
                .forEach(System.out::println);

        System.out.println("------------");
        IntStream chars_ = "abcABC".chars();//97,98,99,65,66,67
        chars_.forEach(System.out::println);

        Stream.of("dog", "cat")
                .flatMapToInt(s->s.chars()) //IntStream
                //.mapToObj(c->(char)c)
                .forEach(System.out::println);



        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");
        List<String> collect = myList.stream()
                .filter(s -> s.endsWith("1"))
                .map(c -> c.toUpperCase())
                .sorted()
                .collect(toList());
        System.out.println(Arrays.toString(collect.toArray()));

        Stream.of("a1", "a2", "a3").
                filter(s->s.startsWith("b")).// put c instead of a
                findFirst(). // returns Optional
                ifPresentOrElse(v->System.out.println("Found " + v), ()-> System.out.println("Nothing found"));
        /* ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)*/

        Deque<Integer> collect1 = IntStream.range(0, 1000)
                .map(Utils::divideBy5) //IntStream
                .boxed() //Stream<Integer)
                //.mapToObj(Integer::valueOf)//same as boxed
                .sorted(Comparator.reverseOrder())
                //.sorted(Comparator.comparingInt(Integer::intValue).reversed())
                .collect(toCollection(ArrayDeque::new));// LinkedList.new


        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        Stream<Integer> stream = Stream.of(4, 5);
        stream.collect(toCollection(() -> list));
        System.out.println(list);//12345

        List<City> cities = new ArrayList<>(Arrays.asList(
                new City("New Delhi", 33.5),
                new City("Mexico", 14),
                new City("New York", 13),
                new City("Dubai", 43),
                new City("London", 15),
                new City("Alaska", 1),
                new City("Kolkata", 30),
                new City("Sydney", 11),
                new City("Mexico", 14),
                new City("Dubai", 45)
        ));

        /* New Delhi: 1, Mexico: 2 */
        Map<String, List<City>> collect6 = cities.stream().collect(groupingBy(City::getName));
        collect6.forEach((k,v)-> System.out.println(k + ":" + Arrays.toString(v.toArray())));

        Map<String, Double> collect5 = cities.stream().collect(toMap(City::getName, City::getTemp));


        // get names of all cities with temp > 15
        Set<String> set = cities.stream()
                .filter(city-> city.temp > 10)
                .map(city->city.getName()) // <R> Stream<R>	map(Function<? super T,? extends R>mapper)
                .collect(toSet());
        Set<String> set1 = cities.stream()
                .filter(city-> city.temp > 10)
                .map(City::getName) // <R> Stream<R>	map(Function<? super T,? extends R>mapper)
                .collect(toSet());

//        int[] a = new int[]{1,2,3};
//        List<Integer> x = IntStream.of(a).mapToObj(Integer::valueOf).collect(Collectors.toList());
//        List<Integer> y = IntStream.of(a).boxed().collect(Collectors.toList());
        // Collector<T, ?, Map< K, U>> toMap(Function keyMapper, Function valueMapper):
        Map<String, Double> map = null; // will fail if duplicate found
        try {
            map = cities.stream().filter(c -> c.getTemp() > 10)
                    .collect(toMap(City::getName, City::getTemp));
            for (Map.Entry<String, Double> entry: map.entrySet()){
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
        // We can avoid and fix this issue by avoiding the key collision(in case of duplicate keys)
        // with the third argument that is BinaryOperator.


        Map<String, List<City>> collect4 = cities.stream()
                .collect(groupingBy(City::getName));

        Map<String, Long> collect3 = cities.stream()
                .collect(groupingBy(City::getName, counting()));

        Map<String, Integer> collect2 = cities.stream()
                .collect(groupingBy(c -> c.name,//City::getName
                        collectingAndThen(
                                counting(), f -> f.intValue()
                        )));

        System.out.println(cities.stream()
                .map(c -> "[Name = " + c.getName() + ", Temp =" + c.getTemp() + "]")
                .collect(joining(" , ")));


        //https://www.baeldung.com/java-flatten-nested-collections
        List<Integer> l1 = Arrays.asList(1,2,3);
        List<Integer> l2 = Arrays.asList(11,22,33);
        List<Integer> l3 = Arrays.asList(111,222,333);
        List<List<Integer>> l = Arrays.asList(l1,l2,l3);
        List<Integer> merged = l.stream().flatMap(Collection::stream).collect(toList());


        //Stream.of(Arrays.asList(1,2,3))

        "abc".chars().forEach(System.out::println); //97,98,99
        "abc".chars().map(x->(char)x).forEach(System.out::println); // 97,98,99
        "abc".chars().mapToObj(x->(char)x).forEach(System.out::println); //a,b.c
        //System.out.println(Character.forDigit('9', 10)); => 9 as char

        List<List<Integer>> lisOfIntegerList = new ArrayList<>();
        lisOfIntegerList.add(Arrays.asList(1,2,3));
        lisOfIntegerList.add(Arrays.asList(11,22,33));
        lisOfIntegerList.add(Arrays.asList(111,222,333));

        String a = lisOfIntegerList.stream().map(x->Arrays.toString(x.toArray())).
                collect(joining("|"));
        String b = lisOfIntegerList.stream().
                flatMap(Collection::stream)
                .map(x-> x.toString())
                .collect(joining("|"));

        System.out.println(a);//[1, 2, 3]|[11, 22, 33]|[111, 222, 333]
        System.out.println(b);//1|2|3|11|22|33|111|222|333

        //   <R> Stream<R> map(Function<? super T, ? extends R> mapper);
        Stream<String> stringStream = lisOfIntegerList.stream().map(x -> Arrays.toString(x.toArray()));
        //    <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
        Stream<Integer> integerStream = lisOfIntegerList.stream().flatMap(x->x.stream());

    }

    public static class Utils {
        public static int divideBy5(int value){
            return value / 5;
        }
    }

    @Data
    @AllArgsConstructor
    public static class City {
        private String name;
        private double temp;
    }
}