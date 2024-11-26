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

public class Report3Controller 
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
    private TableColumn<Report3Data, String> Name;

    @FXML
    private TableView<Report3Data> Report3;

    @FXML
    private TableColumn<Report3Data, Integer> TotalCustomers;

    @FXML
    private TableColumn<Report3Data, Integer> TotalOrders;

    @FXML
    private TableColumn<Report3Data, Double> TotalFuel;
    
    @FXML
    private TableColumn<Report3Data, Double> TotalPayments;

    private ObservableList<Report3Data> reportData = FXCollections.observableArrayList();

    @FXML
    void generateReport(ActionEvent event) throws SQLException 
    {
        SQLHandler.getInstance().buildconnection();;

    	
        String q = """
                select station.name, count(distinct customer.customerid), count(distinct orderdetails.orderid), sum(distinct orderdetails.amountoffuel), sum(distinct payment.amount) 
from station inner join customer on station.stationid = customer.stationid inner join orderdetails on orderdetails.customerid = customer.customerid inner join payment on payment.customerid = customer.customerid inner join owns on owns.stationid = station.stationid
where owns.ownerid = ?
group by station.name
                """;

        ResultSet resultSet = SQLHandler.getInstance().loaddata(q, this.ownerid);
        while (resultSet.next()) {
        	String stationName = resultSet.getString(1);
            int totalcustomers = resultSet.getInt(2);
            int totalorders = resultSet.getInt(3);
            double fuel = resultSet.getDouble(4);
            double payment = resultSet.getDouble(5);

            // Add the data to the ObservableList
            reportData.add(new Report3Data(stationName, totalorders, payment, totalcustomers, fuel));
        }

        System.out.println("Report Data Size: " + reportData.size());
        SQLHandler.getInstance().closeconnection();
        
        
        Report3.setItems(reportData);
    }

    @FXML
    void downloadReport(ActionEvent event) {

    	FileHandler.getInstance().report3(reportData);

    }

    @FXML
    public void initialize() {
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TotalOrders.setCellValueFactory(new PropertyValueFactory<>("totalOrders"));
        TotalPayments.setCellValueFactory(new PropertyValueFactory<>("earnings"));
        TotalCustomers.setCellValueFactory(new PropertyValueFactory<>("totalCustomers"));
        TotalFuel.setCellValueFactory(new PropertyValueFactory<>("totalFuel"));
    

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
