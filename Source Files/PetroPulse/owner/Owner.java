package owner;
public class Owner {
    private int ownerID;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;

    // Constructor
    public Owner(int ownerID, String email, String name, String phoneNumber, String password) {
        this.ownerID = ownerID;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters and Setters
    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
