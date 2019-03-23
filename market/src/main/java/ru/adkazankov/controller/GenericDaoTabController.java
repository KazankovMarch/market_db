package ru.adkazankov.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.adkazankov.App;
import ru.adkazankov.dao.Dao;

import static ru.adkazankov.controller.GenericEditFrameController.NEW_ELEMENT_TITLE;

public abstract class GenericDaoTabController<E> {

    @FXML
    private TableView<E> table;

    protected Dao<E> dao;

    private Collection<E> toRemove = new ArrayList<>();

    private Collection<E>  toUpdate = new ArrayList<>();

    private Collection<E> toAdd = new ArrayList<>();


    @FXML
    void add(ActionEvent event) {
        E e = createAndInit();
        if(openEditFrame(e)){
            toAdd.add(e);
            table.getItems().add(e);
            table.refresh();
        }
    }

    protected abstract E createAndInit();

    @FXML
    void delete(ActionEvent event) {
        E e = table.getSelectionModel().getSelectedItem();
        if (e!=null){
            toRemove.add(e);
            toAdd.remove(e);
            toUpdate.remove(e);
            table.getItems().removeAll(e);
            table.refresh();
        }
        else {
            App.showError("Не выделен элеиент в таблице", null);
        }
    }

    @FXML
    void edit(ActionEvent event) {
        E e = table.getSelectionModel().getSelectedItem();
        if (e!=null){
            if(openEditFrame(e)){
                toUpdate.add(e);
                table.refresh();
            }
        }
        else {
            App.showError("Не выделен элеиент в таблице", null);
        }
    }

    @FXML
    void load() {
        toUpdate.clear();;
        toAdd.clear();
        toRemove.clear();
        table.getItems().setAll(dao.getAll());
        table.refresh();
    }

    @FXML
    void save(ActionEvent event) {
        ArrayList<String> errors = new ArrayList<>();
        toRemove.removeIf(e -> {
            try {
                dao.delete(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
                errors.add("не удален:"+ex.toString());
            }
            return true;
        });
        toAdd.removeIf(e -> {
            try {
                dao.insert(e);
                System.out.println("generic insert "+ e);
            } catch (SQLException ex) {
                ex.printStackTrace();
                errors.add("не добавлен:"+ex.toString());
            }
            return true;
        });
        toUpdate.removeIf(e -> {
            try {
                dao.update(e);
                System.out.println("generic update "+e);
            } catch (SQLException ex) {
                ex.printStackTrace();
                errors.add("не обновлен:"+ex.toString());
            }
            return true;
        });
        if(errors.size()>0){
            App.showError("Изменения нарушающие структуры бд не сохранены:", errors.toString());
            load();
        }
        else {
            App.showInfo("Изменения сохранены", null);
        }
    }


    @FXML
    void exportData(ActionEvent event) throws IOException {
        openExportAndImportFrame(true);
    }
    @FXML
    void importData(ActionEvent event) throws IOException {
        openExportAndImportFrame(false);
    }

    private void openExportAndImportFrame(boolean export) throws IOException {
        if(canExportOrImport()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DataExportAndImportFrame.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            if (export) {
                stage.setTitle("Экспорт");
            } else {
                stage.setTitle("Импорт");
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            GenericDataExportAndImportController controller = loader.getController();
            controller.setDaoAndMethod(dao, export);
            stage.showAndWait();
            load();
        }else {
            App.showError("Сохраните или сбросьте изменения", "Нажмите 'Сохранить' или 'Загрузить'");
        }
    }

    private boolean canExportOrImport() {
        return toAdd.isEmpty() && toRemove.isEmpty() && toUpdate.isEmpty();
    }

    private boolean openEditFrame(E e) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/GenericEditFrame.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            }catch (LoadException ex){
                System.err.println(ex.getCause());
                ex.printStackTrace();
                return false;
            }
            Stage stage = new Stage();
            stage.setTitle(getEditFrameTitle(e)==null? NEW_ELEMENT_TITLE : getEditFrameTitle(e));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            GenericEditFrameController controller = loader.getController();
            controller.setObjectAndDao(e, dao);

            stage.showAndWait();
            return controller.saveClicked();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    protected abstract String getEditFrameTitle(E e);

    public TableView<E> getTable() {
        return table;
    }
}
