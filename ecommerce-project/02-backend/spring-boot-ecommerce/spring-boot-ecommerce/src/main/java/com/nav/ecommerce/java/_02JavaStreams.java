package com.nav.ecommerce.java;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class _02JavaStreams {
    public static String getMyDefault() {
        System.out.println("Getting Default Value");
        return "Default Value";
    }
    public static void main(String[] args) {
        String name = null;
        //Optional<String> str = Optional.of(name); // NPE
        Optional<String> str = Optional.ofNullable(name); // NPE
        str.ifPresentOrElse(System.out::println, ()-> System.out.println("Not Present"));
        System.out.println(str.isPresent()); //false
        System.out.println(str.isEmpty());//true
        System.out.println(str.orElse("DEFAULT")); // if str has not Null Value, returns that otherwise DEFAULT

        // as str wraps numm both orElse and orElseGet calls getMyDefault
        System.out.println(str.orElse(getMyDefault()));
        System.out.println(str.orElseGet(() -> getMyDefault()));
        System.out.println("---------");
        str = Optional.of("str");
        System.out.println(str.orElse(getMyDefault())); // returns "str" but redundantly calls getMyDefault()
        System.out.println(str.orElseGet(() -> getMyDefault())); // does make redundant getMyDefaulr
        System.out.println("-------------");
        System.out.println(str.orElse("lll"));
        System.out.println(str.orElseGet(()->"lll"));

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
                .map(s -> s.length())    // stream of 2 Integers: [3, 3]
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
                .collect(Collectors.toList());
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
                .collect(Collectors.toCollection(ArrayDeque::new));// LinkedList.new


        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        Stream<Integer> stream = Stream.of(4, 5);
        stream.collect(Collectors.toCollection(() -> list));
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
        Map<String, List<City>> collect6 = cities.stream().collect(Collectors.groupingBy(City::getName));
        collect6.forEach((k,v)-> System.out.println(k + ":" + Arrays.toString(v.toArray())));

        Map<String, Double> collect5 = cities.stream().collect(Collectors.toMap(City::getName, City::getTemp));


        // get names of all cities with temp > 15
        Set<String> set = cities.stream()
                .filter(city-> city.temp > 10)
                .map(city->city.getName()) // <R> Stream<R>	map(Function<? super T,? extends R>mapper)
                .collect(Collectors.toSet());
        Set<String> set1 = cities.stream()
                .filter(city-> city.temp > 10)
                .map(City::getName) // <R> Stream<R>	map(Function<? super T,? extends R>mapper)
                .collect(Collectors.toSet());

//        int[] a = new int[]{1,2,3};
//        List<Integer> x = IntStream.of(a).mapToObj(Integer::valueOf).collect(Collectors.toList());
//        List<Integer> y = IntStream.of(a).boxed().collect(Collectors.toList());
        // Collector<T, ?, Map< K, U>> toMap(Function keyMapper, Function valueMapper):
        Map<String, Double> map = null; // will fail if duplicate found
        try {
            map = cities.stream().filter(c -> c.getTemp() > 10)
                    .collect(Collectors.toMap(City::getName, City::getTemp));
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
                .collect(Collectors.groupingBy(City::getName));

        Map<String, Long> collect3 = cities.stream()
                .collect(Collectors.groupingBy(City::getName, Collectors.counting()));

        Map<String, Integer> collect2 = cities.stream()
                .collect(Collectors.groupingBy(c -> c.name,//City::getName
                        Collectors.collectingAndThen(
                                Collectors.counting(), f -> f.intValue()
                        )));

        System.out.println(cities.stream()
                .map(c -> "[Name = " + c.getName() + ", Temp =" + c.getTemp() + "]")
                .collect(Collectors.joining(" , ")));


        //https://www.baeldung.com/java-flatten-nested-collections
        List<Integer> l1 = Arrays.asList(1,2,3);
        List<Integer> l2 = Arrays.asList(11,22,33);
        List<Integer> l3 = Arrays.asList(111,222,333);
        List<List<Integer>> l = Arrays.asList(l1,l2,l3);
        List<Integer> merged = l.stream().flatMap(Collection::stream).collect(Collectors.toList());


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
                collect(Collectors.joining("|"));
        String b = lisOfIntegerList.stream().
                flatMap(Collection::stream)
                .map(x-> x.toString())
                .collect(Collectors.joining("|"));

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