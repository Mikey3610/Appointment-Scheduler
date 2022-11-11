package controller;

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

    public void onAddCust(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 550);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }
    public static Customers customerToModify() {
        return customerToModify;
    }

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

    public void onDeleteCust(ActionEvent actionEvent) throws SQLException {
        Customers selectedCust = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCust == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No customer selected.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setContentText("Are you sure you want to delete this customer?");
            Optional<ButtonType> confirmation = alert.showAndWait();

            if (confirmation.get() == ButtonType.OK) {
                CustomersDAO.deleteCustomer(selectedCust.getCustomerId());
                customerTable.setItems(CustomersDAO.selectAllCustomers());
            }
        }
    }

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
