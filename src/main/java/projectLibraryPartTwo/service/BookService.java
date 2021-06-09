package projectLibraryPartTwo.service;

import projectLibraryPartTwo.entity.Author;
import projectLibraryPartTwo.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookService extends Book{

    public Book createBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        Author author = new Author();
        try {
            author.setId(resultSet.getInt("id"));
            author.setFirstName(resultSet.getString("firstname"));
            author.setLastName(resultSet.getString("lastname"));
            book.setId(resultSet.getInt("id_book"));
            book.setTitle(resultSet.getString("title"));
            book.setGenre(resultSet.getString("genre"));
            book.setDateCreated(resultSet.getDate("DateCreated").toLocalDate());
            book.setAuthor(author);
        } catch (SQLException s) {
            s.getStackTrace();
        }
        return book;
    }
}
