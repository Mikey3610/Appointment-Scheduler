package model;

/** This class contacts the Customer ID objects to be used for the reports within the app. */
public class CustomerIDReport {
    private int customerId;
    private int total;

    /** The constructor of the customer ID report obejcts.
     * @param customerId The customer ID.
     * @param total The total count by customer ID.
     * */
    public CustomerIDReport(int customerId, int total) {
        this.customerId = customerId;
        this.total = total;
    }

    /** The getter for the customer ID.
     * @return Returns the customer ID.
     * */
    public int getCustomerId() {
        return customerId;
    }

    /** The setter for the customer ID.
     * @param customerId The customer ID to be set.
     * */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** Getter of the total by customer ID.
     * @return Returns the total count.
     * */
    public int getTotal() {
        return total;
    }

    /** Setter for the total appointments by customer ID.
     * @param total The total amount by customer ID.
     * */
    public void setTotal(int total) {
        this.total = total;
    }
}
