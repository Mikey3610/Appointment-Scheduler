package helper;

import Lambdas.WelcomeMessage;

import java.sql.Connection;
import java.sql.DriverManager;

/** This method allows the user to connect to the MySQL database which stores all of the appointments/customer data. */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /** This method makes the connection to the MySQL database. */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
            WelcomeMessage message = s ->
            {
                String welcome = "You are now connected to the database";
                return welcome;
            };
            System.out.println(message.welcomeMessage(message));
        }
        catch(Exception e)
        {
            //System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /** This method returns the connection to the MySQL database.
     * @return Returns the connection.
     * */
    public static Connection getConnection(){
        return connection;
    }

    /** This method closes the database upon exit. */
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

}
