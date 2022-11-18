package controller;

import DBAccess.*;
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
import java.time.*;
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
        try {

            LocalDateTime start = LocalDateTime.of(apptStartDateDatePicker.getValue(), apptStartCombo.getValue());
            LocalDateTime end = LocalDateTime.of(apptStartDateDatePicker.getValue(), apptEndCombo.getValue());

            ZoneId zid = ZoneId.systemDefault();
            ZonedDateTime zStartTime = start.atZone(zid);
            ZonedDateTime zendTime = end.atZone(zid);

            ZonedDateTime ESTStartZoneTime = zStartTime.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime ESTEndZoneTime = zendTime.withZoneSameInstant(ZoneId.of("America/New_York"));

            ZonedDateTime startBusinessHours = ZonedDateTime.of(start.toLocalDate(), LocalTime.of(8,0), ZoneId.of("America/New_York"));
            ZonedDateTime endBusinessHours = startBusinessHours.plusHours(14);

            if (ESTStartZoneTime.isBefore(startBusinessHours) || ESTEndZoneTime.isAfter(endBusinessHours)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose a time between 8am - 10pm EST for the appointment.");
                alert.showAndWait();
                return;
            }

            String title = apptTitleText.getText();
            String description = apptDescriptionText.getText();
            String location = apptLocationText.getText();
            String type = apptTypeText.getText();
            Contacts contact = apptContactCombo.getValue();
            Customers customerId = apptCustIdCombo.getValue();
            User user = apptUserIdCombo.getValue();

            Timestamp startTS = Timestamp.valueOf(start);
            Timestamp endTS = Timestamp.valueOf(end);

            if (title.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Title is empty. Please input a title.");
                alert.showAndWait();
                return;
            } else if (description.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Description is empty. Please input a description.");
                alert.showAndWait();
                return;
            } else if (location.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Location is empty. Please input a location.");
                alert.showAndWait();
                return;
            } else if (type.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment type is empty. Please input a type.");
                alert.showAndWait();
                return;
            } else if (contact == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Contact has not been selected. Please select a contact");
                alert.showAndWait();
                return;
            } else if (customerId == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Customer ID has not been selected. Please select a customer ID.");
                alert.showAndWait();
                return;
            } else if (user == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "User ID has not been selected. Please select a user ID.");
                alert.showAndWait();
                return;
            }

            int custId = apptCustIdCombo.getValue().getCustomerId();
            int apptId = Integer.parseInt(apptIdText.getText());
            for (Appointments appt : AppointmentsDAO.selectAllAppointments()){
                if (appt.getCustomerId() != custId || appt.getAppointmentId() == apptId){
                    continue;
                }
                if ((start.isAfter(appt.getStartDateTime().toLocalDateTime()) || start.isEqual(appt.getStartDateTime().toLocalDateTime())) && start.isBefore(appt.getEndDateTime().toLocalDateTime())){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. Start time is conflicting with another appointment.");
                    alert.showAndWait();
                    return;
                }
                if (end.isAfter(appt.getStartDateTime().toLocalDateTime()) && (end.isEqual(appt.getEndDateTime().toLocalDateTime()) || end.isBefore(appt.getEndDateTime().toLocalDateTime()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. End time is conflicting with another appointment.");
                    alert.showAndWait();
                    return;
                }
                if ((start.isBefore(appt.getStartDateTime().toLocalDateTime()) || start.isEqual(appt.getStartDateTime().toLocalDateTime()) && (end.isAfter(appt.getEndDateTime().toLocalDateTime()) || end.isEqual(appt.getEndDateTime().toLocalDateTime())))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment conflict. Appointment start and end times overlap with another appointment.");
                    alert.showAndWait();
                    return;
                }
            }

            if (apptStartCombo.getValue().isAfter(apptEndCombo.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Start time must be before end time.");
                alert.showAndWait();
                return;
            }
            if (apptStartDateDatePicker.getValue().isAfter(apptEndDateDatePicker.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Start date must be before end date.");
                alert.showAndWait();
                return;
            }

            AppointmentsDAO.insertAppointment(apptUserIdCombo.getValue().getId(), apptTitleText.getText(), apptDescriptionText.getText(),
                    apptLocationText.getText(), apptContactCombo.getValue().getContactId(), apptTypeText.getText(), startTS, endTS, apptCustIdCombo.getValue().getCustomerId(), 0);

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainAppointmentScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 600);
            stage.setTitle("Appointment Screen");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Form contains invalid input values or blanks. Please check and input proper values.");
            alert.showAndWait();
            //e.printStackTrace();
        }


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

            apptStartCombo.setItems(AddAppointment.apptTimesList());
            apptEndCombo.setItems(AddAppointment.apptTimesList());

            //apptCustIdCombo.setPromptText("Customer ID");
            //apptUserIdCombo.setPromptText("User ID");

            apptContactCombo.setItems(ContactsDAO.selectAllContacts());
            Contacts contact = ContactsDAO.getContactById(selectedAppointment.getContactId());
            apptContactCombo.setValue(contact);

            apptCustIdCombo.setItems(CustomersDAO.selectAllCustomers());
            Customers customer = CustomersDAO.getCustomerById(selectedAppointment.getCustomerId());
            apptCustIdCombo.setValue(customer);

            apptUserIdCombo.setItems(UserDAO.selectAllUsers());
            User user = UserDAO.getUserById(selectedAppointment.getUserId());
            apptUserIdCombo.setValue(user);





        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
