package customer;

public class Payment {
    private int paymentID;
    private String paymentType;
    private double amount;
    private int customerID; // Foreign Key
    private int stationID;  // Foreign Key

    // Constructor
    public Payment(int paymentID, String paymentType, double amount, int customerID, int stationID) {
        this.paymentID = paymentID;
        this.paymentType = paymentType;
        this.amount = amount;
        this.customerID = customerID;
        this.stationID = stationID;
    }

    // Getters and Setters
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }
}
