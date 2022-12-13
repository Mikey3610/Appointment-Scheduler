package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalTime;

/** This class stores all of the appointment objects used in the app. */
public class Appointments {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private int contactId;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private int customerId;
    private int userId;

    /** Constructor with all of the appointment parameters to be stored.
     * @param userId The user ID.
     * @param customerId The customer ID.
     * @param appointmentId The appointment ID.
     * @param title The appointment title.
     * @param description The appointment description.
     * @param location The appointment location.
     * @param type The type of appointment.
     * @param contactId The contact ID for the appointment.
     * @param endDateTime The end time of the appointment.
     * @param startDateTime The start time of the appointment.
     * */
    public Appointments(int appointmentId, String title, String description, String location, String type, Timestamp startDateTime, Timestamp endDateTime, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /** The getter for the appointment ID.
     * @return Returns the appointment ID. */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** The setter for the appointment ID.
     * @param appointmentId The appointment ID.
     * */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /** The getter for the appointment Title.
     * @return Returns the appointment title.
     * */
    public String getTitle() {
        return title;
    }

    /** The setter for the appointment title.
     * @param title The appointment title.
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /** The getter for the appointment description.
     * @return Returns the appointment description.
     * */
    public String getDescription() {
        return description;
    }

    /** The setter for the appointment description.
     * @param description The appointment description. */
    public void setDescription(String description) {
        this.description = description;
    }

    /** The getter for the appointment location.
     * @return Returns the appointment location.
     * */
    public String getLocation() {
        return location;
    }

    /** The setter for the appointment location.
     * @param location The appointment location.
     * */
    public void setLocation(String location) {
        this.location = location;
    }

    /** The getter for the appointment contact ID.
     * @return Returns the appointment's contact ID.
     * */
    public int getContactId() {
        return contactId;
    }

    /** The setter for the appointment's contact ID.
     * @param contactId The appointment's contact ID.
     * */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** The getter for the appointment's type.
     * @return Returns the appointment type.
     * */
    public String getType() {
        return type;
    }

    /** Setter for the appointment type.
     * @param type The appointment's type.
     * */
    public void setType(String type) {
        this.type = type;
    }

    /** The getter for the start date and time of the appointment.
     * @return Returns the start date and time of the appointment.
     * */
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    /** Setter for the start date and time of the appointment.
     * @param startDateTime The timestamp for the start date and time of the appointment.
     * */
    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    /** Getter for the end date and time of the appointment.
     * @return Returns the end date and time of the appointment.
     * */
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /** Setter for the end date and time of the appointment.
     * @param endDateTime The timestamp for the end date and time of the appoinment.
     * */
    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    /** The getter of the appointment's customer ID.
     * @return Returns the customer ID.
     * */
    public int getCustomerId() {
        return customerId;
    }

    /** Setter for the appointment's customer ID.
     * @param customerId The appointment's customer ID.
     * */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** Getter for the appointment's user ID.
     * @return Returns the user ID.
     * */
    public int getUserId() {
        return userId;
    }

    /** Setter for the appointment's user ID.
     * @param userId the User ID. */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** This method converts the contact ID's data into a string for display in the app.
     * @return Returns the integer value of the contact ID as a string.
     * */
    @Override
    public String toString() {

        return(Integer.toString(getContactId()));

    }
}
