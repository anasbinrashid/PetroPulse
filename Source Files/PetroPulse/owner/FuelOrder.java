package owner;
public class FuelOrder {
    private int fuelOrderID;
    private double fuelAmount;
    private String fuelOrderDate;
    private int stationID; // Foreign Key
    private int supplierID; // Foreign Key

    // Constructor
    public FuelOrder(int fuelOrderID, double fuelAmount, String fuelOrderDate, int stationID, int supplierID) {
        this.fuelOrderID = fuelOrderID;
        this.fuelAmount = fuelAmount;
        this.fuelOrderDate = fuelOrderDate;
        this.stationID = stationID;
        this.supplierID = supplierID;
    }

    // Getters and Setters
    public int getFuelOrderID() {
        return fuelOrderID;
    }

    public void setFuelOrderID(int fuelOrderID) {
        this.fuelOrderID = fuelOrderID;
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public String getFuelOrderDate() {
        return fuelOrderDate;
    }

    public void setFuelOrderDate(String fuelOrderDate) {
        this.fuelOrderDate = fuelOrderDate;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }
}
