package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/** This class displays the Reports menu to the user for them to choose the different types of reports. */
public class ReportsMenu {

    /** This method returns the user back to the Main Appointments Screen.
     * @param actionEvent This action returns the user to the Appointments Screen.
     * */
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

    /** This method takes the user to the Total Customer Reports screen.
     * @param actionEvent This action changes the screen to the Total Customer Reports screen.
     * */
    public void onTotalCustAppts(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ApptsTypeMonthReport.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Total Customer Reports");
        stage.setScene(scene);
        stage.show();
    }

    /** This method takes the user to the Contacts Schedule screen.
     * @param actionEvent This action changes the screen to the Contacts Schedule screen.
     * */
    public void onContactSchedule(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ContactsReport.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Contacts Schedule");
        stage.setScene(scene);
        stage.show();
    }

    /** This method takes the user to the Reports by Customer ID screen.
     * @param actionEvent This action changes the screen to the Reports by Customer ID screen.
     * */
    public void onCustIdReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerIDReport.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Reports by Customer ID");
        stage.setScene(scene);
        stage.show();
    }
}
