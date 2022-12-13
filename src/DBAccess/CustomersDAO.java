package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/** This class has all of the SQL commands used by the app for accessing and making any changes to the database for customers. */
public abstract class CustomersDAO {

    /**Method that utilizes SQL commands to insert customer into Customers object to be displayed on Customer Screen.
     * @param address The customer's address.
     * @param customerName The customer's name.
     * @param divisionId The division ID of the customer.
     * @param phone The phone number of the customer.
     * @param postalCode The postal code of the customer.
     * @return Returns the row of data inserted into the MySQL database.
     * */
    public static int insertCustomer(String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** Method that utilizes SQL commands to update customer in the Customers table of the database.
     * @param postalCode Postal code of the customer
     * @param phone The phone number of the customer.
     * @param customerName The customer's name.
     * @param divisionId The customer's division (region) ID.
     * @param address The customer's address.
     * @param customerId The customer's ID.
     * @return Returns the row of updated data to the customer row from the database.
     * */
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

    /** This method deletes the customer row selected at the specific customer ID in the database.
     * @param customerId The customer ID.
     * @return Returns the deleted customer row.
     * */
    public static int deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This method uses SQL code to select all of the available customers in the database.
     * @return Returns all of the customers in an customers object.
     * */
    public static ObservableList<Customers> selectAllCustomers() throws SQLException {
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Country_ID FROM customers, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            int countryId = rs.getInt("Country_ID");

            Customers customers = new Customers(customerId, customerName, address, postalCode, phone, divisionId, countryId);
            allCustomers.add(customers);

        }
        return allCustomers;
    }

    public static Customers selectCustomer(int customerId) throws SQLException {
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Country_ID FROM customers, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID AND Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String customerName = rs.getString("Customer_Name");
            String phone = rs.getString("Phone");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            int divisionId = rs.getInt("Division_ID");
            int countryId = rs.getInt("Country_ID");

            Customers customer = new Customers(customerId, customerName, phone, address, postalCode, divisionId, countryId);
            return customer;
        }
        return null;
    }

    /** This method retrieves the Customer by specific ID.
     * @param customerId The customer ID.
     * @return Returns the customer object.
     * */
    public static Customers getCustomerById(int customerId) {
        try {
            String SQL = "SELECT C.*, D.Country_ID AS Country_ID FROM customers AS C, countries AS D, first_level_divisions AS E\n" +
                    "WHERE D.Country_ID = E.Country_ID\n" +
                    "AND E.Division_ID = C.Division_ID AND Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(SQL);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                int countryId = rs.getInt("Country_ID");
                Customers customer = new Customers(customerId, customerName, address, postalCode, phone, divisionId, countryId);
                return customer;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /** This method retrieves the totals by each of the customer IDs in the database.
     * @returns Returns a count of the customer IDs.
     * */
    public static ObservableList<CustomerIDReport> getCustomerIdCount() throws SQLException {
        String SQL = "SELECT Customer_ID, COUNT(Customer_ID) AS Total FROM APPOINTMENTS GROUP BY Customer_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery(SQL);
        ObservableList<CustomerIDReport> custIdCount = FXCollections.observableArrayList();

        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            int total = rs.getInt("Total");

            CustomerIDReport a = new CustomerIDReport(customerId, total);
            custIdCount.add(a);
        }
        return custIdCount;
    }
}
