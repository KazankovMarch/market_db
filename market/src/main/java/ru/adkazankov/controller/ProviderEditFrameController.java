package ru.adkazankov.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.adkazankov.App;
import ru.adkazankov.domain.Provider;

public class ProviderEditFrameController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField nameTextField;

    private Provider provider;

    private boolean saveClicked = false;

    @FXML
    void cancel(ActionEvent event) {
        ((Stage)typeTextField.getScene().getWindow()).close();
    }

    @FXML
    void save(ActionEvent event) {
        saveClicked = true;
        if(isCorrectInputData()) {
            provider.setType(typeTextField.getText());
            provider.setName(nameTextField.getText());
            ((Stage) typeTextField.getScene().getWindow()).close();
        }
        else {
            App.showError("Не корректный ввод", null);
        }
    }

    private boolean isCorrectInputData() {
        return !(nameTextField.getText().equals("") || typeTextField.getText().equals(""));
    }

    @FXML
    void initialize() {
        assert typeTextField != null : "fx:id=\"typeTextField\" was not injected: check your FXML file 'ProviderEditFrame.fxml'.";
        assert nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'ProviderEditFrame.fxml'.";

    }

    public void setProvider(Provider provider) {
        this.provider = provider;
        this.typeTextField.setText(provider.getType());
        this.nameTextField.setText(provider.getName());
        if (!(provider.getType()==null || provider.getType().equals(""))){
            this.typeTextField.setEditable(false);
        }
    }

    public boolean saveClicked() {
        return saveClicked;
    }
}
