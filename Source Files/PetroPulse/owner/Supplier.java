package owner;

public class Supplier {
    private int supplierID;
    private String name;
    private String email;
    private String phoneNumber;
    private double sellingPrice;

    // Constructor
    public Supplier(int supplierID, String name, String email, String phoneNumber, double sellingPrice) {
        this.supplierID = supplierID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sellingPrice = sellingPrice;
    }

    // Getters and Setters
    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
