package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.TypeMonthTotal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class AppointmentsDAO {

    public static int insertAppointment(int userId, String title, String description, String location, int contactId, String type, Timestamp start, Timestamp end, int customerId, int Appointment_ID) throws SQLException {
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

    public static int updateAppointment(int appointmentId, String title, String description, String location, String type, Timestamp start, Timestamp end, int userId, int contactId, int customerId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Appointment_ID = ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setTimestamp(6, start);
        ps.setTimestamp(7, end);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.setInt(10, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

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

    public static ObservableList<TypeMonthTotal> getAppointmentsByTypeAndMonth() throws SQLException {
        //Need to fix this SQL command
        String SQL = "SELECT Type, MONTH(Start) AS Month, COUNT(Type) AS Total FROM APPOINTMENTS GROUP BY Type, MONTH(Start)";
        PreparedStatement ps = JDBC.connection.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery(SQL);
        ObservableList<TypeMonthTotal> typeMonth = FXCollections.observableArrayList();

        while (rs.next()) {
            String type = rs.getString("Type");
            String month = rs.getString("Month");
            int total = rs.getInt("Total");

            TypeMonthTotal a = new TypeMonthTotal(type, month, total);
            typeMonth.add(a);
        }
        return typeMonth;
    }




}
