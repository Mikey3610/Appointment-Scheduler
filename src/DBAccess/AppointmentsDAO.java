package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.ContactList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/** This class has all of the SQL commands used by the app for accessing and making any changes to the database for appointments. */
public abstract class AppointmentsDAO {

    /**Method that utilizes SQL commands to insert appointment into Appointments object to be displayed on MainAppointment Screen.
     * @param userId The user ID for the appointment.
     * @param title The title for the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param contactId The contact ID to be used for the appointment.
     * @param type The type of appointment.
     * @param start The start (timestamp) of the appointment.
     * @param end The end (timestamp) of the appointment.
     * @param customerId The customer ID for the appointment.
     * */
    public static int insertAppointment(int userId, String title, String description, String location, int contactId, String type, Timestamp start, Timestamp end, int customerId) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (User_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setInt(5, contactId);
        ps.setString(6, type);
        ps.setTimestamp(7, start);
        ps.setTimestamp(8, end);
        ps.setInt(9, customerId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**Method that utilizes SQL commands to update appointment in the Appointments table of the database.
     * @param customerId The customer ID for the appointment.
     * @param end The end (timestamp) of the appointment.
     * @param start The start (timestamp) of the appointment.
     * @param type The type of appointment.
     * @param contactId The contact ID to be used for the appointment.
     * @param location The location of the appointment.
     * @param description The description of the appointment.
     * @param appointmentId The appointment ID of the appointment.
     * @param title The title of the appointment.
     * @param userId The user ID of the appointment.
     * @return Returns the row of newly updated data in the MySQL database.
     * */
    public static int updateAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId, int appointmentId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.setInt(10, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    //Using CustomersDAO updateCustomer to revise updateAppointment
    /*
    public static int updateCustomer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        ps.setInt(6, customerId);
        // Way to debug
        // System.out.println(ps.toString());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
     */

    /** This method uses SQL code to delete selected appointments from the database within the app.
     * @param appointmentId Appointment ID of the appointment where the deletion will occur.
     * @return Returns the SQL data after deleting the row.
     * */
    public static int deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This method uses SQL code to select all of the available appointments in the database.
     * @return Returns all of the appointments in an appointments object.
     * */
    public static ObservableList<Appointments> selectAllAppointments() throws SQLException {
        //String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID FROM appointments;";
        String sql = "SELECT * FROM appointments;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        while(rs.next()){
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp startDateTime = rs.getTimestamp("Start");
            Timestamp endDateTime = rs.getTimestamp("End");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");

            Appointments appointments = new Appointments(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId);
            allAppointments.add(appointments);

        }
        return allAppointments;
    }

    /** This method uses SQL commands to select appointments within the current month.
     * @return Returns all of the appointments that fall within the current month.
     * */
    public static ObservableList<Appointments> getAppointmentsByMonth() throws SQLException {
            String SQL = "SELECT * FROM APPOINTMENTS WHERE MONTH(start) = MONTH(CURRENT_DATE()) AND YEAR(start) = YEAR(CURRENT_DATE())";
            PreparedStatement ps = JDBC.connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery(SQL);
            ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startDateTime = rs.getTimestamp("Start");
                Timestamp endDateTime = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int UserId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments a = new Appointments(appointmentId, title, description, location, type, startDateTime,
                        endDateTime, customerId, UserId, contactId);
                allAppointments.add(a);
            }
        return allAppointments;
    }

    /** This method uses SQL commands to select appointments within the current week.
     * @return Returns all of the appointments that fall within the current week.
     * */
    public static ObservableList<Appointments> getAppointmentsByWeek() throws SQLException {

        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
            String SQL = "SELECT * FROM APPOINTMENTS WHERE WEEK(start) = WEEK(CURRENT_DATE()) AND YEAR(start) = YEAR(CURRENT_DATE())";
            PreparedStatement ps = JDBC.connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery(SQL);

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startDateTime = rs.getTimestamp("Start");
                Timestamp endDateTime = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int UserId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments a = new Appointments(appointmentId, title, description, location, type, startDateTime,
                        endDateTime, customerId, UserId, contactId);
                allAppointments.add(a);
            }
        return allAppointments;
    }

    /** This method uses SQL commands to retrieve the total number of appointments sorted by appointment type and month of the appointment.
     * @return Returns the type and month in a array object.
     * */
    public static ObservableList<ContactList> getAppointmentsByTypeAndMonth() throws SQLException {
        String SQL = "SELECT Type, MONTHNAME(Start) AS Month, COUNT(Type) AS Total FROM APPOINTMENTS GROUP BY Type, MONTHNAME(Start)";
        PreparedStatement ps = JDBC.connection.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery(SQL);
        ObservableList<ContactList> typeMonth = FXCollections.observableArrayList();

        while (rs.next()) {
            String type = rs.getString("Type");
            String month = rs.getString("Month");
            int total = rs.getInt("Total");

            ContactList a = new ContactList(type, month, total);
            typeMonth.add(a);
        }
        return typeMonth;
    }

    /** This method selects the Contact to get info in the reports menu.
     * @return Returns the contacts array object.
     * */
    public static ObservableList<Appointments> selectContact(int contactID) throws SQLException {
        String SQL = "SELECT * FROM appointments where Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(SQL);
        ps.setInt(1,contactID);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointments> contacts = FXCollections.observableArrayList();

        while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                Timestamp startDateTime = rs.getTimestamp("Start");
                Timestamp endDateTime = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointments selectedContact = new Appointments(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId);
                contacts.add(selectedContact);
        }

        return contacts;
    }

}
