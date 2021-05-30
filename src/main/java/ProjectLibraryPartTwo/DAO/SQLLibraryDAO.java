package ProjectLibraryPartTwo.DAO;

import ProjectLibraryPartTwo.Connection.ConnectorBD;
import ProjectLibraryPartTwo.Entity.Book;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLLibraryDAO implements LibraryDAO {

    @Override
    public List<Book> getAllBooks() {
        List<Book> listAllBooks = new ArrayList<>();
        try (Connection connection = ConnectorBD.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Queries.SELECT_ALL_BOOKS_BY_ID);
            Book book = new Book();
            while (resultSet.next()) {
                listAllBooks.add(book.createBook(resultSet));
            }
            statement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listAllBooks;
    }

    @Override
    public void writeAllBooks(int choice) {
        List<Book> listAllBooks = new ArrayList<>();
        List<Book> newList = getAllBooks();
        if (newList.isEmpty()) {
            System.out.println("Библиотека пуста");
        }
        try (Connection connection = ConnectorBD.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            switch (choice) {
                case 1:
                    resultSet = statement.executeQuery(Queries.SELECT_FROM_BOOKS_BY_TITLE_SORTED_IN_ASC);
                    break;
                case 2:
                    resultSet = statement.executeQuery(Queries.SELECT_FROM_BOOKS_BY_TITLE_SORTED_IN_DESC);
                    break;
                case 3:
                    resultSet = statement.executeQuery(Queries.SELECT_FROM_BOOKS_BY_DATE_SORTED_IN_DESC);
                    break;
                default:
                    System.out.println("******************************\nНеверное значение");
            }
            if (resultSet != null) {
                Book book = new Book();
                while (resultSet.next()) {
                    listAllBooks.add(book.createBook(resultSet));
                }
                listAllBooks.forEach(System.out::println);
                statement.close();
                resultSet.close();
            } else {
                System.out.println("******************************\nЗапрос невозможен");
                statement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean addBookByIdTitleGenre(int id, String title, String genre, int idAuthor, String date) {
        List<Book> newList = getAllBooks();
        int result = 0;
        for (Book book : newList) {
            if (book.getId() == id) {
                System.out.println("Книга с таким ID уже существует");
                return false;
            }
        }
        try (Connection connection = ConnectorBD.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(Queries.INSERT_FROM_BOOKS_BY_ID);
            statement.setInt(1, id);
            statement.setString(2, title);
            statement.setString(3, Book.Genre.valueOf(genre).name());
            statement.setInt(5, idAuthor);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
            statement.setDate(4, Date.valueOf(localDate));
            result = statement.executeUpdate();
            connection.commit();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public boolean deleteBookById(int id) {
        List<Book> allBookInLibrary = getAllBooks();
        List<Book> newList = getAllBooks();
        if (newList.isEmpty()) {
            System.out.println("Библиотека пуста");
            return false;
        }
        int result = 0;
        for (Book book : allBookInLibrary) {
            if (book.getId() == id) {
                try (Connection connection = ConnectorBD.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(Queries.DELETE_FROM_BOOKS_BY_ID);
                    preparedStatement.setInt(1, id);
                    result = preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return result == 1;
    }

    @Override
    public boolean correctBookByIdNewTitleNewGenre(int id, String title, String genre) {
        List<Book> newList = getAllBooks();
        if (newList.isEmpty()) {
            System.out.println("Библиотека пуста");
            return false;
        }
        boolean exist = newList.stream().anyMatch(b -> (b.getId() == id));
        if (!exist) {
            System.out.println("Такой книги нет в базе данных, попробуйте другой ID ->");
            return false;
        }
        Book book = newList.stream().filter(book1 -> book1.getId() == id).findAny().get();
        boolean delete = deleteBookById(id);
        if (delete) {
            boolean result = addBookByIdTitleGenre(book.getId(), title, genre,
                    book.getAuthorID(), book.getDateCreated());
            if (result) {
                return true;
            } else {
                System.out.println("Что-то пошло не так.....");
                return false;
            }
        } else {
            System.out.println("Что-то пошло не так...");
            return false;
        }
    }
}
