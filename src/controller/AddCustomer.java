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

public class AddCustomer implements Initializable {

    public TextField CustIDCombo;
    public TextField CustNameText;
    public TextField AddressText;
    public TextField PostalCodeText;
    public TextField PhoneNumberText;
    public ComboBox<Countries> CountryCombo;
    public ComboBox<Divisions> DivisionCombo;

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

    public void onSaveCust(ActionEvent actionEvent) throws SQLException, IOException {
        String customer = CustNameText.getText();
        String address = AddressText.getText();
        String postalCode = PostalCodeText.getText();
        String phone = PhoneNumberText.getText();
        Countries country = CountryCombo.getValue();
        Divisions division = DivisionCombo.getValue();

        CustomersDAO.insertCustomer(CustNameText.getText(), AddressText.getText(), PostalCodeText.getText(), PhoneNumberText.getText(), DivisionCombo.getValue().getDivisionId());

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountryCombo.setPromptText("Select Country...");
        CountryCombo.setItems(CountriesDAO.getAllCountries());
        //CountryCombo.setValue(CountriesDAO.getAllCountries().get(1));
        DivisionCombo.setPromptText("Select Division...");
        DivisionCombo.setItems(DivisionsDAO.getAllDivisions());

    }
}
