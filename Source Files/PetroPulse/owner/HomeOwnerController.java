package owner;
import report.*;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeOwnerController 
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
    
    @FXML
    private Label userNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label nameLabel;

    public void setName(String name) {
    	nameLabel.setText(name);
    }
    
    public void setUserName(String name) {
        userNameLabel.setText(name);
        this.name = name;
    }
    public void setEmail(String e) {
    	emailLabel.setText(e);
    	this.email = e;
    }
    public void setPhoneNo(String phoneno) {
        phoneLabel.setText(phoneno);
        this.phonenumber = phoneno;
    }
    public void setpass(String phoneno) {
        this.password = phoneno;
    }
    public void setdp(double phoneno) {
        this.dp = phoneno;
    }
    
    public void setid(String id)
    {
    	this.ownerid = Integer.parseInt(id);
    }
    
    public void setschedule(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("SetEmployeeSchedule.fxml"));	
		root = loader.load();	    		
		SetEmployeeScheduleController hw = loader.getController();
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
    
    public void viewreport(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/report/ViewReport.fxml"));	
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
    
    public void ordermaintenance(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderMaintenance.fxml"));	
		root = loader.load();	    		
		OrderMaintenanceController hw = loader.getController();
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
    
    public void orderfuel(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderFuel.fxml"));	
		root = loader.load();	    		
		OrderFuelController hw = loader.getController();
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
    
    public void maketransaction(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("MakeTransactions.fxml"));	
		root = loader.load();	    		
		MakeTransactionsController hw = loader.getController();
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
    
    public void manageworker(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageWorkers.fxml"));	
		root = loader.load();	    		
		ManageWorkersController hw = loader.getController();
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
    
    public void managecustomer(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageCustomer.fxml"));	
		root = loader.load();	    		
		ManageCustomerController hw = loader.getController();
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
    
    public void addstation(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStation.fxml"));	
		root = loader.load();	    		
		AddStationController hw = loader.getController();
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
    
    public void pso(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("OwnerPerformStationOperations.fxml"));	
		root = loader.load();	    		
		OwnerPerformStationOperationsController hw = loader.getController();
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
    
    public void signout(ActionEvent event) throws IOException 
    {
    	root = FXMLLoader.load(getClass().getResource("/application/LandingPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
}
