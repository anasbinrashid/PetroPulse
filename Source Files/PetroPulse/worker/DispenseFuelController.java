package worker;
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

public class DispenseFuelController 
{
	@FXML
    public Label nameLabel;
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
    public ComboBox<String> cb;
        
    public void setUserName(String name) {
    	this.name = name;
        nameLabel.setText(name);
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
     	     
    	 ArrayList<String> custs = new ArrayList<>();
    	 
         String q= "select customer.name from customer inner join station on station.stationid = customer.stationid inner join worker on worker.stationid = station.stationid where worker.workerid = ?";
         ResultSet rs = SQLHandler.getInstance().loaddata(q, this.workerid);
         
         while(rs.next())
         {
        	 custs.add(rs.getString(1));
         }
         
         rs.close();
         
         cb.setItems(FXCollections.observableArrayList(custs));

         
         SQLHandler.getInstance().closeconnection();
    }
    public void setpass(String p) {
    	this.password= p;
    }

    @FXML
    private TextField TimeText;
    
    @FXML
    private TextField customertext;

    @FXML
    private Label myLabel;
    
    @FXML 
    private Label bill;

    @FXML
    private Slider mySlider;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    public void initialize() 
    {
        comboBox.setItems(FXCollections.observableArrayList("Petrol", "Diesel", "Hi-Octane"));
        myLabel.textProperty().bind(mySlider.valueProperty().asString("Number of Litres: %.1f"));

        // Optional: Set an initial value for the slider if needed
        mySlider.setValue(0); // Default slider value
    }

    @FXML
    void showbill(ActionEvent event) throws SQLException
    {
        SQLHandler.getInstance().buildconnection();;

        double amountOfFuel = mySlider.getValue();
        
		String q = "select fuelstandid from fuelstand where workerid = ?";
        int fuelstandid = SQLHandler.getInstance().getvalue(q, this.workerid);

        
		q= "select fuelprice from fuelstand where fuelstand.fuelstandid=?";
        ResultSet rs = SQLHandler.getInstance().loginverification(q, String.valueOf(fuelstandid), "");
        double fuelprice = 0;
        if(rs.next())
        {
        	fuelprice = rs.getDouble(1);

        }
    	int updatedearnings = (int) (amountOfFuel * fuelprice);
    	bill.setText("Bill: Rs. "+updatedearnings+".0");

        
        SQLHandler.getInstance().closeconnection();;


    }
    
    @FXML
    void saleDetected(ActionEvent event) throws NumberFormatException, SQLException 
    {
        SQLHandler.getInstance().buildconnection();;

        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : null;
        String time = TimeText.getText();
        double amountOfFuel = mySlider.getValue();
        String customerIDs = cb.getValue(); 
        String fueltype = comboBox.getValue();
        double fuelprice;

        
        
        if (date == null || time.isEmpty() || comboBox.getValue() == null || customerIDs.isEmpty()) 
        {
            showAlert("Error", "Please ensure all fields are filled!", Alert.AlertType.ERROR);
            return;
        }

        String qs= "select customerid from customer where name = ?";
        int customerID = SQLHandler.getInstance().getid(qs, customerIDs);
        
        String q = "select stationid from customer where customerid = ?";
        int stationid = SQLHandler.getInstance().getvalue(q, customerID);
        
        if(stationid==this.stationid)
        {
        	try 
            {
        		q = "select fuelstandid from fuelstand where workerid = ?";
                int fuelstandid = SQLHandler.getInstance().getvalue(q, this.workerid);
        		
        		q= "select fueltype, fuelprice from fuelstand where fuelstand.fuelstandid=?";
                ResultSet rs = SQLHandler.getInstance().loginverification(q, String.valueOf(fuelstandid), "");
                
                if(rs.next())
                {
                	String retrfueltype = rs.getString(1);
                	fuelprice = rs.getDouble(2);
                	
                	if (retrfueltype.equalsIgnoreCase(fueltype)) 
                	{
                		q= "select totalcapacity, capacity from fuelstand inner join station where fuelstand.stationid = station.stationid and station.stationid=? and fuelstand.fuelstandid=?";
                        rs = SQLHandler.getInstance().loginverification(q, String.valueOf(stationid), String.valueOf(fuelstandid));
                        
                        int updatedstation, updatedstand;
                        
                        if(rs.next())
                        {
                        	int cap = rs.getInt(2);

                        	if(cap>=amountOfFuel)
                        	{
                        		q = "INSERT INTO OrderDetails (OrderDate, Time, AmountOfFuel, CustomerID, fuelstandid) VALUES (?, ?, ?, ?, ?)";
                                int ra = SQLHandler.getInstance().insertorder(q, time, date, String.valueOf(amountOfFuel), String.valueOf(customerID), String.valueOf(fuelstandid));
                                
                                if(ra>0)
                                {                 	                                    
                                    updatedstation = (int) (rs.getInt(1) - amountOfFuel);
                                	updatedstand = (int) (rs.getInt(2) - amountOfFuel);
                                	
                                	q = "update station set station.totalcapacity = ? where station.stationid = ?";
                                	SQLHandler.getInstance().update(q, String.valueOf(stationid), String.valueOf(updatedstation));
                                	
                                	q = "update fuelstand set fuelstand.capacity = ? where fuelstand.fuelstandid = ?";
                                	SQLHandler.getInstance().update(q, String.valueOf(fuelstandid), String.valueOf(updatedstand));
                                	
                                	q = "select loyaltypoints from customer where customerid = ?";
                                    int loyaltypoints = SQLHandler.getInstance().getvalue(q, customerID);
                                    
                                	loyaltypoints = loyaltypoints + (int)(5 * amountOfFuel);
                                	q = "update customer set customer.loyaltypoints= ? where customer.customerid= ?";
                                	SQLHandler.getInstance().updateearnings(q, String.valueOf(customerID), String.valueOf(loyaltypoints));
                                	
                                	q = "select duepayment from customer where customerid = ?";
                                    double duepayment = SQLHandler.getInstance().getfuelprice(q, customerID);
                                	
                                	double updatedearnings = (amountOfFuel * fuelprice);
                                	duepayment += updatedearnings;
                                	                                	
                            		q = "update customer set customer.duepayment= ? where customer.customerid= ?";
                            		SQLHandler.getInstance().updateearnings(q, String.valueOf(customerID), String.valueOf(duepayment));
                                	
                                    showAlert("Success", "Sale recorded successfully!", Alert.AlertType.INFORMATION);

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
                                else
                                {
                                    showAlert("Database Error", "Failed to record sale!", Alert.AlertType.ERROR);
                                }
                        	}
                        	else
                        	{
                                showAlert("Error", "Not enough Fuel!", Alert.AlertType.ERROR);
                        	}
                        	
                        }	
                	}
                	else
                	{
                		showAlert("Error", "Wrong Fuel Type Selection!", Alert.AlertType.ERROR);
                        
                	}
                }    
            } 
        	catch (Exception e) 
        	{
                e.printStackTrace();
                showAlert("Database Error", "Failed to record sale: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        else
        {
        	showAlert("Error", "This Customer does not have an account for your Station!", Alert.AlertType.ERROR);
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
