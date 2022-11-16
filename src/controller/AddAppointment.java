package controller;

import DBAccess.AppointmentsDAO;
import DBAccess.ContactsDAO;
import DBAccess.CustomersDAO;
import DBAccess.UserDAO;
import javafx.collections.FXCollections;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {

    public TextField ApptIdText;
    public TextField TitleText;
    public TextField DescriptionText;
    public TextField LocationText;
    public DatePicker StartDateDatePicker;
    public ComboBox<LocalTime> StartTimeCombo;
    public ComboBox<Contacts> ContactCombo;
    public TextField TypeText;
    public ComboBox<Customers> CustIdCombo;
    public ComboBox<User> UserIdCombo;
    public DatePicker EndDateDatePicker;
    public ComboBox<LocalTime> EndTimeCombo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartTimeCombo.setPromptText("Select start time...");
        EndTimeCombo.setPromptText("Select end time...");
        ContactCombo.setPromptText("Contact ID...");
        CustIdCombo.setPromptText("Customer ID...");
        UserIdCombo.setPromptText("User ID...");

        try {

            ContactCombo.setItems(ContactsDAO.selectAllContacts());
            CustIdCombo.setItems(CustomersDAO.selectAllCustomers());
            UserIdCombo.setItems(UserDAO.selectAllUsers());
            StartTimeCombo.setItems(AddAppointment.apptTimesList());
            EndTimeCombo.setItems(AddAppointment.apptTimesList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        /*
        //ZonedDateTime;
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(20, 0);

        while(start.isBefore(end.plusSeconds(1))){
            StartTimeCombo.getItems().add(start);
            start = start.plusMinutes(15);
            end = start.plusMinutes(15);
            EndTimeCombo.getItems().add(end);
            //create an end time that is less than 10pm EST based off of the ZonedDateTime
        }
        StartTimeCombo.getSelectionModel().select(LocalTime.of(8, 0));
        EndTimeCombo.getSelectionModel().select(LocalTime.of(8, 0));
        //add a validation for end time being after the start time
        */


    }

    public static ObservableList<LocalTime> apptTimesList() {
        ObservableList<LocalTime> times = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(1, 00);
        LocalTime end = LocalTime.of(23,00);
        while (start.isBefore(end.plusSeconds(1))) {
            times.add(start);
            start = start.plusMinutes(15);
        }
        return times;
    }

    public void onSaveAppt(ActionEvent actionEvent) throws IOException, SQLException {

        try {

            /*
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
            LocalDateTime ldt = ts.toLocalDateTime();
            ZonedDateTime zdt = ldt.atZone(ZoneId.of(String.valueOf(ZoneId.systemDefault())));
            ZonedDateTime estzdt = zdt.withZoneSameInstant(ZoneId.of("America/New York"));
            LocalDateTime ldtIn = estzdt.toLocalDateTime();
             */

            LocalDateTime start = LocalDateTime.of(StartDateDatePicker.getValue(), StartTimeCombo.getValue());
            LocalDateTime end = LocalDateTime.of(StartDateDatePicker.getValue(), EndTimeCombo.getValue());

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

            // DatePicker will give you a "LocalDate" and combo box gives a localtime object
            //LocalDate - datepicker
            //LocalTime - combobox
            //LocalDateTime (above 2 combined)
            //Combine Datepicker (LocalDate) and Comboboxes (LocalTimes) into LocalDateTime

            String title = TitleText.getText();
            String description = DescriptionText.getText();
            String location = LocationText.getText();
            String type = TypeText.getText();
            Contacts contact = ContactCombo.getValue();
            Customers customerId = CustIdCombo.getValue();
            User user = UserIdCombo.getValue();
            /*
            StartTimeCombo.getValue();
            EndTimeCombo.getValue();
            StartDateDatePicker.getValue();
            EndDateDatePicker.getValue();
            */

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

            if (StartTimeCombo.getValue().isAfter(EndTimeCombo.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Start time must be before end time.");
                alert.showAndWait();
                return;
            }
            if (StartDateDatePicker.getValue().isAfter(EndDateDatePicker.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Start date must be before end date.");
                alert.showAndWait();
                return;
            }

            if ((StartDateDatePicker.getValue().getDayOfWeek().equals(DayOfWeek.SATURDAY)) || StartDateDatePicker.getValue().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a business day between Monday and Friday.");
                alert.showAndWait();
                return;
            }

            //check for conflicting times
            int custId = CustIdCombo.getValue().getCustomerId();
            for (Appointments appt : AppointmentsDAO.selectAllAppointments()){
                if (appt.getCustomerId() != custId){
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


            AppointmentsDAO.insertAppointment(UserIdCombo.getValue().getUserId(), TitleText.getText(), DescriptionText.getText(),
                    LocationText.getText(), ContactCombo.getValue().getContactId(), TypeText.getText(), startTS, endTS, CustIdCombo.getValue().getCustomerId(), 0);

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

    public void onCancelAppt(ActionEvent actionEvent) throws IOException {
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


}
