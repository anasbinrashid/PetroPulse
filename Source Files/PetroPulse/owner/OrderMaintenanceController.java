package owner;
import application.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OrderMaintenanceController 
{
	public Stage stage;
    public Scene scene;
    public Parent root;
	
    public int ownerid;
    public String name;
    public String email;
    public String password;
    public String phonenumber;
    
    @FXML
    private Label userNameLabel;
    @FXML
    private TextField desc;
    @FXML
    private ComboBox<String> comboBox1;
    @FXML
    private ComboBox<String> comboBox2;
    @FXML
    private ComboBox<String> comboBox3;
    public ArrayList<String> stationids = new ArrayList<>();

    public double dp;
    public void setdp(double phoneno) {
        this.dp = phoneno;
    }
    
    public void setUserName(String name) {
        userNameLabel.setText(name);
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
    
    public void setid(String id) throws SQLException
    {
    	this.ownerid = Integer.parseInt(id);
    	
        SQLHandler.getInstance().buildconnection();;
    	
        comboBox1.setItems(FXCollections.observableArrayList("Fuel Pump", "CNG Tank", "Oil Dispenser", "Software System", "Storage Tank", "Oil Tank"));
    
        String q= "select station.name from station inner join owns on owns.stationid = station.stationid where owns.ownerid = ?";
        ResultSet rs = SQLHandler.getInstance().loaddata(q, ownerid);
        
        while(rs.next())
        {
        	stationids.add(rs.getString(1));
        }
        
        rs.close();
        
        comboBox2.setItems(FXCollections.observableArrayList(stationids));
        
        ArrayList<String> num = new ArrayList<>();
        
        q= "select name from supplier";
        rs = SQLHandler.getInstance().onlyquery(q);
        
        while(rs.next())
        {
        	num.add(rs.getString(1));
        }
        
        comboBox3.setItems(FXCollections.observableArrayList(num));
        
        SQLHandler.getInstance().closeconnection();

    }

    
    @FXML
    public void order(ActionEvent event) throws IOException, SQLException 
    {
        SQLHandler.getInstance().buildconnection();;
    	
        String item = comboBox1.getValue();
        String description = desc.getText();
        String stats = comboBox2.getValue();
        String supps = comboBox3.getValue();
        
        if (item.isEmpty() || description.isEmpty()) 
        {
            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            
            return;
        }
        
        String q= "select stationid from station where name = ?";
        int stat = SQLHandler.getInstance().getid(q, stats);
        
        q= "select supplierid from supplier where name = ?";
        int supp = SQLHandler.getInstance().getid(q, supps);
        
        q = "INSERT INTO maintenance (description, stationid, supplierid, item, ownerid) VALUES (?, ?, ?, ?, ?)";
        int ra = SQLHandler.getInstance().insertmaintenance(q, description, stat, supp, item, this.ownerid);
        
        if(ra>0)
        {

            q = "select duepayment from owner where ownerid = ?";
            double duepayment = SQLHandler.getInstance().getfuelprice(q, this.ownerid);
        	
        	double updatedearnings = 0.0;
        	
            Random random = new Random();

        	
        	if ("Fuel Pump".equals(item)) {
                updatedearnings = random.nextDouble() * 1000; // Random value between 0 and 1000
            } else if ("CNG Tank".equals(item)) {
                updatedearnings = random.nextDouble() * 500; // Random value between 0 and 500
            } else if ("Oil Dispenser".equals(item)) {
                updatedearnings = random.nextDouble() * 300; // Random value between 0 and 300
            } else if ("Software System".equals(item)) {
                updatedearnings = random.nextDouble() * 2000; // Random value between 0 and 2000
            } else if ("Storage Tank".equals(item)) {
                updatedearnings = random.nextDouble() * 1500; // Random value between 0 and 1500
            } else if ("Oil Tank".equals(item)) {
                updatedearnings = random.nextDouble() * 800; // Random value between 0 and 800
            }
        	
        	duepayment += updatedearnings;
    		this.dp = duepayment;
        	q = "update owner set owner.duepayment= ? where owner.ownerid= ?";
        	SQLHandler.getInstance().updateearnings(q, String.valueOf(this.ownerid), String.valueOf(this.dp));
        	
            showAlert("Success", "Maintenance Ordered successfully!", Alert.AlertType.INFORMATION);

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
        
        SQLHandler.getInstance().closeconnection();
    }
    
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void goback(ActionEvent event) throws IOException
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeOwner.fxml"));	
		root = loader.load();	    		
        HomeOwnerController hw = loader.getController();
        hw.setid(String.valueOf(ownerid));
        hw.setName(name);
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
