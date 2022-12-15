package controller;

import DBAccess.AppointmentsDAO;
import DBAccess.CustomersDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class stores all of the information of the customers and displays that to the user.*/
public class CustomerScreen implements Initializable {
    public TableView <Customers> customerTable;
    public TableColumn customerIdCol;
    public TableColumn customerNameCol;
    public TableColumn addressCol;
    public TableColumn postalCodeCol;
    public TableColumn phoneNumberCol;
    public TableColumn countryCol;
    public TableColumn divisionIdCol;
    public static Customers customerToModify;

    /** This method intiliazes the Customer Screen with the data stored in the database for customers.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerTable.setItems(CustomersDAO.selectAllCustomers());

            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            countryCol.setCellValueFactory(new PropertyValueFactory<>("countryId"));
            divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method will take the user to the Add Customer screen.
     * @param actionEvent Changes the screen to the Add Customer screen.
     * */
    public void onAddCust(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 550);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /** This method allows the user to select one of the customers from the Customer Screen and modify or delete it. */
    public static Customers customerToModify() {
        return customerToModify;
    }

    /** This method will bring the user to the Modify Customer screen if they have a customer selected on the screen.
     * @param actionEvent Changes to the Modify Customer screen.
     * */
    public void onModifyCust(ActionEvent actionEvent) throws IOException {
        customerToModify = customerTable.getSelectionModel().getSelectedItem();

        if (customerToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customers selected.");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 550);
            stage.setTitle("Modify Customer");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This method will delete the selected customer and its data from the database.
     * @param actionEvent This action deletes the customer and its data from the database.
     * */
    public void onDeleteCust(ActionEvent actionEvent) throws SQLException {
        try {
            Customers selectedCust = customerTable.getSelectionModel().getSelectedItem();

            if (selectedCust == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No customer selected.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Customer");
                alert.setContentText("All appointments for this customer will be deleted. Are you sure you want to delete this customer?");
                Optional<ButtonType> confirmation = alert.showAndWait();

                if (confirmation.get() == ButtonType.OK) {
                    AppointmentsDAO.deleteAppointmentByCustID(selectedCust.getCustomerId());
                    CustomersDAO.deleteCustomer(selectedCust.getCustomerId());
                    customerTable.setItems(CustomersDAO.selectAllCustomers());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method will return the user to the main appointments screen and prompt the user with a confirmation window.
     * @param actionEvent This action brings the user back to the main appointments screen with a confirmation window.
     * */
    public void onBacktoAppts(ActionEvent actionEvent) throws IOException {
        Alert cancellation = new Alert(Alert.AlertType.CONFIRMATION);
        cancellation.setTitle("Go back to Appointments Screen");
        cancellation.setContentText("Go back to Appointments Screen?");
        Optional<ButtonType> result = cancellation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainAppointmentScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 600);
            stage.setTitle("Main Appointment Screen");
            stage.setScene(scene);
            stage.show();
        }
    }
}
