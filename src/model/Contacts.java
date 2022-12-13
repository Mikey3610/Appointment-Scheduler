package model;

/** This class stores all of the contacts objects used in the app. */
public class Contacts {
    private int contactId;
    private String contactName;
    private String email;

    /** Constructor of the contacts objects stored in the database of the app.
     * @param contactId The contact ID.
     * @param contactName The contact name.
     * @param email The contact's email.
     * */
    public Contacts(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /** Getter for the contact ID.
     * @return Returns contact ID.
     * */
    public int getContactId() {
        return contactId;
    }

    /** Setter for the contact ID.
     * @param contactId The contact ID.
     * */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** Getter for the contact name.
     * @return Returns the contact name.
     * */
    public String getContactName() {
        return contactName;
    }

    /** Setter for the contact name.
     * @param contactName The contact's name.
     * */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** Getter for the contact's email.
     * @return returns the contact's email.
     * */
    public String getEmail() {
        return email;
    }

    /** Setter for the contact's email.
     * @param email The contact's email.
     * */
    public void setEmail(String email) {
        this.email = email;
    }

    /** This method returns the contact name as a string to display to the user in the app.
     * @return Returns the Contact Name as a String.
     * */
    @Override
    public String toString() {
        return(getContactName());
    }
}
