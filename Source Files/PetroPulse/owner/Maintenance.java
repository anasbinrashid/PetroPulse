package owner;

public class Maintenance {
    private int maintenanceID;
    private String description;
    private int stationID; // Foreign Key
    private int supplierID; // Foreign Key

    // Constructor
    public Maintenance(int maintenanceID, String description, int stationID, int supplierID) {
        this.maintenanceID = maintenanceID;
        this.description = description;
        this.stationID = stationID;
        this.supplierID = supplierID;
    }

    // Getters and Setters
    public int getMaintenanceID() {
        return maintenanceID;
    }

    public void setMaintenanceID(int maintenanceID) {
        this.maintenanceID = maintenanceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
