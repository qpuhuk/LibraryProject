package projectLibraryPartOne.service;

import projectLibraryPartOne.entity.Book;

import java.util.ArrayList;

public class LibraryService {

    private ArrayList<Book> listBooks;

    public LibraryService() {
        this.listBooks = new ArrayList<>();
    }

    public ArrayList<Book> getListBooks() {
        return listBooks;
    }

    public void setListBooks(ArrayList<Book> listBooks) {
        this.listBooks = listBooks;
    }

    @Override
    public String toString() {
        return "Library{" +
                "listBooks=" + listBooks +
                '}';
    }

    public void addBook(Book book) {
        if (listBooks.isEmpty()) {
            listBooks.add(book);
        } else {
            int temp = 0;
            for (Book listBook : listBooks) {
                if (listBook.getId() == book.getId()) {
                    temp++;
                }
                if (temp > 0) {
                    System.out.println("A book with this id already exists");
                    break;
                }
            }
            if (temp == 0) {
                listBooks.add(book);
            }
        }
    }

    public void getListBooks(LibraryService library) {
        if (listBooks.isEmpty()) {
            System.out.println("The list of books is empty");
        } else {
            System.out.println("All list of library : " + library);
        }
    }

    public void deleteBook(Book book) {
        if (listBooks.isEmpty()) {
            System.out.println("List books is empty. Delete is impossible");
        } else {
            int temp = 0;
            for (Book bookTemp : listBooks) {
                if (bookTemp.getId() == book.getId()) {
                    temp++;
                }
                if (temp > 0) {
                    listBooks.remove(book);
                    break;
                }
            }
            if (temp == 0) {
                System.out.println("There is no such book in the database. Delete is impossible");
            }
        }
    }

    public void editBook(Book book, String genre) {
        if (!listBooks.contains(book)) {
            System.out.println("There is no such book in the database. Edit book impossible");
        } else {
            deleteBook(book);
            book.setGenre(genre);
            addBook(book);
        }
    }
}
