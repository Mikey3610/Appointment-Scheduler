package controller;

import DBAccess.AppointmentsDAO;
import DBAccess.CustomersDAO;
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

/** This class will display the totals of the appointments by customer ID. */
public class CustomerIDReport implements Initializable {

    public TableView custIdTable;
    public TableColumn custIdCol;
    public TableColumn totalCol;

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

    /** This method initializes the CustomerIDReport screen with the corresponding Customer ID and totals information.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            custIdTable.setItems(CustomersDAO.getCustomerIdCount());
            custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
