package report;

public class Report2Data {
	
	private String StationName;
	private int TotalWorkers;
	private double avgSalary;
	private double minSalary;
	private double maxSalary;
	public Report2Data(String stationName, int totalWorkers, double avgSalary, double minSalary, double maxSalary) {
		super();
		StationName = stationName;
		TotalWorkers = totalWorkers;
		this.avgSalary = avgSalary;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
	}
	public String getStationName() {
		return StationName;
	}
	public void setStationName(String stationName) {
		StationName = stationName;
	}
	public int getTotalWorkers() {
		return TotalWorkers;
	}
	public void setTotalWorkers(int totalWorkers) {
		TotalWorkers = totalWorkers;
	}
	public double getAvgSalary() {
		return avgSalary;
	}
	public void setAvgSalary(double avgSalary) {
		this.avgSalary = avgSalary;
	}
	public double getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(double minSalary) {
		this.minSalary = minSalary;
	}
	public double getMaxSalary() {
		return maxSalary;
	}
	public void setMaxSalary(double maxSalary) {
		this.maxSalary = maxSalary;
	}
	
	
	

}
