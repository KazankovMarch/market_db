package ru.adkazankov.dao;

import ru.adkazankov.util.DbWork;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class DaoImpl<T> implements Dao<T> {

    public static DbWork dbWork = DbWork.getInstance();

    public ArrayList<T> getAll() {
        try {
            ResultSet resultSet = executeQuery("SELECT * FROM "+getTable());
            ArrayList<T> arrayList = new ArrayList<T>();
            while (resultSet.next()){
                arrayList.add(toObj(resultSet));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        return dbWork.executeQuery(sql);
    }
    public Boolean execute(String sql) throws SQLException {
        return dbWork.execute(sql);
    }
    public Integer executeUpdate(String sql) throws SQLException {
        return dbWork.executeUpdate(sql);
    }

    public abstract T toObj(ResultSet resultSet);

    protected abstract String getTable();

}
