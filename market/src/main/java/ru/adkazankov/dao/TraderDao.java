package ru.adkazankov.dao;

import ru.adkazankov.domain.Shop;
import ru.adkazankov.domain.Trader;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TraderDao extends DaoImpl<Trader> {

    private static final String FIO = "Имя";
    private static final String CITY = "Город";
    private static final String NAME = "Название";
    private static final String COST = "Цена";
    private static final String COUNT = "Количество";
    private static final String TABLE = "Продавцы";


    @Override
    public Trader toObj(ResultSet resultSet) {
        try {
            Trader trader = new Trader();
            trader.setFio(resultSet.getString(FIO));
            trader.setCity(resultSet.getString(CITY));
            trader.setName(resultSet.getString(NAME));
            trader.setCost(resultSet.getDouble(COST));
            trader.setCount(resultSet.getInt(COUNT));
            return trader;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getTable() {
        return TABLE;
    }

    public Trader findBykey(String fio, String city, String name){
        String sql = "SELECT * FROM "+getTable()+" WHERE "
                +FIO+" = ? AND "+CITY+" = ? AND "+NAME+" = ?";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1, fio);
            statement.setString(2, city);
            statement.setString(3, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return toObj(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Trader entity) throws SQLException {
        String sql = "INSERT INTO "+getTable()+" VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getFio());
            statement.setString(2, entity.getCity());
            statement.setString(3, entity.getName());
            statement.setDouble(4, entity.getCost());
            statement.setInt(5, entity.getCount());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Trader entity) throws SQLException {
        String sql = "DELETE FROM "+getTable()+" WHERE "+FIO+" = ?, "+CITY+" = ?, "+NAME+" = ?";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1,  entity.getFio());
            statement.setString(2,  entity.getCity());
            statement.setString(3,  entity.getName());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Trader entity) throws SQLException {
        String sql = "UPDATE "+getTable()+" SET "+COST+" = ?, "+COUNT+" = ? WHERE "
                +FIO+" = ?, "+CITY+" = ?, "+NAME+" = ?";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setDouble(1, entity.getCost());
            statement.setInt(2, entity.getCount());
            statement.setString(3, entity.getFio());
            statement.setString(4, entity.getCity());
            statement.setString(5, entity.getName());
            statement.executeUpdate();
        }
    }
}
