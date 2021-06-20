package projectLibraryPartTwo.service;

import projectLibraryPartTwo.entity.Author;

import java.sql.ResultSet;

public interface AuthorService {
    Author createAuthor(ResultSet resultSet);
}
