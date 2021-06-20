package projectLibraryPartTwo.service;

import projectLibraryPartTwo.entity.Book;

import java.sql.ResultSet;

public interface BookService {
    Book createBook(ResultSet resultSet);
}
