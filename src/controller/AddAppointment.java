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
        LocalTime start = LocalTime.of(8, 00);
        LocalTime end = LocalTime.of(20,00);
        while (start.isBefore(end.plusSeconds(1))) {
            times.add(start);
            start = start.plusMinutes(15);

        }
        return times;
    }

    public void onSaveAppt(ActionEvent actionEvent) throws IOException, SQLException {

        try {


            //
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
            LocalDateTime ldt = ts.toLocalDateTime();
            ZonedDateTime zdt = ldt.atZone(ZoneId.of(String.valueOf(ZoneId.systemDefault())));
            ZonedDateTime estzdt = zdt.withZoneSameInstant(ZoneId.of("America/New York"));
            LocalDateTime ldtIn = estzdt.toLocalDateTime();

            String title = TitleText.getText();
            String description = DescriptionText.getText();
            String location = LocationText.getText();
            String type = TypeText.getText();
            StartTimeCombo.getValue();
            EndTimeCombo.getValue();
            StartDateDatePicker.getValue();
            EndDateDatePicker.getValue();
            Contacts contact = ContactCombo.getValue();
            Customers customerId = CustIdCombo.getValue();
            User user = UserIdCombo.getValue();


            if (title.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Title is empty. Please input a title.");
                alert.showAndWait();
            } else if (description.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Description is empty. Please input a description.");
                alert.showAndWait();
            } else if (location.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Location is empty. Please input a location.");
                alert.showAndWait();
            } else if (type.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment type is empty. Please input a type.");
                alert.showAndWait();
            } else if (contact == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Contact has not been selected. Please select a contact");
                alert.showAndWait();
            } else if (customerId == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Customer ID has not been selected. Please select a customer ID.");
                alert.showAndWait();
            } else if (user == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "User ID has not been selected. Please select a user ID.");
                alert.showAndWait();
            }


            /*
            Timestamp startDate = Timestamp.valueOf(LocalDateTime.of(StartDateDatePicker.getValue(), StartTimeCombo.getValue()));
            Timestamp endDate = Timestamp.valueOf(LocalDateTime.of(StartDateDatePicker.getValue(), EndTimeCombo.getValue()));


            AppointmentsDAO.insertAppointment(UserIdCombo.getValue().getUserId(), TitleText.getText(), DescriptionText.getText(),
                    LocationText.getText(), ContactCombo.getValue().getContactId(), TypeText.getText(), startDate, endDate, CustIdCombo.getValue().getCustomerId());
             */




            Parent root = FXMLLoader.load(getClass().getResource("/view/MainAppointmentScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 600);
            stage.setTitle("Appointment Screen");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Form contains invalid input values or blanks. Please check and input proper values.");
            alert.showAndWait();
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
