package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import model.TypeMonthTotal;
import DBAccess.AppointmentsDAO;

public class ApptsTypeMonthReport implements Initializable {

    public TableView typeMonthTotalTable;
    public TableColumn typeCol;
    public TableColumn monthCol;
    public TableColumn totalCol;
    public Button BackToReportsMenu;

    public void onBackToReportsMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Reports Menu");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            typeMonthTotalTable.setItems(AppointmentsDAO.getAppointmentsByTypeAndMonth());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("Month"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("Total"));
    }
}
