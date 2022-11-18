package controller;

import DBAccess.AppointmentsDAO;
import DBAccess.UserDAO;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.time.ZoneId;
import java.time.LocalDateTime;


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

    public void onLogin(ActionEvent actionEvent) throws IOException, SQLException {
        String userName = UserNameText.getText();
        String password = PasswordText.getText();


        LocalDateTime nowTime = LocalDateTime.now();
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
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Upcoming appointment within 15 minutes." + "\nAppointment ID: " + appt.getAppointmentId() + "\nDate & Time: " + appt.getStartDateTime());
                    alert.showAndWait();
                    apptFound = true;
                }
            }
            if (!apptFound){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No upcoming appointments.");
                alert.showAndWait();
            }


            Parent root = FXMLLoader.load(getClass().getResource("/view/MainAppointmentScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 600);
            stage.setTitle("Main Appointments Screen");
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("Incorrect Login credentials."));
            alert.showAndWait();
        }

    }

    public void onExit(ActionEvent actionEvent) {
            System.exit(0);
    }


    public void onClear(ActionEvent actionEvent) {
        UserNameText.clear();
        PasswordText.clear();
    }
}
