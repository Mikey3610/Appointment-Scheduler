package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class has all of the SQL commands used by the app for accessing and making any changes to the database for users (login users). */
public abstract class UserDAO {

    public static boolean validatedUser(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name= ? AND Password = ?";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, password);
        ResultSet rs = preparedStatement.executeQuery();
        boolean validatedUser = false;
        if(rs.next()){
            validatedUser = true;
        }
        return validatedUser;
    }

    /** This method validates the login credentials to either successfully or unsuccessfully access the app's main appointment screen.
     * @param userName The user's username.
     * @param password The user's password.
     * @return Returns the corresponding username and password from the database.
     * */
    public static User loginUser(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name= ? AND Password = ?";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, password);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            int userId = rs.getInt("User_ID");
            //String password = rs.getString("Password");
            User loginUser = new User(userId, userName, "");
            return loginUser;
        }
        return null;
    }

    /** This method selects all of the users in the database.
     * @return Returns all users.
     * */
    public static ObservableList<User> selectAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");

            User user = new User(userId, userName, password);
            allUsers.add(user);

        }
        return allUsers;
    }

    /** This method selects the user by their specific ID.
     * @param userId The user's ID.
     * @return Returns the user object.
     * */
    public static User getUserById(int userId) {
        try {
            String SQL = "SELECT * FROM users WHERE User_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(SQL);
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                User user = new User(userId,userName, password);
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


}
