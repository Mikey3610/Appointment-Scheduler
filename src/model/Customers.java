package model;

/** This class stores all of the customers objects used in the app. */
public class Customers {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private int countryId;

    /** The constructor that creates all of the customers objects in the app.
     * @param customerId The customer ID.
     * @param countryId The country ID.
     * @param divisionId The division ID.
     * @param customerName The customer's name.
     * @param address The customer's address.
     * @param phone The customer's phone number.
     * @param postalCode The customer's postal code.
     * */
    public Customers(int customerId, String customerName, String address, String postalCode, String phone, int divisionId, int countryId){
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.countryId = countryId;
    }

    /** Getter for the customer ID.
     * @return Returns the customer ID.
     * */
    public int getCustomerId() {
        return customerId;
    }

    /** Getter for the customer name.
     * @return Returns the customer's name.
     * */
    public String getCustomerName() {
        return customerName;
    }

    /** Getter for the customer's address.
     * @return returns the customer's address.
     * */
    public String getAddress() {
        return address;
    }

    /** Getter for the customer's postal code.
     * @return Returns the postal code of the customer.
     * */
    public String getPostalCode() {
        return postalCode;
    }

    /** Getter for the customer's phone number.
     * @return Returns the customer's phone number.
     * */
    public String getPhone() {
        return phone;
    }

    /** Getter for the customer's division ID.
     * @return Returns the customer's division ID.
     * */
    public int getDivisionId() {
        return divisionId;
    }

    /** Getter for the country ID.
     * @return Returns the country ID.
     * */
    public int getCountryId() {return countryId;
    }

    /** This method returns the customer's name data as a string to be used in the app.
     * @return Returns the customer name.
     * */
    @Override
    public String toString() {
        return(getCustomerName());
    }
}
