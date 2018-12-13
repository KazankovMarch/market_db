package ru.adkazankov.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.adkazankov.dao.TraderDao;
import ru.adkazankov.domain.Trader;

import java.net.URL;
import java.util.*;

public class TraderTabController extends GenericDaoTabController<Trader> implements Initializable{

    @FXML
    private TableColumn<Trader, String> fioColumn;

    @FXML
    private TableColumn<Trader, String> cityColumn;

    @FXML
    private TableColumn<Trader, String> nameColumn;

    @FXML
    private TableColumn<Trader, String> costColumn;

    @FXML
    private TableColumn<Trader, String> countColumn;

    @Override
    protected Trader createAndInit() {
        Trader trader = new Trader();
        return trader;
    }

    @Override
    protected String getEditFrameTitle(Trader trader) {
        return trader.getFio();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao = new TraderDao();
        List<TableColumn<Trader,  String>> columns = new ArrayList<>();
        columns.add(fioColumn);
        columns.add(cityColumn);
        columns.add(nameColumn);
        columns.add(costColumn);
        columns.add(countColumn);
        getTable().getColumns().setAll(columns);
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    }
}
