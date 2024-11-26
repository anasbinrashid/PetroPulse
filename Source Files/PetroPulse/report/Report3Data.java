package report;

public class Report3Data {
	
	private String Name;
	private int totalOrders;
	private double earnings;
	private int totalCustomers;
	private double totalFuel;
	
	public Report3Data(String name, int totalOrders, double earnings, int totalCustomers, double f) {
		super();
		Name = name;
		this.totalOrders = totalOrders;
		this.earnings = earnings;
		this.totalCustomers = totalCustomers;
		this.setTotalFuel(f);
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}
	public double getEarnings() {
		return earnings;
	}
	public void setEarnings(double earnings) {
		this.earnings = earnings;
	}
	public int getTotalCustomers() {
		return totalCustomers;
	}
	public void setTotalCustomers(int totalCustomers) {
		this.totalCustomers = totalCustomers;
	}
	public double getTotalFuel() {
		return totalFuel;
	}
	public void setTotalFuel(double totalFuel) {
		this.totalFuel = totalFuel;
	}
	
	
	

}
