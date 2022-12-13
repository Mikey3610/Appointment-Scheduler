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

import DBAccess.AppointmentsDAO;

/** This class will display the appointments in the database by type and month. */
public class ApptsTypeMonthReport implements Initializable {

    public TableView typeMonthTotalTable;
    public TableColumn typeCol;
    public TableColumn monthCol;
    public TableColumn totalCol;
    public Button BackToReportsMenu;

    /** This method will return the user back to the Reports menu.
     * @param actionEvent Returns the user to the Reports menu screen.
     * */
    public void onBackToReportsMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Reports Menu");
        stage.setScene(scene);
        stage.show();
    }

    /** This method autopopulates this screen with the corresponding type and month data from the appointments.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            typeMonthTotalTable.setItems(AppointmentsDAO.getAppointmentsByTypeAndMonth());

        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("Month"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("Total"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
