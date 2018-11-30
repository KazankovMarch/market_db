package ru.adkazankov.util;

import java.sql.*;

import static ru.adkazankov.Config.*;

public class DbWork {
    private static DbWork ourInstance = new DbWork();
    private Connection connection;


    public static DbWork getInstance() {
        return ourInstance;
    }

    private DbWork() {
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        }
        return connection;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }
    public Boolean execute(String sql) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        return statement.execute(sql);
    }
    public Integer executeUpdate(String sql) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql);
    }

}

