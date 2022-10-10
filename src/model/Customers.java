package model;

public class Customers {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;

    public Customers(int customerId, String customerName, String address, String postalCode, String phone){
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }
}
