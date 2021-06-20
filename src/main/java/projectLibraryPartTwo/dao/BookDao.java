package projectLibraryPartTwo.dao;

import projectLibraryPartTwo.entity.Author;
import projectLibraryPartTwo.entity.Book;

import java.util.List;

public interface BookDao {

    void writeAllBooks(int choice);

    boolean addBookByIdTitleGenre(int id, String title, String genre, int idAuthor, String localDate);

    boolean deleteBookById(int id);

    boolean correctBookByIdNewTitleNewGenre(int id, String title, String genre);

    List<Book> getAllBooks();

    List<Author> getAllAuthors();
}
