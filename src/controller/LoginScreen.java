package controller;

import DBAccess.AppointmentsDAO;
import DBAccess.UserDAO;
import Lambdas.ConcatMessage;
import Lambdas.CurrentTimeLambda;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.User;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.time.ZoneId;
import java.time.LocalDateTime;

/** This class brings up the login screen for the user to input their credentials and either successfully or unsuccessfully connect to the database.*/
public class LoginScreen implements Initializable {

    public Label UserName;
    public Label Password;
    public Label TimeZone;
    public Label LoginScreenLabel;
    public Label ZoneLabel;
    public TextField UserNameText;
    public TextField PasswordText;
    public Button LoginBtn;
    public Button ExitBtn;
    public Button ClearBtn;
    ResourceBundle rb = ResourceBundle.getBundle("language_properties/Lang", Locale.getDefault());

    /** This method takes each of the fields on the login screen and changes their language depending on the local machine's region.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);

        ZoneId zid = ZoneId.systemDefault();
        ZoneLabel.setText(String.valueOf(zid));

        LoginScreenLabel.setText(rb.getString("LoginScreenLabel"));
        UserName.setText(rb.getString("UserName"));
        Password.setText(rb.getString("Password"));
        LoginBtn.setText(rb.getString("LoginBtn"));
        ClearBtn.setText(rb.getString("ClearBtn"));
        ExitBtn.setText(rb.getString("ExitBtn"));
        TimeZone.setText(rb.getString("TimeZone"));
    }

    /** This method validates the user's input credentials and connects them to the database and brings them to the main appointment screen upon success.
     * It also will log the login attempts, whether successful or not and at what time they were made.
     * @param actionEvent This action validates the input credentials to login to the app.
     * @return lambda CurrentTimeLambda will take the current local date time which is then used to check for any upcoming appointments.
     * @return lambda ConcatMessage will concatenate the appointment ID and Date & Time notification to the user for appointments upcoming within 15 minutes.
     * */
    public void onLogin(ActionEvent actionEvent) throws IOException, SQLException {
        String userName = UserNameText.getText();
        String password = PasswordText.getText();


        //lambda that will convert to localDateTime
        CurrentTimeLambda localDateTime = now -> {
            LocalDateTime rightNow = LocalDateTime.now();
            return rightNow;
        };


        LocalDateTime nowTime = localDateTime.currentTime(localDateTime);//Changed from assigning before lambda = LocalDateTime.now();
        LocalDateTime plusFifteenTime = nowTime.plusMinutes(15);

        User loginUser = UserDAO.loginUser(userName, password);
        if(loginUser != null){

            boolean apptFound = false;
            for (Appointments appt : AppointmentsDAO.selectAllAppointments()) {
                if (loginUser.getId() != appt.getUserId()){
                    continue;
                }
                if (appt.getStartDateTime().toLocalDateTime().isAfter(nowTime) &&
                        (appt.getStartDateTime().toLocalDateTime().isBefore(plusFifteenTime))) {
                    ConcatMessage loginAlert = s -> {
                        String message = "Upcoming appointment within 15 minutes." + "\nAppointment ID: " + appt.getAppointmentId() + "\nDate & Time: " + appt.getStartDateTime();
                        return message;
                    };
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, loginAlert.LoginAlert(loginAlert));// "Upcoming appointment within 15 minutes." + "\nAppointment ID: " + appt.getAppointmentId() + "\nDate & Time: " + appt.getStartDateTime());
                    alert.showAndWait();
                    apptFound = true;
                }
            }
            if (!apptFound){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No upcoming appointments.");
                alert.showAndWait();
            }
            logFile(userName, true);
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainAppointmentScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 600);
            stage.setTitle("Main Appointments Screen");
            stage.setScene(scene);
            stage.show();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("IncorrectLogin"));
            alert.showAndWait();
            logFile(userName, false);
        }

    }

    /** This method exits and closes the app. */
    public void onExit(ActionEvent actionEvent) {
            System.exit(0);
    }

    /** This method clears out any input from the fields in the screen.
     * @param actionEvent This action clears the input made by the user.
     * */
    public void onClear(ActionEvent actionEvent) {
        UserNameText.clear();
        PasswordText.clear();
    }

    /** This class takes all login attempts and stores them in a text file.
     * The stored info shows whether the login attempt was successful or not and at what time the login was attempted.
     * @param loginSuccessful Returns a true value if the login credentials were successful and the user could connect.
     * @param userName Takes the username from the input from the user and stores that in the text log file.
     * */
    public void logFile(String userName, boolean loginSuccessful) throws IOException {
        String filename ="login_activity.txt", item;
        Scanner keyboard = new Scanner(System.in);
        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwriter);
        if (loginSuccessful == true) {
            outputFile.println("User " + userName + " successfully logged in at " + LocalDateTime.now());
        }
        else {
            outputFile.println("User " + userName + " made an invalid login at " + LocalDateTime.now());
        }
        outputFile.close();
        System.out.println("File written.");


    }
}
