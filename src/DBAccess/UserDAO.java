package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
