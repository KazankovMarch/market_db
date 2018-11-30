package ru.adkazankov.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFrameController implements Initializable {

    @FXML
    private Tab registrationTab;

    @FXML
    private Tab traderTab;

    @FXML
    private Tab shopTab;

    @FXML
    private Tab providerTab;

    @FXML
    private Tab queryTab;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            queryTab.setContent(FXMLLoader.load(getClass().getResource("/view/QueryTab.fxml")));
            providerTab.setContent(FXMLLoader.load(getClass().getResource("/view/ProviderTab.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
