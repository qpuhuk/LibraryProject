package projectLibraryPartTwo.dao;

public class Queries {
    public static final String SELECT_ALL_BOOKS_BY_ID =
            "SELECT * FROM books JOIN author ON AuthorID = author.Id ORDER BY id_book";
    public static final String SELECT_FROM_BOOKS_BY_TITLE_SORTED_IN_ASC =
            "SELECT * FROM books JOIN author on AuthorID = author.Id ORDER BY title";
    public static final String SELECT_FROM_BOOKS_BY_TITLE_SORTED_IN_DESC =
            "SELECT * FROM books JOIN author on AuthorID = author.Id ORDER BY title DESC";
    public static final String SELECT_FROM_BOOKS_BY_DATE_SORTED_IN_DESC =
            "SELECT * FROM books JOIN author on AuthorID = author.Id ORDER BY dateCreated DESC";
    public static final String INSERT_FROM_BOOKS_BY_ID = "INSERT INTO books (Id_book, title, Genre, DateCreated, AuthorID) values (?,?,?,?,?)";
    public static final String DELETE_FROM_BOOKS_BY_ID = "DELETE FROM books WHERE id_book = ?";
    public static final String SELECT_ALL_AUTHORS = "SELECT * FROM library.author";
    public static final String INSERT_IN_AUTHOR = "INSERT INTO author values (?,?,?)";

}
