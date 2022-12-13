package model;

/** This class stores the contact list objects stored in the database. */
public class ContactList {
    private String type;
    private String month;
    private int total;

    /** Constructor of the contact list.
     * @param type The appointment type.
     * @param month The month of the appointment.
     * @param total Total count of the appointment types per month. */
    public ContactList(String type, String month, int total){
        this.type = type;
        this.month = month;
        this.total = total;
    }

    /** Getter for the appointment type for the contact list.
     * @return Returns the appointment type.
     * */
    public String getType() {
        return type;
    }

    /** Setter for the type of appointment.
     * @param type The appointment type.
     * */
    public void setType(String type) {
        this.type = type;
    }

    /** The getter for the month of the appointment.
     * @return Returns the month of the appointment.
     * */
    public String getMonth() {
        return month;
    }

    /** The setter of the appointment month.
     * @param month The appointment's month.
     * */
    public void setMonth(String month) {
        this.month = month;
    }

    /** Getter for the total months per type of appointment.
     * @return Returns the total count of appointments per type.
     * */
    public int getTotal() {
        return total;
    }

    /** Setter for the total months per type of appointment.
     * @param total Sets the total count of appointments per type.
     * */
    public void setTotal(int total) {
        this.total = total;
    }
}
