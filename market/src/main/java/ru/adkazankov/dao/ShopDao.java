package ru.adkazankov.dao;
import ru.adkazankov.domain.Provider;
import ru.adkazankov.domain.Shop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShopDao extends DaoImpl<Shop> {

    private static final String NAME = "Название";
    private static final String TYPE = "Тип";
    private static final String TABLE = "Магазин";


    @Override
    public Shop toObj(ResultSet resultSet) {
        try {
            Shop shop = new Shop();
            shop.setName(resultSet.getString(NAME));
            shop.setType(resultSet.getString(TYPE));
            return shop;
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
    protected Shop fromStringSQL(String line, String delimiter) {
        Shop shop = new Shop();
        String[] fields = line.split(delimiter);
        shop.setName(fields[0]);
        shop.setType(fields[1]);
        return shop;
    }

    public Shop findByKey(String name){
        String sql = "SELECT * FROM "+TABLE+" WHERE "+NAME+" = ?";
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
        return Shop.class;
    }

    @Override
    public void insert(Shop entity) throws SQLException {
        String sql = "INSERT INTO "+getTable()+" VALUES (?, ?)";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getType());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Shop entity) throws SQLException {
        String sql = "DELETE FROM "+getTable()+" WHERE "+NAME+" = ?";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Shop entity) throws SQLException {
        String sql = "UPDATE "+getTable()+" SET "+NAME+" = ? WHERE "+TYPE+" = ?";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getType());
            statement.executeUpdate();
        }
    }
}
