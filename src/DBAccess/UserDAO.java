package DBAccess;

import helper.JDBC;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDAO {

    public static User validateUser(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name= ? AND Password = ?";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, password);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            int userId = rs.getInt("User_ID");
            User validatedUser = new User(userId, userName, password);
            return validatedUser;
        }
        return null;
    }
}
