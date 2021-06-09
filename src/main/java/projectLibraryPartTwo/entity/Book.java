package projectLibraryPartTwo.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Book {
    private int id;
    private String title;
    private Genre genre;
    private LocalDate dateCreated;
    private Author author;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return String.valueOf(genre);
    }

    public void setGenre(String genre) {
        this.genre = Genre.valueOf(genre);
    }

    public String getDateCreated() {
        return String.valueOf(dateCreated);
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getAuthorID() {
        return author.getId();
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                ", dateCreated=" + dateCreated +
                ", author=" + author +
                '}';
    }
}
