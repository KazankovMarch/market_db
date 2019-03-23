package ru.adkazankov.dao;

import ru.adkazankov.util.DbWork;

import java.io.*;
import java.lang.reflect.Field;
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

    @Override
    public void exportTo(String path, String delimiter) throws SQLException {
        try(
                BufferedWriter writer = new BufferedWriter(new FileWriter(path))
                ) {


            List<T> list = getAll();
            for(T t : list){
                writer.write(toStringSQL(t, delimiter));
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        execute("COPY "+getTable()+" TO '"+path+"' DELIMITER '"+delimiter+"'");
    }

    protected String toStringSQL(T t, String delimiter){
        System.out.println("toStrSQL " + t.toString() +" "+delimiter);
        try {
            StringBuilder builder = new StringBuilder();
            for(Field f : t.getClass().getDeclaredFields()){
                f.setAccessible(true);
                System.out.println(f.toString());
                builder.append(f.get(t).toString());
                builder.append(delimiter);
            }
            System.out.println(builder.toString());
            builder.setLength(builder.length() - 1); //delete last character (,)
            return builder.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void importFrom(String path, String delimiter) throws SQLException {

        try(
                BufferedReader reader = new BufferedReader(new FileReader(path))
        ) {
            String line;
            while ((line = reader.readLine()) != null && line.length()>2){
                System.out.println(line);
                T t = fromStringSQL(line, delimiter);
                insert(t);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        execute("COPY "+getTable()+" FROM '"+path+"' DELIMITER '"+delimiter+"");
    }

    protected abstract T fromStringSQL(String line, String delimiter);
}
