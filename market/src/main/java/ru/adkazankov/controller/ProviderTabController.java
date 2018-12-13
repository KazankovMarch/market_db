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
import ru.adkazankov.dao.ProviderDao;
import ru.adkazankov.dao.ShopDao;
import ru.adkazankov.domain.Provider;
import ru.adkazankov.domain.Shop;

public class ProviderTabController extends GenericDaoTabController<Provider>{

    @FXML
    private TableColumn<Provider, String> typeColumn;

    @FXML
    private TableColumn<Provider, String> nameColumn;

    @Override
    protected Provider createAndInit() {
        return new Provider();
    }

    @Override
    protected String getEditFrameTitle(Provider provider) {
        return provider.getName();
    }

    @FXML
    void initialize() {
        dao = new ProviderDao();
        List<TableColumn<Provider,  String>> columns = new ArrayList<>();
        columns.add(typeColumn);
        columns.add(nameColumn);
        getTable().getColumns().setAll(columns);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

}
