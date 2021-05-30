package ProjectLibraryPartOne;

import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {

        Library library = new Library();
        Book book = new Book(2, "English", "ACTION");
        Book book1 = new Book(1, "Burda", "FANTASTIC");
        Book book2 = new Book(4, "Russian", "CHILDREN_BOOK");
        Book book3 = new Book(3, "Boroda", "COMEDY");
        library.addBook(book);
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.getListBooks(library);  // просто список

        library.listBooks.sort(new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        });
        library.getListBooks(library);  // отсортированный список по title

        library.deleteBook(book1);
        library.getListBooks(library);  //удаление книги

        Collections.reverse(library.listBooks);
        library.getListBooks(library);   // в обратном порядке по добавлению

        library.editBook(book, "COMEDY");
        library.getListBooks(library);   // редактируем одну книгу
    }
}
