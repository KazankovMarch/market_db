package ru.adkazankov.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.adkazankov.App;
import ru.adkazankov.dao.RegistrationDao;
import ru.adkazankov.domain.Registration;

public class RegistrationTabController extends GenericDaoTabController<Registration>{

    @FXML
    private TableColumn<Registration, String> fioColumn;

    @FXML
    private TableColumn<Registration, String> dateColumn;

    @FXML
    private TableColumn<Registration, String> countCuolumn;


    @Override
    protected Registration createAndInit() {
        return new Registration();
    }

    @Override
    protected String getEditFrameTitle(Registration registration) {
        return registration.getFio();
    }


    @FXML
    void initialize() {
        dao = new RegistrationDao();
        List<TableColumn<Registration,  String>> list = new ArrayList<>();
        list.add(fioColumn);
        list.add(dateColumn);
        list.add(countCuolumn);
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        countCuolumn.setCellValueFactory(new PropertyValueFactory<>("tradeCount"));
    }

}
