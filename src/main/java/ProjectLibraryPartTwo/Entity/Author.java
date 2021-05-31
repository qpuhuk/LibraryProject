package ProjectLibraryPartTwo.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Author {
    private int id;
    private String firstName;
    private String lastName;

    public Author() {
    }

    public Author(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
