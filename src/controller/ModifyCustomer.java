package controller;

import DBAccess.CountriesDAO;
import DBAccess.CustomersDAO;
import DBAccess.DivisionsDAO;
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

public class ModifyCustomer implements Initializable {

    public TextField CustIDText;
    public TextField CustNameText;
    public TextField AddressText;
    public TextField PostalCodeText;
    public TextField PhoneNumberText;
    public ComboBox<Countries> CountryCombo;
    public ComboBox<Divisions> DivisionCombo;
    public Customers selectedCustomer;


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

    public void onSaveModifyCust(ActionEvent actionEvent) {
        String customer = CustIDText.getText();
        String address = AddressText.getText();
        String postalCode = PostalCodeText.getText();
        String phone = PhoneNumberText.getText();
        String customerName = CustNameText.getText();
        Countries country = CountryCombo.getValue();
        Divisions division = DivisionCombo.getValue();

        /*
        CustomersDAO.updateCustomer(CustNameText.getText(), AddressText.getText(), PostalCodeText.getText(), PhoneNumberText.getText(),
                DivisionCombo.getValue().getDivisionId());

         */


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCustomer = CustomerScreen.customerToModify();

        try {
            CustNameText.setText(String.valueOf(selectedCustomer.getCustomerId()));
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
