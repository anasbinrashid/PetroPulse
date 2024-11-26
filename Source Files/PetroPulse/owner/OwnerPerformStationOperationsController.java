package owner;
import application.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

public class OwnerPerformStationOperationsController 
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
    @FXML
    private Label userNameLabel;
    @FXML
    private TextField pricetext;
    
    @FXML
    private TextField cleantext;

    @FXML
    private Label priceLabel;
    
    @FXML
    private TextField cap;
    
    @FXML
    private ComboBox<String> combo;
    @FXML
    private ComboBox<String> combost;
    @FXML
    private ComboBox<String> comboft;
    @FXML
    private ComboBox<String> combow;
    public ArrayList<Integer> stationids = new ArrayList<>();
    public ArrayList<String> stationnames = new ArrayList<>();
    public ArrayList<String> workernames = new ArrayList<>();

    
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
    	
        combo.setItems(FXCollections.observableArrayList("Petrol", "Diesel", "Hi-Octane"));
        comboft.setItems(FXCollections.observableArrayList("Petrol", "Diesel", "Hi-Octane"));

        SQLHandler.getInstance().buildconnection();;
    	    
        String q= "select stationid from owns where ownerid = ?";
        ResultSet rs = SQLHandler.getInstance().loaddata(q, ownerid);
        
        while(rs.next())
        {
        	stationids.add(rs.getInt(1));
        }
        
        q = "select station.name from owns inner join station on station.stationid = owns.stationid where owns.ownerid = ?";
        rs = SQLHandler.getInstance().loaddata(q, ownerid);

        while(rs.next())
        {
        	stationnames.add(rs.getString(1));
        }
        
        combost.setItems(FXCollections.observableArrayList(stationnames));
        
        rs.close();
        SQLHandler.getInstance().closeconnection();
        

    }
    
    public void changeprice(ActionEvent event) throws NumberFormatException, SQLException, IOException
    {
        SQLHandler.getInstance().buildconnection();;

        String newprice = pricetext.getText(); 
        String ft = combo.getValue();
        
        if (newprice.isEmpty()) 
        {
            showAlert("Error", "Please ensure all fields are filled!", Alert.AlertType.ERROR);
            return;
        }

        for(int sid : stationids)
        {
            String q = "update fuelstand set fuelprice=? where stationid=? and fueltype=?";
            SQLHandler.getInstance().updatefp(q, newprice, String.valueOf(sid), ft);
        }
        
        
        showAlert("Successful", "Price has been Changed!", Alert.AlertType.CONFIRMATION);
        

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
        
		SQLHandler.getInstance().closeconnection();
    }
    
    public void chooseworker(ActionEvent event) throws NumberFormatException, SQLException, IOException
    {
        SQLHandler.getInstance().buildconnection();;

    	String stn = combost.getValue();
    	
    	String q= "select stationid from station where name = ?";
        int stid = SQLHandler.getInstance().getid(q, stn);
    	
    	q= "select name from worker where stationid = ?";
        ResultSet rs = SQLHandler.getInstance().loaddata(q, stid);
        
        workernames.clear();
        
        while(rs.next())
        {
        	workernames.add(rs.getString(1));
        }
        
        combow.setItems(FXCollections.observableArrayList(workernames));
        
        SQLHandler.getInstance().closeconnection();
    }
    
    public void clean(ActionEvent event) throws NumberFormatException, SQLException, IOException
    {
        SQLHandler.getInstance().buildconnection();;

    	String stn = combost.getValue();
    	String ftype = comboft.getValue();
    	String v = cap.getText();
    	int capacity = Integer.parseInt(v);
    	
    	String wn = combow.getValue();
    			
    	String q= "select stationid from station where name = ?";
        int stid = SQLHandler.getInstance().getid(q, stn);
        q= "select workerid from worker where name = ?";
        int wid = SQLHandler.getInstance().getid(q, wn);
        
        ThirdPartyFuelPriceAPI thirdPartyAPI = new ThirdPartyFuelPriceAPI();
        FuelPriceService fuelPriceService = new FuelPriceAdapter(thirdPartyAPI);

        double p = fuelPriceService.getFuelPrice(ftype);
        
        q = "insert into fuelstand (capacity, fueltype, fuelprice, stationid, workerid) values (?, ?, ?, ?, ?)";
        SQLHandler.getInstance().insertfuelstand(q, capacity, ftype, stid, wid, p);
        
        showAlert("Successful", "New Fuelstand Created!", Alert.AlertType.CONFIRMATION);
        

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
