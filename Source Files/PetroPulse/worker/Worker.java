package worker;

public class Worker {
    public int workerid;
    public String name;
    public String email;
    public String phonenumber;
    public int salary;
    public int station;

    public Worker(int id, String name, String email, String phonenumber, int salary, int sid) {
        this.workerid = id;
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.salary = salary;
        this.station= sid;
    }

    // Getters and Setters
    public int getId() { return workerid; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhonenumber() { return phonenumber; }
    public int getSalary() { return salary; }
    public int getStation() { return station; }
}
