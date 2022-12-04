package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class ReportsMenu {
        public void onBack(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainAppointmentScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 600);
            stage.setTitle("Appointments Screen");
            stage.setScene(scene);
            stage.show();
    }

    public void toMainAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainAppointmentScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 970, 450);
        stage.setTitle("Main Appointment Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void onTotalCustAppts(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ApptsTypeMonthReport.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Total Customer Reports");
        stage.setScene(scene);
        stage.show();
    }

    public void onContactSchedule(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ContactsReport.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Contacts Schedule");
        stage.setScene(scene);
        stage.show();
    }

    public void onCustIdReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerIDReport.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Reports by Customer ID");
        stage.setScene(scene);
        stage.show();
    }
}
