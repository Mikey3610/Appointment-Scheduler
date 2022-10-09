package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainAppointmentScreen implements Initializable {

    public Label ApptOrCust;
    public Button AddApptOrCust;
    public Button ModifyApptOrCust;
    public Button DeleteApptOrCust;
    public ToggleGroup tGroupCustOrAppt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onExit(ActionEvent actionEvent) {
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
        exit.setTitle("Confirm Exit");
        exit.setContentText("Do you want to exit?");
        Optional<ButtonType> result = exit.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void onViewWeek(ActionEvent actionEvent) {
        ApptOrCust.setText("Appointments");
        AddApptOrCust.setText("Add Appointment");
        ModifyApptOrCust.setText("Change Appointment");
        DeleteApptOrCust.setText("Delete Appointment");
    }

    public void onViewMonth(ActionEvent actionEvent) {
        ApptOrCust.setText("Appointments");
        AddApptOrCust.setText("Add Appointment");
        ModifyApptOrCust.setText("Change Appointment");
        DeleteApptOrCust.setText("Delete Appointment");
    }

    public void onViewAll(ActionEvent actionEvent) {
        ApptOrCust.setText("Appointments");
        AddApptOrCust.setText("Add Appointment");
        ModifyApptOrCust.setText("Change Appointment");
        DeleteApptOrCust.setText("Delete Appointment");
    }

    public void onViewCust(ActionEvent actionEvent) {
        ApptOrCust.setText("Customer View");
        AddApptOrCust.setText("Add Customer");
        ModifyApptOrCust.setText("Change Customer");
        DeleteApptOrCust.setText("Delete Customer");
    }

    public void onAddApptOrCust(ActionEvent actionEvent) throws IOException {
        if (AddApptOrCust.equals("Add Appointment")){
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 640);
            stage.setTitle("Add Appointment Form");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void onModifyApptOrCust(ActionEvent actionEvent) {
    }

    public void onDeleteApptOrCust(ActionEvent actionEvent) {
    }



}
