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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OrderFuelController {

    @FXML
    private TextField TimeText;

    @FXML
    private Label myLabel;

    @FXML
    private Slider mySlider;

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ComboBox<String> cb2;
    @FXML
    private ComboBox<String> cb21;

    public ArrayList<String> stationids = new ArrayList<>();


    @FXML 
    private Label bill;
    @FXML
    private DatePicker datePicker;

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
    	    
    	String q= "select station.name from station inner join owns on owns.stationid = station.stationid where owns.ownerid = ?";
        ResultSet rs = SQLHandler.getInstance().loaddata(q, ownerid);
        
        while(rs.next())
        {
        	stationids.add(rs.getString(1));
        }
        
        rs.close();
        
        cb21.setItems(FXCollections.observableArrayList(stationids));
        
        
        ArrayList<String> num = new ArrayList<>();
        
        q= "select name from supplier";
        rs = SQLHandler.getInstance().onlyquery(q);
        
        while(rs.next())
        {
        	num.add(rs.getString(1));
        }		
        
        cb2.setItems(FXCollections.observableArrayList(num));
        
        SQLHandler.getInstance().closeconnection();
    }
    
    @FXML
    public void initialize() {
        // Initialize ComboBox
    	 comboBox.setItems(FXCollections.observableArrayList("Petrol", "Diesel", "Hi-Octane"));

        // Set up a listener on the slider's value property
         myLabel.textProperty().bind(mySlider.valueProperty().asString("Number of Litres: %.1f"));

         // Optional: Set an initial value for the slider if needed
         mySlider.setValue(0); // Default slider value

    }

    @FXML
    void showbill(ActionEvent event) throws SQLException
    {
        SQLHandler.getInstance().buildconnection();;

        double amountOfFuel = mySlider.getValue();

        String suppids = cb2.getValue();

        String q= "select supplierid from supplier where name = ?";
        int suppid = SQLHandler.getInstance().getid(q, suppids);
        
        q = "select sellingprice from supplier where supplierid = ?";
        double sp = SQLHandler.getInstance().getfuelprice(q, suppid);
        
    	int updatedearnings = (int)(sp*amountOfFuel);

    	bill.setText("Bill: Rs. "+updatedearnings+".0");

        
        SQLHandler.getInstance().closeconnection();;


    }
    
    @FXML
    void saleDetected(ActionEvent event) 
    {
        SQLHandler.getInstance().buildconnection();;
    	
        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : null;
        double amountOfFuel = mySlider.getValue();
        String stationids = cb21.getValue();
        String item = comboBox.getValue();
        String suppids = cb2.getValue();
        
        
        if (date.isEmpty() || item.isEmpty()) 
        {
            showAlert("Error", "Please ensure all fields are filled!", Alert.AlertType.ERROR);
            return;
        }

        try 
        {
        	String q= "select stationid from station where name = ?";
            int stationid = SQLHandler.getInstance().getid(q, stationids);
            
            q= "select supplierid from supplier where name = ?";
            int suppid = SQLHandler.getInstance().getid(q, suppids);
        	
        	q = "INSERT INTO fuelorder (fuelamount, fuelorderdate, stationid, supplierID) VALUES (?, ?, ?, ?)";
        	int ra = SQLHandler.getInstance().insertfuelorder(q, amountOfFuel, date, stationid, suppid);
            
        	if(ra>0)
        	{
        		 q = "select duepayment from owner where ownerid = ?";
                 double duepayment = SQLHandler.getInstance().getfuelprice(q, this.ownerid);
             	
                 q = "select sellingprice from supplier where supplierid = ?";
                 double sp = SQLHandler.getInstance().getfuelprice(q, suppid);
                 
             	double updatedearnings = (sp*amountOfFuel);
             	
             	duepayment += updatedearnings;
        		this.dp = duepayment;
            	q = "update owner set owner.duepayment= ? where owner.ownerid= ?";
            	SQLHandler.getInstance().updateearnings(q, String.valueOf(this.ownerid), String.valueOf(this.dp));
        		
                double updatedstation, updatedstand = 0;

            	q= "select totalcapacity from station where stationid = ?";
                int tc = SQLHandler.getInstance().getvalue(q, stationid);
                
                updatedstation = (int) (tc+amountOfFuel);
                
            	q = "update station set station.totalcapacity = ? where station.stationid = ?";
            	int updatedStationCapacity = (int) Math.round(updatedstation); // Convert to integer
            	SQLHandler.getInstance().update(q, String.valueOf(stationid), String.valueOf(updatedStationCapacity));

            	
                q = "select fuelstandid from fuelstand inner join station on fuelstand.stationid = station.stationid where station.stationid = ? and fuelstand.fueltype = ?";
                ResultSet rs = SQLHandler.getInstance().getfuelstand1(q, stationid, item);
                
                ArrayList<Integer> fsids = new ArrayList<>();
                
                while(rs.next())
                {
                	fsids.add(rs.getInt(1));
                }
                
                int newcap = (int) (amountOfFuel / fsids.size());
                
                for(int fuelstandid : fsids)
                {
                	q = "select capacity from fuelstand where fuelstand.fuelstandid=?";
                	int curr = SQLHandler.getInstance().getvalue(q, fuelstandid);
                	
                	updatedstand = (int) (curr + newcap);
                	
                	q = "update fuelstand set fuelstand.capacity = ? where fuelstand.fuelstandid = ?";
                	int updatedstandCapacity = (int) Math.round(updatedstand); // Convert to integer
                	SQLHandler.getInstance().update(q, String.valueOf(fuelstandid), String.valueOf(updatedstandCapacity));

                }
                            	            	
                showAlert("Success", "Fuel Ordered successfully!", Alert.AlertType.INFORMATION);
                
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
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            showAlert("Database Error", "Failed to record sale: " + e.getMessage(), Alert.AlertType.ERROR);
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
