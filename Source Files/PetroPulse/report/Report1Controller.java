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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Report1Controller 
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
    private TableColumn<Report1Data, String> Name;

    @FXML
    private TableColumn<Report1Data, Integer> Fuelstand;

    @FXML
    private TableColumn<Report1Data, Integer> Capacity;

    @FXML
    private TableColumn<Report1Data, Double> Earnings;

    @FXML
    private TableColumn<Report1Data, Integer> Order;
    
    @FXML
    private TableView<Report1Data> Report1;

    @FXML
    private Button GenerateReport;

    private ObservableList<Report1Data> reportData = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        // Configure columns
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Earnings.setCellValueFactory(new PropertyValueFactory<>("earnings"));
        Capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        Fuelstand.setCellValueFactory(new PropertyValueFactory<>("fuelstand"));
        Order.setCellValueFactory(new PropertyValueFactory<>("order"));
        

        // Load data from database
        loadDataFromDatabase();

        Report1.setItems(reportData);

    }

    @FXML
    public void loadDataFromDatabase() throws SQLException 
    {
        SQLHandler.getInstance().buildconnection();;
        String q = """
            SELECT station.name, station.totalearnings, station.totalcapacity, 
                   (SELECT COUNT(fuelstand.fuelstandid) FROM fuelstand 
                    WHERE fuelstand.stationid = station.stationid) AS fuelstand_count,
                   (SELECT COUNT(orderdetails.orderid) FROM orderdetails 
                    INNER JOIN fuelstand ON fuelstand.fuelstandid = orderdetails.fuelstandid 
                    WHERE fuelstand.stationid = station.stationid) AS orderdetails_count
            FROM station 
            INNER JOIN owns ON owns.stationid = station.stationid 
            WHERE owns.ownerid = ? 
            GROUP BY station.stationid, station.name, station.totalearnings, station.totalcapacity
        """;

        ResultSet resultSet = SQLHandler.getInstance().loaddata(q, this.ownerid);
        while (resultSet.next()) {
            String name = resultSet.getString(1);
            double earnings = resultSet.getDouble(2);
            int capacity = resultSet.getInt(3);
            int fs = resultSet.getInt(4);
            int od = resultSet.getInt(5);

            System.out.println("Station Name: " + name + ", Earnings: " + earnings + 
                               ", Capacity: " + capacity + ", Fuel Stand: " + fs + 
                               ", Orders: " + od);

            reportData.add(new Report1Data(name, earnings, capacity, fs, od));
        }

        System.out.println("Report Data Size: " + reportData.size());
        SQLHandler.getInstance().closeconnection();
    }

    @FXML
    void downloadReport(ActionEvent event) 
    {
        FileHandler.getInstance().report1(reportData);
        
        
    }
    
    @FXML
    void generate(ActionEvent event) throws SQLException {

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
