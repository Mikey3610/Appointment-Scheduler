package model;

public class CustomerIDReport {
    private int customerId;
    private int total;

    public CustomerIDReport(int customerId, int total) {
        this.customerId = customerId;
        this.total = total;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
