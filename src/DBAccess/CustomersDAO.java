package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomersDAO {

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

    public static int deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

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


    public static Customers getCustomerById(int customerId) {
        try {
            //"SELECT C.* FROM Countries AS C INNER JOIN First_Level_Divisions AS D ON C.COUNTRY_ID = D.COUNTRY_ID AND D.DIVISION_ID = ?"
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
}

    /*
     public static void selectAllCustomers(int customerId) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE Customer_ID =?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,"customerId");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerIdFK = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            System.out.print(customerIdFK + " | ");
            System.out.print(customerName + "\n");
        }
    }
}

     */
