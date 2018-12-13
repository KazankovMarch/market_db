package ru.adkazankov.dao;

import ru.adkazankov.domain.Provider;
import ru.adkazankov.domain.Registration;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDao extends DaoImpl<Registration> {

    private static final String FIO = "Имя";
    private static final String DATE = "Дата_регистрации";
    private static final String COUNT = "Количество_позиций";
    private static final String TABLE = "Регистрация";

    @Override
    public Registration toObj(ResultSet resultSet) {
        try {
            Registration registration = new Registration();
            registration.setFio(resultSet.getString(FIO));
            registration.setDate(resultSet.getDate(DATE));
            registration.setTradeCount(resultSet.getInt(COUNT));
            return registration;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getTable() {
        return TABLE;
    }

    public Registration findByKey(String fio){
        String sql = "SELECT * FROM "+TABLE+" WHERE "+FIO+" = ?";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1, fio);
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
        return Registration.class;
    }

    @Override
    public void insert(Registration entity) throws SQLException {
        String sql = "INSERT INTO "+getTable()+" VALUES (?, ?, ?)";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getFio());
            statement.setDate(2, (Date) entity.getDate());
            statement.setInt(3, entity.getTradeCount());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Registration entity) throws SQLException {
        String sql = "DELETE FROM "+getTable()+" WHERE "+FIO+" = ?";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setString(1,  entity.getFio());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Registration entity) throws SQLException {
        String sql = "UPDATE "+getTable()+" SET "+DATE+" = ?, "+COUNT+" = ? WHERE "+FIO+" = ?";
        try(PreparedStatement statement = dbWork.getConnection().prepareStatement(sql)) {
            statement.setDate(1, (Date) entity.getDate());
            statement.setInt(2, entity.getTradeCount());
            statement.setString(3, entity.getFio());
            statement.executeUpdate();
        }
    }
}
