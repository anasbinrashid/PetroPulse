package customer;
import application.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.*;
import javafx.stage.*;

public class SignupCustomerController 
{
    @FXML
    public TextField fname;
    @FXML
    public PasswordField fpass;
    @FXML
    public TextField femail;
    @FXML
    public TextField fphone;
    @FXML
    public ComboBox<String> combo;
    @FXML
    public ArrayList<String> stationnames = new ArrayList<>();

    public Stage stage;
    public Scene scene;
    public Parent root;

    
    @FXML
    public void initialize() throws SQLException
    {
        SQLHandler.getInstance().buildconnection();;

    	
    	String q = "select station.name from station";
        ResultSet rs = SQLHandler.getInstance().onlyquery(q);

        while(rs.next())
        {
        	stationnames.add(rs.getString(1));
        }
        
        combo.setItems(FXCollections.observableArrayList(stationnames));
        
        rs.close();
        
        SQLHandler.getInstance().closeconnection();;

    }
    
    @FXML
    public void enterclicked(ActionEvent event) throws IOException, SQLException 
    {
        SQLHandler.getInstance().buildconnection();;

    	
        String username = fname.getText();
        String password = fpass.getText();
        String email = femail.getText();
        String number = fphone.getText();
        String station = combo.getValue();
        
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || number.isEmpty() || station.isEmpty()) 
        {
            showalert(AlertType.WARNING, "Form Error!", "Please enter your username and password!");
            return;
        }
        
        String q= "select stationid from station where name = ?";
        int stid = SQLHandler.getInstance().getid(q, station);
        
        q = "insert into customer (name, email, password, phonenumber, loyaltypoints, membershipstatus, stationid, duepayment) values (?, ?, ?, ?, ?, ?, ?, ?)";

        int ra = SQLHandler.getInstance().signupcustomer(q, username, email, password, number, String.valueOf(stid));
        
        if (ra > 0) 
        {
        	showalert(AlertType.INFORMATION, "Signup Successful", "You have successfully Signed Up!");
            
        	SQLHandler.getInstance().closeconnection();
                
            root = FXMLLoader.load(getClass().getResource("LoginCustomer.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    		
        } 
        else 
        {
        	showalert(AlertType.ERROR, "Signup Failed", "Customer signup failed!");
        }
    }

    public void showalert(AlertType alertType, String title, String message) 
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void showlogin(ActionEvent event) throws IOException 
    {
    	root = FXMLLoader.load(getClass().getResource("LoginCustomer.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
}
