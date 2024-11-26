package worker;
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

public class HomeWorkerController 
{
	public Stage stage;
    public Scene scene;
    public Parent root;
    
    public int workerid;
    public String name;
    public String email;
    public String password;
    public String phonenumber;
    public double salary;
    public int stationid;
	
    @FXML
    public Label userNameLabel;
    @FXML
    public Label emailLabel;
    @FXML
    public Label phoneLabel;
    @FXML
    public Label salLabel;
    @FXML
    public Label stationLabel;
    
    public void setUserName(String name) {
    	this.name = name;
        userNameLabel.setText(name);
    }
    public void setEmail(String e) {
    	this.email = e;
    	emailLabel.setText(e);
    }
    public void setPhoneNo(String phoneno) {
    	this.phonenumber = phoneno;
        phoneLabel.setText(phoneno);
    }
    public void setsalary(String sal) {
    	this.salary = Double.parseDouble(sal);
    	salLabel.setText(sal);
    }
    public void setstation(String station) {
    	this.stationid = Integer.parseInt(station);
    	stationLabel.setText(station);
    }
    public void setid(String id) {
    	this.workerid = Integer.parseInt(id);
    }
    public void setpass(String p) {
    	this.password= p;
    }


    public void viewschedule(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewSchedule.fxml"));	
		root = loader.load();	    		
		ViewScheduleController hw = loader.getController();
        hw.setid(String.valueOf(workerid));
		hw.setUserName(name);
		hw.setpass(password);
		hw.setEmail(email);
		hw.setPhoneNo(phonenumber);
		hw.setsalary(String.valueOf(salary));
		hw.setstation(String.valueOf(stationid));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    public void dispensefuel(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("DispenseFuel.fxml"));	
		root = loader.load();	    		
		DispenseFuelController hw = loader.getController();
        hw.setid(String.valueOf(workerid));
		hw.setUserName(name);
		hw.setpass(password);
		hw.setEmail(email);
		hw.setPhoneNo(phonenumber);
		hw.setsalary(String.valueOf(salary));
		hw.setstation(String.valueOf(stationid));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    public void pso(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkerPerformStationOperations.fxml"));	
		root = loader.load();	    		
		WorkerPerformStationOperationsController hw = loader.getController();
        hw.setid(String.valueOf(workerid));
		hw.setUserName(name);
		hw.setpass(password);
		hw.setEmail(email);
		hw.setPhoneNo(phonenumber);
		hw.setsalary(String.valueOf(salary));
		hw.setstation(String.valueOf(stationid));
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
