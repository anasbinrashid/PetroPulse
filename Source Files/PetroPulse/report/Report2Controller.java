package report;
import application.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Report2Controller 
{
	public Stage stage;
    public Scene scene;
    public Parent root;
	
    public int ownerid;
    public String name;
    public String email;
    public String password;
    public String phonenumber;
    
    public double dp;
    public void setdp(double phoneno) {
        this.dp = phoneno;
    }
    
    public void setUserName(String name) {
        this.name = name;
    }
    public void setEmail(String e) {
    	this.email = e;
    }
    public void setPhoneNo(String phoneno) {
        this.phonenumber = phoneno;
    }
    public void setpass(String phoneno) {
        this.password = phoneno;
    }
    public void setid(String phoneno) {
        this.ownerid = Integer.parseInt(phoneno);
    }
	
    @FXML
    private TableColumn<Report2Data, Double > AvgSalary;

    @FXML
    private TableColumn<Report2Data, Double > MaxSalary;

    @FXML
    private TableColumn<Report2Data, Double > MinSalary;

    @FXML
    private TableColumn<Report2Data, String> Name;

    @FXML
    private TableView<Report2Data> Report2;

    @FXML
    private TableColumn<Report2Data, Integer> Workers;
    
    
    private ObservableList<Report2Data> reportData = FXCollections.observableArrayList();
    
    
    @FXML
    public void initialize() throws SQLException {
        Name.setCellValueFactory(new PropertyValueFactory<>("stationName")); // Matches getStationName()
        AvgSalary.setCellValueFactory(new PropertyValueFactory<>("avgSalary")); // Matches getAvgSalary()
        MaxSalary.setCellValueFactory(new PropertyValueFactory<>("maxSalary")); // Matches getMaxSalary()
        Workers.setCellValueFactory(new PropertyValueFactory<>("totalWorkers")); // Matches getTotalWorkers()
        MinSalary.setCellValueFactory(new PropertyValueFactory<>("minSalary")); // Matches getMinSalary()

        loadDataFromDatabase();
        
        Report2.setItems(reportData);
    }

    @FXML
    public void loadDataFromDatabase() throws SQLException 
    {
        SQLHandler.getInstance().buildconnection();;

        String q = "select s.name as stationname, count(w.workerid) as totalworkers, avg(w.salary) as averagesalary, min(w.salary) as minsalary, max(w.salary) as maxsalary from worker w join station s on w.stationid = s.stationid inner join owns on owns.stationid = s.stationid where owns.ownerid = ? group by s.name order by averagesalary desc";

        ResultSet resultSet = SQLHandler.getInstance().loaddata(q, this.ownerid);
        while (resultSet.next()) {
        	String stationName = resultSet.getString(1);
            int totalWorkers = resultSet.getInt(2);
            double avgSalary = resultSet.getDouble(3);
            double minSalary = resultSet.getDouble(4);
            double maxSalary = resultSet.getDouble(5);

            // Add the data to the ObservableList
            reportData.add(new Report2Data(stationName, totalWorkers, avgSalary, minSalary, maxSalary));
        }

        System.out.println("Report Data Size: " + reportData.size());
        SQLHandler.getInstance().closeconnection();
    }
        

   

    

    @FXML
    void downloadReport(ActionEvent event) 
    {
    	FileHandler.getInstance().report2(reportData);

    	

    }

    @FXML
    void generateReport(ActionEvent event) throws SQLException {
    	
    	loadDataFromDatabase();
    

    }
    
    @FXML
    public void goback(ActionEvent event) throws IOException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewReport.fxml"));	
		root = loader.load();	    		
		ViewReportController hw = loader.getController();
        hw.setid(String.valueOf(ownerid));
		hw.setUserName(name);
		hw.setpass(password);
		hw.setEmail(email);
		hw.setPhoneNo(phonenumber);
		hw.setdp(dp);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

}
