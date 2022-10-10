package com.company;

import DBAccess.DBCountries;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    public static void main(String[] args) {

        JDBC.openConnection();
        DBCountries.checkDateConversion();
        launch(args);
        JDBC.closeConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        //stage.setTitle("Login Screen");
        stage.setScene(new Scene(root, 580, 280));
        stage.show();
    }
}
