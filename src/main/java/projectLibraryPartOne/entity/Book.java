package projectLibraryPartOne.entity;

public class Book {

    private int id;
    private String title;
    private Genre genre;

    public Book(int id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = Genre.valueOf(genre);
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genreId=" + genre +
                '}';
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = Genre.valueOf(genre);
    }
}
