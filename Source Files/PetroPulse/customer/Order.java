package customer;

public class Order {
    private int orderID;
    private String orderDate;
    private String time;
    private double amountOfFuel;
    private int customerID; // Foreign Key
    private int stationID;  // Foreign Key

    // Constructor
    public Order(int orderID, String orderDate, String time, double amountOfFuel, int customerID, int stationID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.time = time;
        this.amountOfFuel = amountOfFuel;
        this.customerID = customerID;
        this.stationID = stationID;
    }

    // Getters and Setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAmountOfFuel() {
        return amountOfFuel;
    }

    public void setAmountOfFuel(double amountOfFuel) {
        this.amountOfFuel = amountOfFuel;
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
