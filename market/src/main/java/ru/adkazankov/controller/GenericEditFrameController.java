package ru.adkazankov.controller;

import ru.adkazankov.annotation.Column;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.adkazankov.App;
import ru.adkazankov.dao.Dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericEditFrameController {


    public static final String NEW_ELEMENT_TITLE = "Новый элемент";

    @FXML
    protected GridPane gridPane;

    protected Object e;

    protected Dao dao;

    protected Map<Field, TextField> fieldMap = new HashMap<>();

    protected boolean saveClicked = false;

    @FXML
    private void cancel(ActionEvent event) {
        ((Stage)gridPane.getScene().getWindow()).close();
    }

    @FXML
    private void save(ActionEvent event) throws IllegalAccessException {
        saveClicked = true;
        if(isCorrectInputData()) {
            try {
                for (Field field : fieldMap.keySet()) {
                    field.setAccessible(true);
                    Class type = field.getType();
                    if (type.equals(String.class)) {
                        field.set(e, fieldMap.get(field).getText());
                    } else if (type.equals(Integer.class)) {
                        field.set(e, Integer.parseInt(fieldMap.get(field).getText()));
                    } else if (type.equals(Double.class)) {
                        field.set(e, Double.parseDouble(fieldMap.get(field).getText()));
                    } else if (type.equals(Boolean.class)) {
                        field.set(e, Boolean.parseBoolean(fieldMap.get(field).getText()));
                    } else {
                        throw new IllegalStateException(type.toString() + " not supported yet");
                    }
                    System.out.println(field.get(e));
                }
                ((Stage) gridPane.getScene().getWindow()).close();
            }catch (NumberFormatException ex){
                App.showError("Не удалось распознать числа", null);
            }
        }
        else {
            App.showError("Пожалуйста, заполните все поля", null);
        }
    }

    private boolean isCorrectInputData() {
        for(TextField textField : fieldMap.values()){
            if(textField.getText()==null || textField.getText().equals("")){
                return false;
            }
        }
        return true;
    }


    void setObjectAndDao(Object e, Dao  dao) {
        try {
            this.e = e;
            this.dao = dao;
            int i = 1;
            for (Field f : getColumns(dao)) {
                Column column = f.getAnnotation(Column.class);
                Label label = new Label(column.name());
                TextField textField = new TextField();
                if(f.get(e)!=null) {
                    textField.setText(f.get(e).toString());
                }
                if(column.pKey() && !((Stage)gridPane.getScene().getWindow()).getTitle().contains(NEW_ELEMENT_TITLE)){
                    textField.setEditable(false);
                }
                fieldMap.put(f, textField);
                gridPane.addRow(i, label, textField);
                i++;
            }
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }

//    private Integer getRowCount(GridPane gridPane) {
//        Method method = null;
//        try {
//            method = gridPane.getClass().getDeclaredMethod("getNumberOfRows");
//            method.setAccessible(true);
//            return (Integer) method.invoke(gridPane);
//        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e1) {
//            e1.printStackTrace();
//        }
//        return null;
//    }

    private List<Field> getColumns(Dao dao) {
        List<Field> res = new ArrayList<>();
        for(Field f : dao.getTClass().getDeclaredFields()){
            f.setAccessible(true);
            if(f.getAnnotation(Column.class)!=null){
                res.add(f);
            }
        }
        System.out.println(res);
        return res;
    }

    boolean saveClicked(){
        return saveClicked;
    }
}
