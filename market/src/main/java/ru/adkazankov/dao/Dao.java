package ru.adkazankov.dao;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    Class getTClass();

    void insert(T entity) throws SQLException;

    void delete(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    List<T> getAll();

    void exportTo(String path, String delimiter) throws SQLException;

    void importFrom(String path, String delimiter) throws SQLException;

}
