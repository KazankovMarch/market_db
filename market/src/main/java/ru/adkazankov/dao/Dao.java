package ru.adkazankov.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    void insert(T entity) throws SQLException;

    void delete(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    List<T> getAll();


}
