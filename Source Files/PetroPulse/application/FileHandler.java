package application;
import report.*;


import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class FileHandler extends PersistenceHandler
{	
	private static FileHandler instance; // Singleton instance

    private FileHandler() {}

    public static FileHandler getInstance() 
    {
        if (instance == null) 
        {
            synchronized (FileHandler.class) 
            { 
                if (instance == null) 
                {
                    instance = new FileHandler();
                }
            }
        }
        
        return instance;
    }
	
	public void report1(ObservableList<Report1Data> reportData)
	{
		try (FileWriter writer = new FileWriter("Sales.csv")) 
        {
            writer.write("Station Name,Total Earnings,Total Capacity,Fuel Stand Count,Orders Count\n");
            
            for (Report1Data data : reportData) 
            {
            	writer.write(String.format("%s,%.2f,%d,%d,%d\n",
            	        data.getName(),
            	        data.getEarnings(),
            	        data.getCapacity(),
            	        data.getFuelstand(),
            	        data.getOrder()));

            }

            // Show success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Download Complete");
            alert.setHeaderText(null);
            alert.setContentText("The report has been successfully downloaded as 'Sales.csv'.");
            alert.showAndWait();
        } 
        
        catch (IOException e) 
        {
            // Show error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Download Failed");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while downloading the report: " + e.getMessage());
            alert.showAndWait();
        }
	}
	
	public void report2(ObservableList<Report2Data> reportData)
	{
		try (FileWriter writer = new FileWriter("Workers.csv")) {
            writer.write("Name,Total Workers,Average Salary,Max Salary,Min Salary\n");
            for (Report2Data data : reportData) {
                writer.write(String.format("%s,%s,%.2f,%.2f,%.2f\n",
                        data.getStationName(),
                        data.getTotalWorkers(),
                        data.getAvgSalary(),
                        data.getMaxSalary(), 
                        data.getMinSalary() ));
            }

            // Show success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Download Complete");
            alert.setHeaderText(null);
            alert.setContentText("The report has been successfully downloaded as 'Workers.csv'.");
            alert.showAndWait();
        } catch (IOException e) {
            // Show error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Download Failed");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while downloading the report: " + e.getMessage());
            alert.showAndWait();
        }
	}
	
	public void report3(ObservableList<Report3Data> reportData)
	{
		try (FileWriter writer = new FileWriter("Customers.csv")) {
            writer.write("Name,Total Customers, Total Orders,Fuel Sold,Total Earnings\n");
            for (Report3Data data : reportData) {
                writer.write(String.format("%s,%d,%d,%.2f,%.2f\n",
                        data.getName(),
                        data.getTotalCustomers(),
                        data.getTotalOrders(),
                        data.getTotalFuel(),
                        data.getEarnings()));
            }

            // Show success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Download Complete");
            alert.setHeaderText(null);
            alert.setContentText("The report has been successfully downloaded as 'Customers.csv'.");
            alert.showAndWait();
        } catch (IOException e) {
            // Show error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Download Failed");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while downloading the report: " + e.getMessage());
            alert.showAndWait();
        }
	}
	
	@Override
	public void closeconnection() 
	{	
        System.out.println("\nFile-based connection closed!");
	}
}
