package ru.adkazankov.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.adkazankov.dao.ShopDao;
import ru.adkazankov.domain.Shop;
import ru.adkazankov.domain.Trader;

public class ShopTabController extends GenericDaoTabController<Shop> {

    @FXML
    private TableColumn<Shop, String> nameColumn;

    @FXML
    private TableColumn<Shop, String> typeColumn;


    @Override
    protected Shop createAndInit() {
        return new Shop();
    }


    @Override
    protected String getEditFrameTitle(Shop shop) {
        return shop.getName();
    }

    @FXML
    void initialize() {
        dao = new ShopDao();
        List<TableColumn<Shop,  String>> columns = new ArrayList<>();
        columns.add(nameColumn);
        columns.add(typeColumn);
        getTable().getColumns().setAll(columns);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    }
}
