package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class ReportsMenu {
        public void onBack(ActionEvent actionEvent) throws IOException {
            Alert cancellation = new Alert(Alert.AlertType.CONFIRMATION);
            cancellation.setTitle("Return to Main Screen");
            cancellation.setContentText("Go back?");
            Optional<ButtonType> result = cancellation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                toMainAppointmentScreen(actionEvent);
            }
    }

    public void toMainAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainAppointmentScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 970, 450);
        stage.setTitle("Main Appointment Screen");
        stage.setScene(scene);
        stage.show();
    }
}
