package projectLibraryPartTwo.service;

import projectLibraryPartTwo.entity.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorService extends Author{

    public Author createAuthor(ResultSet resultSet) {
        Author author = new Author();
        try {
            author.setId(resultSet.getInt("Id"));
            author.setFirstName(resultSet.getString("FirstName"));
            author.setLastName(resultSet.getString("LastName"));
        } catch (SQLException s) {
            s.getStackTrace();
        }
        return author;
    }

}
