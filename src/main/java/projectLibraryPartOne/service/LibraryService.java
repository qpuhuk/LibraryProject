package projectLibraryPartOne.service;

import projectLibraryPartOne.entity.Book;

public interface LibraryService {
    void addBook(Book book);

    void getListBooks(LibraryServiceImpl library);

    void deleteBook(Book book);

    void editBook(Book book, String genre);
}
