package javapractice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import static java.util.stream.Collectors.*;

public class _2CitiStream {
    public static void main(String[] args) {
        //TODO Write test here
        List<Author> testAuthors = new ArrayList<>();
        Book book = new Book("AA1");
        Book book1 = new Book("AA2");
        Book book2 = new Book("B3");
        Book book3 = new Book("AA4");
        Book book4 = new Book("B5");

        Author author1 = new Author("A1", Arrays.asList(book, book1));
        Author author2 = new Author("A2", Arrays.asList(book2, book3, book4));
        List<Author> authors = Arrays.asList(
                author1,
                author2
        );

        List<String> expected = getAuthors(authors, "AA");
        String expectedString = Arrays.toString(expected.toArray());
        System.out.println(expectedString);

        List<String> a = authors.stream()
                .filter(theAuthor -> theAuthor.getName().contains("A"))
                .map(Author::getName)
                .map(String::toUpperCase)
                .reduce(
                        new ArrayList<String>(),
                        (names, name) -> { // pure function
                            names.add(name); // mutable but has no side effect - Local mutability - so parallel stream will work
                            return names;
                        },
                        (names1, names2) -> {
                            names1.addAll(names2);
                            return names1;
                        }
                );

        List<String> a_readable = authors.stream()
                .filter(theAuthor -> theAuthor.getName().contains("A"))
                .map(Author::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toList());



        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result = letters
                .stream()
                .reduce("", (partialString, element) -> partialString + element);
        //assertThat(result).isEqualTo("abcde");

    }
    /**
     * Using java stream api return the name of authors whose books' (at least one book's) title contains (case insensitive) the given word <wordInBookTitle>
     * @param authors List of Authors
     * @param wordInBookTitle Word in book's title
     */
    static List<String> getAuthors(List<Author> authors, String wordInBookTitle) {
        //TODO implement in 1 line using stream api
//        return authors.stream()
//                .filter(author -> {
//                    List<Book> booksByAuthor = author.getBooks();
//                    boolean containsTitle = false;
//                    for (Book book: booksByAuthor){
//                        if (book.getTitle().toUpperCase().contains(wordInBookTitle)){
//                            containsTitle = true;
//                            break;
//                        }
//                    }
//                    return containsTitle;
//                })
//                .map(Author::getName)
//                .collect(Collectors.toList());
//    }

        return authors.stream()
                .map(_2CitiStream::getAuthNameToBookTitles)
                .filter(s -> s.toUpperCase().contains(wordInBookTitle.toUpperCase()))
                .map(s -> s.split(":")[0])
                .collect(Collectors.toList());


    }

    private static String getAuthNameToBookTitles(Author theAuthor) {
        return theAuthor.getName()
                + ":" + theAuthor.getBooks().stream()
                .map(Book::getTitle)
                .collect(Collectors.joining(","));
    }
}



class Author {
    List<Book> books;
    String name;
    public Author(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }
    public List<Book> getBooks() {
        return books;
    }
    public String getName() {
        return name;
    }
}

class Book {
    String title;
    public Book(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
