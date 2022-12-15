package controller;

import DBAccess.CountriesDAO;
import DBAccess.CustomersDAO;
import DBAccess.DivisionsDAO;
import Lambdas.WarningLambda;
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

/** This class allows the user to add customers with all the different criteria such as times, dates, and people associated with the customer. */
public class AddCustomer implements Initializable {

    public TextField CustIDText;
    public TextField CustNameText;
    public TextField AddressText;
    public TextField PostalCodeText;
    public TextField PhoneNumberText;
    public ComboBox<Countries> CountryCombo;
    public ComboBox<Divisions> DivisionCombo;

    /** This method will cancel any input from the user in the Add Customer screen and return the user to the Customer Screen.
     * @param actionEvent This action will cancel any input and return the user to the Customer Screen.
     * */
    public void onCancelCust(ActionEvent actionEvent) throws IOException {
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

    /** This method will save input from the user for adding customers into the database.
     * LAMBDA EXPRESSION - Lambda that displays error message as a String if customer name field is blank. Used to improve readability in the body of the code.
     * @param actionEvent This action will save all of the input and enter the data into the database.
     * */
    public void onSaveCust(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            String customer = CustNameText.getText();
            String address = AddressText.getText();
            String postalCode = PostalCodeText.getText();
            String phone = PhoneNumberText.getText();
            Countries country = CountryCombo.getValue();
            Divisions division = DivisionCombo.getValue();


            if (customer.isEmpty()) {
                WarningLambda message = () -> {
                    String s = "Customer name is empty. Please input a name.";
                    return s;
                };


            if (customer.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(message.warningMessage());//"Customer name is empty. Please input a name.");
                alert.showAndWait();
                return;
            }
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

            //Inserts the input values in the different variables using the insertCustomer method in the CustomersDAO class
            CustomersDAO.insertCustomer(CustNameText.getText(), AddressText.getText(), PostalCodeText.getText(), PhoneNumberText.getText(),
                    DivisionCombo.getValue().getDivisionId());

            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 600);
            stage.setTitle("Customers");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Form contains invalid input values or blanks. Please check and input proper values.");
            alert.showAndWait();
        }
    }

    /** This method will display the proper division (region) that corresponds to whatever country the user has selected.
     * @param actionEvent The action will take whatever country the user has selected and retrieve the corresponding division. */
    public void onCountryCombo(ActionEvent actionEvent) {
        int countryId = CountryCombo.getValue().getCountryId();
        ObservableList<Divisions> list = DivisionsDAO.getDivisionByCountry(countryId);
        DivisionCombo.setItems(DivisionsDAO.getDivisionByCountry(countryId));
    }

    /** This method initializes the Add Customer screen with the prompts for the user to select from.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountryCombo.setPromptText("Select Country...");
        CountryCombo.setItems(CountriesDAO.getAllCountries());
        //CountryCombo.setValue(CountriesDAO.getAllCountries().get(1));
        DivisionCombo.setPromptText("Select Division...");
        DivisionCombo.setItems(DivisionsDAO.getAllDivisions());

    }
}
