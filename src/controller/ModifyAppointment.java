package controller;

import DBAccess.ContactsDAO;
import DBAccess.CountriesDAO;
import DBAccess.CustomersDAO;
import DBAccess.DivisionsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyAppointment implements Initializable {
    public TextField apptIdText;
    public TextField apptTitleText;
    public TextField apptDescriptionText;
    public TextField apptLocationText;
    public DatePicker apptStartDateDatePicker;
    public DatePicker apptEndDateDatePicker;
    public ComboBox<LocalTime> apptStartCombo;
    public ComboBox<LocalTime> apptEndCombo;
    public ComboBox<Contacts> apptContactCombo;
    public TextField apptTypeText;
    public ComboBox<Customers> apptCustIdCombo;
    public ComboBox<User> apptUserIdCombo;
    public Appointments selectedAppointment;

    public void onCancelModifyAppt(ActionEvent actionEvent) throws IOException {
        Alert cancellation = new Alert(Alert.AlertType.CONFIRMATION);
        cancellation.setTitle("Confirm Cancellation");
        cancellation.setContentText("Discard changes and return to Appointments Screen?");
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

    public void onSaveModifyAppt(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedAppointment = MainAppointmentScreen.appointmentToModify();

        try {
            apptIdText.setText(String.valueOf(selectedAppointment.getAppointmentId()));
            apptTitleText.setText(selectedAppointment.getTitle());
            apptDescriptionText.setText(selectedAppointment.getDescription());
            apptLocationText.setText(selectedAppointment.getLocation());
            apptTypeText.setText(selectedAppointment.getType());
            apptStartDateDatePicker.setValue(LocalDate.from((selectedAppointment.getStartDateTime().toLocalDateTime())));
            apptEndDateDatePicker.setValue(LocalDate.from((selectedAppointment.getEndDateTime().toLocalDateTime())));
            apptStartCombo.setValue(LocalTime.from(selectedAppointment.getStartDateTime().toLocalDateTime()));
            apptEndCombo.setValue(LocalTime.from(selectedAppointment.getEndDateTime().toLocalDateTime()));
            apptContactCombo.setItems(ContactsDAO.selectAllContacts());
            Contacts contact = ContactsDAO.getContactById(selectedAppointment.getContactId());
            apptContactCombo.setValue(contact);

            //apptCustIdCombo.setValue(selectedAppointment.getAppointmentId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

            /*
            PhoneNumberText.setText(String.valueOf(selectedCustomer.getPhone()));
            CustNameText.setText(String.valueOf(selectedCustomer.getCustomerName()));

            CountryCombo.setItems(CountriesDAO.getAllCountries());
            Countries country = CountriesDAO.getCountryDivisionId(selectedCustomer.getDivisionId());
            CountryCombo.setValue(country);

            DivisionCombo.setItems(DivisionsDAO.getDivisionByCountry(selectedCustomer.getCountryId()));
            Divisions division = DivisionsDAO.getDivision(selectedCustomer.getDivisionId());
            DivisionCombo.setValue(division);

             */
    }
}
