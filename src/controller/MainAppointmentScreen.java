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

    }

    public void onViewMonth(ActionEvent actionEvent) {

    }

    public void onViewAll(ActionEvent actionEvent) {

    }

    public void onViewCust(ActionEvent actionEvent) {

    }

    public void onAddAppt(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onModifyAppt(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 550);
        stage.setTitle("Modify Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteAppt(ActionEvent actionEvent) {
    }


    public void onCustTable(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Reports Menu");
        stage.setScene(scene);
        stage.show();
    }
}
