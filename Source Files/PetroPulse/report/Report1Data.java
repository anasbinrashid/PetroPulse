package report;

public class Report1Data {
    private String name;
    private int fuelstand;
    private int capacity;
    private double earnings;
    private int order;

    public Report1Data(String name, double earnings, int capacity, int fuelStand, int order ) 
    {
        this.name = name;
        this.fuelstand = fuelStand;
        this.capacity = capacity;
        this.earnings = earnings;
        this.order = order;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFuelstand() {
		return fuelstand;
	}

	public void setFuelstand(int fuelStand) {
		this.fuelstand = fuelStand;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getEarnings() {
		return earnings;
	}

	public void setEarnings(double earnings) {
		this.earnings = earnings;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

    // Getters and setters
}
