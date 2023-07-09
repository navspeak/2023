
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
public class Solution {
    public static void main(String[] args) {
        //TODO Write test here
        List<Author> testAuthors = new ArrayList<>();
        Book book = new Book("AA1");
        Book book1 = new Book("AA2");
        Book book2 = new Book("B3");
        Book book3 = new Book("AA4");
        Book book4 = new Book("B5");

        List<Author> authors = Arrays.asList(
                new Author("A1",Arrays.asList(book, book1)),
                new Author("A2",Arrays.asList(book2, book3))
        );

        List<String> expected = getAuthors(authors, "AA");
        String expectedString = Arrays.toString(expected.toArray());
        System.out.println(expectedString);

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
                    .filter(author -> {
        List<Book> booksByAuthor = author.getBooks();
        boolean containsTitle = false;
        for (Book book: booksByAuthor){
            if (book.getTitle().toUpperCase().contains(wordInBookTitle)){
                containsTitle = true;
                break;
            }
        }
        return containsTitle;
    })
                .map(Author::getName)
                .collect(Collectors.toList());
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
