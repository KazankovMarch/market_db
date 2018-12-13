package ru.adkazankov.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.adkazankov.App;
import ru.adkazankov.dao.Dao;

import java.io.File;
import java.sql.SQLException;

public class GenericDataExportAndImportController {

    private static final String DELIMITER = ",";

    private File file;

    private FileChooser fileChooser;

    private Dao dao;

    private boolean export;

    @FXML
    private Button actionButton;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private HBox hBox;

    @FXML
    private TextField textField;

    @FXML
    void action(ActionEvent event) {
        if(file!=null) {
            if (export) {
                progressIndicator.setVisible(true);
                try {
                    dao.exportTo(file.getPath(), DELIMITER);
                } catch (SQLException e) {
                    App.showError(e.toString(),"Не удалось экспортировать данные");
                }
                progressIndicator.setVisible(false);
            } else {
                progressIndicator.setVisible(true);
                try {
                    dao.importFrom(file.getPath(), DELIMITER);
                } catch (SQLException e) {
                    App.showError(e.toString(),"Не удалось импортировать данные");
                }
                progressIndicator.setVisible(false);
            }
            close();
        }else {
            App.showError("Выберите файл", null);
        }
        progressIndicator.setVisible(false);
    }

    private void close() {
        ((Stage)textField.getScene().getWindow()).close();
    }

    @FXML
    void cancel(ActionEvent event) {
        close();
    }


    @FXML
    void selectFile(ActionEvent event) {
        file = fileChooser.showOpenDialog(textField.getScene().getWindow());
        textField.setText(file==null?"":file.toString());
    }


    @FXML
    void initialize() {
        fileChooser = new FileChooser();
        progressIndicator.setVisible(false);
    }

    public void setDaoAndMethod(Dao dao, boolean export){
        this.dao = dao;
        this.export = export;
        if(export){
            actionButton.setText("Экспорт");
        }else {
            actionButton.setText("Импорт");
        }
    }

}
