package model;

/** This class stores all of the User objects used in the app. */
public class User {
    private int userId;
    private String userName;
    private String password;

    /** Constructor for the User objects.
     * @param userName The user's username.
     * @param userId The user's user ID.
     * @param password the user's password.
     * */
    public User(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /** The gettter for the user ID.
     * @return Returns the user ID.
     * */
    public int getId() {
        return userId;
    }

    /** The setter for the user ID.
     * @param userId The user ID.
     * */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** Getter for the user's password.
     * @return Returns the user's password.
     * */
    public String getPassword() {
        return password;
    }

    /** Setter for the user's password.
     * @param password the user's password.
     * */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Getter for the user's username.
     * @return Returns the user's username.
     * */
    public String getUserName() {
        return userName;
    }

    /** Setter for the user's username.
     * @param userName The user's username.
     * */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** This method returns the username as a String for the app.
     * @return Returns the username as a String.
     * */
    @Override
    public String toString() {
        return(getUserName());
    }
}
