package ProjectLibraryPartTwo.DAO;

import ProjectLibraryPartTwo.Connection.ConnectorBD;
import ProjectLibraryPartTwo.Entity.Author;
import ProjectLibraryPartTwo.Entity.Book;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
    public List<Author> getAllAuthors() {
        List<Author> listAllAuthors = new ArrayList<>();
        try (Connection connection = ConnectorBD.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Queries.SELECT_ALL_AUTHORS);
            Author author = new Author();
            while (resultSet.next()) {
                listAllAuthors.add(author.createAuthor(resultSet));
            }
            statement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listAllAuthors;
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
            List<Author> allAuthors = getAllAuthors();
            PreparedStatement preparedStatement = null;
            boolean checkAuthor = allAuthors.stream().noneMatch(author -> (author.getId() == idAuthor));
            if (checkAuthor) {
                int resultAuthor;
                Scanner scanner = new Scanner(System.in);
                System.out.println("Автора с таким ID не существует, введите его имя и фамилию ->");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Введите имя ->");
                String name = scanner.nextLine();
                System.out.println("Введите фамилию ->");
                String serName = scanner.nextLine();
                preparedStatement = connection.prepareStatement(Queries.INSERT_IN_AUTHOR);
                preparedStatement.setInt(1, idAuthor);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, serName);
                resultAuthor = preparedStatement.executeUpdate();
                if (resultAuthor != 0) {
                    System.out.println("Новый автор добавлен!!");
                }
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            result = statement.executeUpdate();
            statement.close();
            connection.commit();
        } catch (SQLException | InterruptedException throwables) {
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
        System.out.println("Ожидайте, идёт обработка базы данных...");
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
