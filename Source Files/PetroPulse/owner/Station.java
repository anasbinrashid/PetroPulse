package owner;

public class Station {
    private int stationID;
    private String name;
    private String location;
    private int totalCapacity;
    private String company;
    private double totalEarnings;

    // Constructor
    public Station(int stationID, String name, String location, int totalCapacity, String company, double totalEarnings) {
        this.stationID = stationID;
        this.name = name;
        this.location = location;
        this.totalCapacity = totalCapacity;
        this.company = company;
        this.totalEarnings = totalEarnings;
    }

    // Getters and Setters
    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }
}
