package worker;
public class FuelStand {
    private int fuelStandID;
    private double capacity;
    private String fuelType;
    private double fuelPrice;
    private int stationID; // Foreign Key
    private int workerID;  // Foreign Key

    // Constructor
    public FuelStand(int fuelStandID, double capacity, String fuelType, double fuelPrice, int stationID, int workerID) {
        this.fuelStandID = fuelStandID;
        this.capacity = capacity;
        this.fuelType = fuelType;
        this.fuelPrice = fuelPrice;
        this.stationID = stationID;
        this.workerID = workerID;
    }

    // Getters and Setters
    public int getFuelStandID() {
        return fuelStandID;
    }

    public void setFuelStandID(int fuelStandID) {
        this.fuelStandID = fuelStandID;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(double fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public int getWorkerID() {
        return workerID;
    }

    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }
}
