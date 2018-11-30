package ru.adkazankov.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
import ru.adkazankov.domain.Provider;

public class ProviderTabController {

    private ProviderDao dao = new ProviderDao();

    private Collection<Provider> toRemove = new ArrayList<>();

    private Collection<Provider>  toUpdate = new ArrayList<>();

    private Collection<Provider> toAdd = new ArrayList<>();

    @FXML
    private TableView<Provider> table;

    @FXML
    private TableColumn<Provider, String> typeColumn;

    @FXML
    private TableColumn<Provider, String> nameColumn;

    @FXML
    void add(ActionEvent event) {
        Provider provider = new Provider();
        provider.setName("Новый поставщик");
        if(openEditFrame(provider)){
            toAdd.add(provider);
            table.getItems().add(provider);
            table.refresh();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        Provider provider = table.getSelectionModel().getSelectedItem();
        if (provider!=null){
            toRemove.add(provider);
            toAdd.remove(provider);
            toUpdate.remove(provider);
            table.getItems().removeAll(provider);
            table.refresh();
        }
        else {
            App.showError("Не выделен элеиент в таблице", null);
        }
    }

    @FXML
    void edit(ActionEvent event) {
        Provider provider = table.getSelectionModel().getSelectedItem();
        if (provider!=null){
            if(openEditFrame(provider)){
                toUpdate.add(provider);
                table.refresh();
            }
        }
        else {
            App.showError("Не выделен элеиент в таблице", null);
        }
    }

    @FXML
    void load(ActionEvent event) {
        toUpdate.clear();;
        toAdd.clear();
        toRemove.clear();
        table.getItems().setAll(dao.getAll());
        table.refresh();
    }

    @FXML
    void save(ActionEvent event) {
        ArrayList<String> errors = new ArrayList<>();
        toRemove.removeIf(provider -> {
            try {
                dao.delete(provider);
            } catch (SQLException e) {
                e.printStackTrace();
                errors.add("не удален:"+provider.toString());
            }
            return true;
        });
        toAdd.removeIf(provider -> {
            try {
                dao.insert(provider);
            } catch (SQLException e) {
                e.printStackTrace();
                errors.add("не добавлен:"+provider.toString());
            }
            return true;
        });
        toUpdate.removeIf(provider -> {
            try {
                dao.update(provider);
            } catch (SQLException e) {
                e.printStackTrace();
                errors.add("не обновлен:"+provider.toString());
            }
            return true;
        });
        if(errors.size()>0){
            App.showError("Изменения нарушающие структуры бд не сохранены:", errors.toString());
            load(event);
        }
        else {
            App.showInfo("Изменения сохранены", null);
        }
    }

    @FXML
    void initialize() {
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'ProviderTab.fxml'.";
        assert typeColumn != null : "fx:id=\"typeColumn\" was not injected: check your FXML file 'ProviderTab.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'ProviderTab.fxml'.";

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    }



    public boolean openEditFrame(Provider provider) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ProviderEditFrame.fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(provider.getName());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            ProviderEditFrameController controller = loader.getController();
            controller.setProvider(provider);

            stage.showAndWait();
            return controller.saveClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
