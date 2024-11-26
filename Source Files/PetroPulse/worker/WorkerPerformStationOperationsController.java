package worker;
import application.*;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WorkerPerformStationOperationsController 
{
	@FXML
    public Label userNameLabel;
	
	@FXML
    private TextField pricetext;
    
    @FXML
    private TextField cleantext;

    @FXML
    private Label priceLabel;
    
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
        
    public void setUserName(String name) {
    	this.name = name;
        userNameLabel.setText(name);
    }
    public void setEmail(String e) {
    	this.email = e;
    }
    public void setPhoneNo(String phoneno) {
    	this.phonenumber = phoneno;
    }
    public void setsalary(String sal) {
    	this.salary = Double.parseDouble(sal);
    }
    public void setstation(String station) {
    	this.stationid = Integer.parseInt(station);
    }
    public void setid(String id) throws SQLException {
    	 this.workerid = Integer.parseInt(id);
    	 
         SQLHandler.getInstance().buildconnection();;

     	String q = "select fuelprice from fuelstand where workerid=?";
     	double fuelprice = SQLHandler.getInstance().getfuelprice(q, this.workerid);
     	priceLabel.setText(String.valueOf(fuelprice));
     	
     	SQLHandler.getInstance().closeconnection();
    }
    public void setpass(String p) {
    	this.password= p;
    }

    
    
    public void changeprice(ActionEvent event) throws NumberFormatException, SQLException, IOException
    {
        SQLHandler.getInstance().buildconnection();;

        String newprice = pricetext.getText(); 

        if (newprice.isEmpty()) 
        {
            showAlert("Error", "Please ensure all fields are filled!", Alert.AlertType.ERROR);
            return;
        }

        String q = "update fuelstand set fuelprice=? where workerid=?";
        int ra = SQLHandler.getInstance().updateearnings(q, String.valueOf(workerid), newprice);
        
        if(ra>0)
        {
            showAlert("Successful", "Price has been Changed!", Alert.AlertType.CONFIRMATION);
            
            SQLHandler.getInstance().closeconnection();

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
        
        SQLHandler.getInstance().closeconnection();
    }
    
    public void clean(ActionEvent event) throws NumberFormatException, SQLException, IOException
    {
        SQLHandler.getInstance().buildconnection();;

        String clean = cleantext.getText(); 

        if (clean.isEmpty()) 
        {
            showAlert("Error", "Please ensure all fields are filled!", Alert.AlertType.ERROR);
            return;
        }

        String q = "insert into audit(entity, action, entityid) values (?, ?, ?)";
        int ra = SQLHandler.getInstance().clean(q, workerid, clean);
        
        if(ra>0)
        {
            showAlert("Successful", "Changes Recorded!", Alert.AlertType.CONFIRMATION);
            
            SQLHandler.getInstance().closeconnection();

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
        
        SQLHandler.getInstance().closeconnection();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) 
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void goback(ActionEvent event) throws IOException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeWorker.fxml"));	
		root = loader.load();	    		
        HomeWorkerController hw = loader.getController();
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
}
