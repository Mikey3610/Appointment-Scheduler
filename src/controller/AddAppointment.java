package controller;

import DBAccess.AppointmentsDAO;
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
import model.Contacts;
import model.Customers;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    }


    public void onSaveAppt(ActionEvent actionEvent) {
        try {

        } catch(Exception e) {
                e.printStackTrace();
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
