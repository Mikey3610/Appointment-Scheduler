package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import model.ContactList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class has all of the SQL commands used by the app for accessing and making any changes to the database for contacts. */
public abstract class ContactsDAO {
    /** This method uses SQL code to select all of the available contacts in the database.
     * @return Returns all of the contacts in an contacts array.
     * */
    public static ObservableList<Contacts> selectAllContacts() throws SQLException {
        String sql = "SELECT * FROM contacts;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        while(rs.next()){
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contacts contacts = new Contacts(contactId, contactName, email);
            allContacts.add(contacts);

        }
        return allContacts;
    }

    /** This method selects a contact by their corresponding ID.
     * @return Returns contact object.
     * */
    public static Contacts getContactById(int contactId) {
        try {
            String SQL = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(SQL);
            ps.setInt(1,contactId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contacts contact = new Contacts(contactId,contactName, email);
                return contact;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
