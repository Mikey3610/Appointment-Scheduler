package controller;

import DBAccess.CountriesDAO;
import DBAccess.CustomersDAO;
import DBAccess.DivisionsDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class allows the user to modify a selected customer's data. */
public class ModifyCustomer implements Initializable {

    public TextField CustIDText;
    public TextField CustNameText;
    public TextField AddressText;
    public TextField PostalCodeText;
    public TextField PhoneNumberText;
    public ComboBox<Countries> CountryCombo;
    public ComboBox<Divisions> DivisionCombo;
    public Customers selectedCustomer;

    /** This method cancels any input from the user and returns them to the Main Appointment Screen.
     * */
    public void onCancelModifyCust(ActionEvent actionEvent) throws IOException {
        Alert cancellation = new Alert(Alert.AlertType.CONFIRMATION);
        cancellation.setTitle("Confirm Cancellation");
        cancellation.setContentText("Discard changes and return to Customers Screen?");
        Optional<ButtonType> result = cancellation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 600);
            stage.setTitle("Customers");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This method saves any changes made to the customer fields and returns the user to the Main Appointments screen.
     * @param actionEvent This action saves the input from the user for any edits made to the customers.
     * @return Returns with a notification to the user if they leave any fields blank.
     * */
    public void onSaveModifyCust(ActionEvent actionEvent) throws IOException {
        try {
            String customer = CustNameText.getText();
            String address = AddressText.getText();
            String postalCode = PostalCodeText.getText();
            String phone = PhoneNumberText.getText();
            Countries country = CountryCombo.getValue();
            Divisions division = DivisionCombo.getValue();

            if (customer.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Customer name is empty. Please input a name.");
                alert.showAndWait();
                return;
            } else if (address.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Address is empty. Please input an address.");
                alert.showAndWait();
                return;
            } else if (postalCode.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Postal Code is empty. Please input a postal code.");
                alert.showAndWait();
                return;
            } else if (phone.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Phone number is empty. Please input a phone number.");
                alert.showAndWait();
                return;
            } else if (country == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Country has not been selected. Please select a country");
                alert.showAndWait();
                return;
            } else if (division == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Division has not been selected. Please select a division.");
                alert.showAndWait();
                return;
            }

            CustomersDAO.updateCustomer(selectedCustomer.getCustomerId(), CustNameText.getText(), AddressText.getText(), PostalCodeText.getText(), PhoneNumberText.getText(), DivisionCombo.getValue().getDivisionId());


            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 600);
            stage.setTitle("Customers");
            stage.setScene(scene);
            stage.show();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method will display the proper division (region) that corresponds to whatever country the user has selected.
     * @param actionEvent The action will take whatever country the user has selected and retrieve the corresponding division. */
    public void onCountryCombo(ActionEvent actionEvent) {
        int countryId = CountryCombo.getValue().getCountryId();
        ObservableList<Divisions> list = DivisionsDAO.getDivisionByCountry(countryId);
        DivisionCombo.setItems(DivisionsDAO.getDivisionByCountry(countryId));
    }


    /** This method autopopulates all of the fields in the Modify Customer window with the selected customer's data.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCustomer = CustomerScreen.customerToModify();

        try {
            CustIDText.setText(String.valueOf(selectedCustomer.getCustomerId()));
            AddressText.setText(selectedCustomer.getAddress());
            PostalCodeText.setText(String.valueOf(selectedCustomer.getPostalCode()));
            PhoneNumberText.setText(String.valueOf(selectedCustomer.getPhone()));
            CustNameText.setText(String.valueOf(selectedCustomer.getCustomerName()));
            CountryCombo.setItems(CountriesDAO.getAllCountries());
            Countries country = CountriesDAO.getCountryDivisionId(selectedCustomer.getDivisionId());
            CountryCombo.setValue(country);
            DivisionCombo.setItems(DivisionsDAO.getDivisionByCountry(selectedCustomer.getCountryId()));
            Divisions division = DivisionsDAO.getDivision(selectedCustomer.getDivisionId());
            DivisionCombo.setValue(division);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
