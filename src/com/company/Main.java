package com.company;

import DBAccess.CountriesDAO;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.*;

/** This class creates an app that will allow a user to schedule appointments with different parameters and accurately keep track of the times that are input.*/
public class Main extends Application {
//comment
    /** This is the main method.
     * This is the first method that gets called when you run this Java program.
     * It will connect to the SQL database that is used to keep track of, update, delete, and modify data.
     * @param args The main method arguments.
     * */
    public static void main(String[] args) {

        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();

        /*
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldtIn = utczdt.toLocalDateTime();

        ZonedDateTime zdtOut = ldtIn.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtOutToLocalTZ = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldtOutFinal = zdtOutToLocalTZ.toLocalDateTime();

        System.out.println(ts);
        System.out.println(ldt);
        System.out.println(zdt);
        System.out.println(utczdt);
        System.out.println(ldtIn);
        System.out.println(zdtOut);
        System.out.println(zdtOutToLocalTZ);
        System.out.println(ldtOutFinal);
         */
    }

    /** This method will load the initial login screen to the user when the app starts.
     * @param stage This is the stage for the first screen of the app titled "LoginScreen".
     * */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        //stage.setTitle("Login Screen");
        stage.setScene(new Scene(root, 580, 280));
        stage.show();
    }
}
