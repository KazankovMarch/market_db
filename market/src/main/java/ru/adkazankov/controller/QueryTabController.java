package ru.adkazankov.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import ru.adkazankov.App;
import ru.adkazankov.util.DbWork;

public class QueryTabController {

    private DbWork dbWork = DbWork.getInstance();

    @FXML
    private TextField sqlTextField;

    @FXML
    private HBox resultHBox;

    @FXML
    void execute(Event event) {
        ResultSet resultSet = null;
        try {
            resultSet = dbWork.executeQuery(sqlTextField.getText());
            ArrayList<ObservableList<String>> grid = getGrid(resultSet);
            int columnNumber = grid.size();
            Collection<ListView<String>> listViews = new ArrayList<>();
            for(int i = 0; i < columnNumber; i++){
                ListView<String> listView = new ListView<>();
                listView.setItems(grid.get(i));
                listViews.add(listView);
            }
            resultHBox.getChildren().setAll(listViews);
        } catch (SQLException e) {
            App.showError("Не удалось выполнинть SELECT", e.toString());
        }
    }

    private ArrayList<ObservableList<String>> getGrid(ResultSet resultSet) throws SQLException {
        ArrayList<ObservableList<String>> grid = new ArrayList<>();
        ResultSetMetaData rsmd = null;
        rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        gridInit(grid, columnsNumber);
        for(int i = 1; i <=  columnsNumber; i++){
            grid.get(i-1).add(rsmd.getColumnName(i));
        }
        while (resultSet.next()){
            for(int i = 1; i <= columnsNumber; i++){
                grid.get(i-1).add(myToString(resultSet,i));
            }
        }
        return grid;
    }

    private String myToString(ResultSet resultSet, int i) throws SQLException {
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            String className = rsmd.getColumnClassName(i);
            Class aClass = Class.forName(className);
            Method method = aClass.getMethod("toString");
            return (String) method.invoke(resultSet.getObject(i));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "error";
    }

    private void gridInit(ArrayList<ObservableList<String>> grid, int columnsNumber) {
        for(int i = 0; i < columnsNumber; i++){
            grid.add(FXCollections.observableArrayList());
        }
    }

    @FXML
    void initialize() {
        assert sqlTextField != null : "fx:id=\"sqlTextField\" was not injected: check your FXML file 'QueryTab.fxml'.";
        assert resultHBox != null : "fx:id=\"resultHBox\" was not injected: check your FXML file 'QueryTab.fxml'.";
    }
}
