package owner;
import application.*;
import customer.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageCustomerController 
{
	@FXML
    private TextField email;
    @FXML
    private TextField fname;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField fcid;
	
	public Stage stage;
    public Scene scene;
    public Parent root;
	
    public int ownerid;
    public String name;
    public String Email;
    public String password;
    public String phonenumber;
    public ArrayList<Integer> stationids = new ArrayList<>();
    public double dp;
    public void setdp(double phoneno) {
        this.dp = phoneno;
    }
    @FXML
    private Label userNameLabel;
    @FXML
    private TableColumn<Customer, Integer> custId;
    @FXML
    private TableColumn<Customer, String> custName;
    @FXML
    private TableColumn<Customer, String> custEmail;
    @FXML
    private TableColumn<Customer, String> custPhoneNumber;
    @FXML
    private TableColumn<Customer, String> station;

    @FXML
    private TableView<Customer> workerTable;
    private ObservableList<Customer> workerList = FXCollections.observableArrayList();
    
    
    public void setUserName(String name) {
        userNameLabel.setText(name);
        this.name = name;
    }
    public void setEmail(String e) {
    	this.Email = e;
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
    	this.initialize();
    }
    
    @FXML
    public void initialize() {
        try {
            SQLHandler.getInstance().buildconnection();
            custId.setCellValueFactory(new PropertyValueFactory<>("customerid"));
            custName.setCellValueFactory(new PropertyValueFactory<>("name"));
            custEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            custPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
            station.setCellValueFactory(new PropertyValueFactory<>("stationid"));

            workerList.clear();
            String q = """
                SELECT customerid, customer.Name, customer.Email, customer.PhoneNumber, customer.stationid 
                FROM customer 
                INNER JOIN owns ON customer.stationid = owns.stationid 
                INNER JOIN owner ON owns.ownerid = owner.ownerid 
                WHERE owner.ownerid = ?
            """;

            try (ResultSet rs = SQLHandler.getInstance().loaddata(q, this.ownerid)) {
                while (rs.next()) {
                    Customer customer = new Customer(
                            rs.getInt("customerid"),
                            rs.getString("Name"),
                            rs.getString("Email"),
                            rs.getString("PhoneNumber"),
                            rs.getInt("StationID")
                    );
                    workerList.add(customer);
                    System.out.println("Added customer: " + customer.getName());
                }
            }
            System.out.println("Worker List Size: " + workerList.size());
            workerTable.setItems(workerList);
        } catch (SQLException e) {
            showAlert("Database Error", "Error loading customers: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            SQLHandler.getInstance().closeconnection();
        }
    }

    
    @FXML
    void removecustomer(ActionEvent event) throws IOException 
    {
        SQLHandler.getInstance().buildconnection();;

        String custIdText = fcid.getText();

        if (custIdText.isEmpty()) 
        {
            showAlert("Error", "Please enter the Customer ID to remove.", Alert.AlertType.ERROR);
            return;
        }

        try 
        {
            String q = "DELETE FROM customer WHERE customerID = ?";
            int ra = SQLHandler.getInstance().updatemembership(q, custIdText);

            if(ra>0)
        	{
            	showAlert("Success", "Customer removed successfully!", Alert.AlertType.INFORMATION);
            	
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageCustomer.fxml"));	
        		root = loader.load();	    		
        		ManageCustomerController hw = loader.getController();
                hw.setid(String.valueOf(ownerid));
        		hw.setUserName(name);
        		hw.setpass(password);
        		hw.setEmail(Email);
        		hw.setPhoneNo(phonenumber);
        		hw.setdp(dp);
        		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        		scene = new Scene(root);
        		stage.setScene(scene);
        		stage.show();
        	}
        } 
        catch (NumberFormatException e) 
        {
            showAlert("Error", "Customer ID must be a number.", Alert.AlertType.ERROR);
        } 
        catch (SQLException e) 
        {
            showAlert("Database Error", "Error removing customer: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        
        SQLHandler.getInstance().closeconnection();
    }

    @FXML
    void updatecustomer(ActionEvent event) throws IOException 
    {
        SQLHandler.getInstance().buildconnection();;

        String custIdText = fcid.getText();
        String wname = fname.getText();
        String workerEmail = email.getText();
        String phone = phoneNumber.getText();

        if (custIdText.isEmpty() || wname.isEmpty() || workerEmail.isEmpty() || phone.isEmpty()) {
            showAlert("Error", "Please fill in all fields.", Alert.AlertType.ERROR);
            return;
        }

        try 
        {
            int id = Integer.parseInt(custIdText);
            
            String q = "UPDATE customer SET Name = ?, Email = ?, PhoneNumber = ? WHERE customerID = ?";
            int ra = SQLHandler.getInstance().updatecustomer(q, wname, workerEmail, phone, id);

            if(ra>0)
        	{
                showAlert("Success", "Customer updated successfully!", Alert.AlertType.INFORMATION);
            	
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageCustomer.fxml"));	
        		root = loader.load();	    		
        		ManageCustomerController hw = loader.getController();
                hw.setid(String.valueOf(ownerid));
        		hw.setUserName(name);
        		hw.setpass(password);
        		hw.setEmail(Email);
        		hw.setPhoneNo(phonenumber);
        		hw.setdp(dp);
        		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        		scene = new Scene(root);
        		stage.setScene(scene);
        		stage.show();
        	}
            
            
        } 
        catch (NumberFormatException e) 
        {
            showAlert("Error", "Worker ID and Salary must be numbers.", Alert.AlertType.ERROR);
        } 
        catch (SQLException e) 
        {
            showAlert("Database Error", "Error updating worker: " + e.getMessage(), Alert.AlertType.ERROR);
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
		hw.setUserName(name);
		hw.setpass(password);
		hw.setName(name);
		hw.setEmail(Email);
		hw.setPhoneNo(phonenumber);
		hw.setdp(dp);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
}
