package ru.adkazankov.dao;

import ru.adkazankov.domain.Provider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProviderDao extends DaoImpl<Provider> {

    private static final String NAME = "Поставщик";
    private static final String TYPE = "Тип";
    private static final String TABLE = "Поставщик";


    @Override
    public Provider toObj(ResultSet resultSet) {
        try {
            Provider provider = new Provider();
            provider.setName(resultSet.getString(NAME));
            provider.setType(resultSet.getString(TYPE));
            return provider;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getTable() {
        return TABLE;
    }

    @Override
    protected Provider fromStringSQL(String line, String delimiter) {
        Provider provider = new Provider();
        String[] fields = line.split(delimiter);
        provider.setType(fields[0]);
        provider.setName(fields[1]);
        System.out.println(provider.toString());
        return provider;
    }

    public Provider findByKey(String name){
        String sql = "SELECT * FROM "+TABLE+" WHERE "+TYPE+" = ?";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return toObj(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Class getTClass() {
        return Provider.class;
    }

    @Override
    public void insert(Provider entity) throws SQLException {
        String sql = "INSERT INTO "+getTable()+" VALUES (?, ?)";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getType());
            statement.setString(2, entity.getName());
            System.out.println(statement.toString());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Provider entity) throws SQLException {
        String sql = "DELETE FROM "+getTable()+" WHERE "+TYPE+" = ?";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getType());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Provider entity) throws SQLException {
        String sql = "UPDATE "+getTable()+" SET "+NAME+" = ? WHERE "+TYPE+" = ?";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getType());
            System.out.println(statement.toString());
            int i = statement.executeUpdate();
            System.out.println(i);
        }
    }

}
