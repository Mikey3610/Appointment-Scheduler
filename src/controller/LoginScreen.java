package controller;

import DBAccess.UserDAO;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
        if(UserDAO.validatedUser(userName, password)){
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainAppointmentScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 970, 450);
            stage.setTitle("Main Appointment Screen");
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("IncorrectLogin"));
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
