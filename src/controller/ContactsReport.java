package controller;

import DBAccess.AppointmentsDAO;
import DBAccess.ContactsDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** This class will display the contact party's information from within the stored appointment information.*/
public class ContactsReport implements Initializable {

    public TableView contactsTable;
    public ComboBox<Contacts> selectContact;
    public TableColumn apptIDCol;
    public TableColumn titleCol;
    public TableColumn typeCol;
    public TableColumn descriptionCol;
    public TableColumn startDateCol;
    public TableColumn endDateCol;
    public TableColumn custIdCol;

    /** This method will return the user back to the Reports menu.
     * @param actionEvent Returns the user to the Reports menu screen.
     * */
    public void onBackToReportsMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Reports Menu");
        stage.setScene(scene);
        stage.show();
    }

    /** This method autopopulates this screen with the corresponding contact data from the appointments.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            selectContact.setItems(ContactsDAO.selectAllContacts());
            contactsTable.setItems(AppointmentsDAO.selectAllAppointments());

            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /** This method will retrieve the contact ID from the contact data in the database.
     * @param actionEvent Retrieves the contact ID info for the user to select from.
     * */
    public void onSelectContact(ActionEvent actionEvent) throws SQLException {
        //appointmentTable.setItems(AppointmentsCRUD.getContactAppointments(contactComboBox.getValue().getContactId()));
        contactsTable.setItems(AppointmentsDAO.selectContact(selectContact.getValue().getContactId()));
    }
}
