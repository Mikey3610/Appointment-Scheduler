package controller;

import DBAccess.AppointmentsDAO;
import DBAccess.CustomersDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class shows all of the appointments stored in the system and allows the user to make multiple choices for editing or going to other pages in the app.
 * */
public class MainAppointmentScreen implements Initializable {

    public ToggleGroup tGroupAppts;
    public TableView <Appointments> appointmentsTable;
    public TableColumn appointmentIdCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactIdCol;
    public TableColumn typeCol;
    public TableColumn startDateTimeCol;
    public TableColumn endDateTimeCol;
    public TableColumn customerIdCol;
    public TableColumn userIdCol;
    public static Appointments appointmentToModify;

    /** This method initializes the Main Appointments screen with all of the appointments data from the database into a table for the user to see.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentsTable.setItems(AppointmentsDAO.selectAllAppointments());

            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

            //AppointmentsDAO.insertAppointment(1, "test", "test","place",1,"test", Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)), 1, 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method will exit the program.
     * @param actionEvent This action closes and exits the program.
     * */
    public void onExit(ActionEvent actionEvent) {
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
        exit.setTitle("Confirm Exit");
        exit.setContentText("Do you want to exit?");
        Optional<ButtonType> result = exit.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /** This method will filter all appointments to the current week's appointments. */
    public void onViewWeek(ActionEvent actionEvent) throws SQLException {
        appointmentsTable.setItems(AppointmentsDAO.getAppointmentsByWeek());
    }

    /** This method will filter all appointments to the current month's appointments. */
    public void onViewMonth(ActionEvent actionEvent) throws SQLException {
        appointmentsTable.setItems(AppointmentsDAO.getAppointmentsByMonth());
    }

    /** This method will filter to see all appointments. */
    public void onViewAll(ActionEvent actionEvent) throws SQLException {
        appointmentsTable.setItems(AppointmentsDAO.selectAllAppointments());
    }

    /** This method brings the user to the Add Appointmnent screen.*/
    public void onAddAppt(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /** This method allows the user to select one of the appointments from the Main Appointments Screen and modify or delete it. */
    public static Appointments appointmentToModify() {
        return appointmentToModify;
    }

    /** This method will bring the user to the Modify Appointment screen if they have an appointment selected on the screen.
     * @param actionEvent Changes to the Modify Appointment screen.
     * */
    public void onModifyAppt(ActionEvent actionEvent) throws IOException {
        appointmentToModify = appointmentsTable.getSelectionModel().getSelectedItem();

        if (appointmentToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointments selected.");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 550);
            stage.setTitle("Modify Appointment");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This method deletes the selected appointment and its data from the database.
     * @param actionEvent This action deletes the selected appointment and its data.
     * */
    public void onDeleteAppt(ActionEvent actionEvent) throws SQLException {
        Appointments selectedAppt = appointmentsTable.getSelectionModel().getSelectedItem();
        int appointmentId = selectedAppt.getAppointmentId();
        String appointmentType = selectedAppt.getType();

        if (selectedAppt == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No appointment selected.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setContentText("Are you sure you want to delete this appointment?" + " Appointment ID: " + appointmentId + "\nType: " + appointmentType);
            Optional<ButtonType> confirmation = alert.showAndWait();

            if (confirmation.get() == ButtonType.OK) {
                AppointmentsDAO.deleteAppointment(selectedAppt.getAppointmentId());
                appointmentsTable.setItems(AppointmentsDAO.selectAllAppointments());
            }
        }
    }

    /** This method brings the user to the Add Customer screen.
     * @param actionEvent This actions changes the screen to the Add Customer screen.
     * */
    public void onCustTable(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /** This method brings the user to the Reports Menu screen.
     * @param actionEvent This action changes the screen to the Reports Menu screen.
     * */
    public void onReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Reports Menu");
        stage.setScene(scene);
        stage.show();
    }
}
