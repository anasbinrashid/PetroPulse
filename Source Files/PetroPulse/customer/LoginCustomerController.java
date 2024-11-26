package customer;
import application.*;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

//import java.sql.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.*;
import javafx.stage.*;

public class LoginCustomerController 
{

	public TextField fname;
    public PasswordField fpass;
    public Stage stage;
    public Scene scene;
    public Parent root;

    
    @FXML
    void enterclicked(ActionEvent event) throws IOException, SQLException 
    {
        SQLHandler.getInstance().buildconnection();;
    	
        String username = fname.getText();
        String password = fpass.getText();

        if (username.isEmpty() || password.isEmpty()) 
        {
            showalert(AlertType.WARNING, "Form Error!", "Please enter your username and password!");
            return;
        }

        String q = "select * from customer where name = ? AND password = ?";

        ResultSet rs= SQLHandler.getInstance().loginverification(q, username, password);
        
        if (rs.next()) 
        {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeCustomer.fxml"));	
    		root = loader.load();
    		
    		Customer hw = new Customer();
            hw.setid(rs.getString(1));
    		hw.setUserName(rs.getString(2));
    		hw.setEmail(rs.getString(3));
    		hw.setpass(rs.getString(4));
    		hw.setPhoneNo(rs.getString(5));
    		hw.setLoyaltyPoints(rs.getString(6));
    		hw.setmem(rs.getString(7));
    		hw.setstation(rs.getString(8));
    		hw.setdue(rs.getString(9));
    		
            HomeCustomerController h = loader.getController();
            h.setval(hw);
        	
            showalert(AlertType.INFORMATION, "Login Successful", "You have successfully logged in!");
            
            SQLHandler.getInstance().closeconnection();
            
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
        } 
        else 
        {
            showalert(AlertType.ERROR, "Login Failed", "Incorrect username or password.");
        }
        
    }

    private void showalert(AlertType alertType, String title, String message) 
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    public void showsignup(ActionEvent event) throws IOException 
    {
    	root = FXMLLoader.load(getClass().getResource("SignupCustomer.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    public void goback(ActionEvent event) throws IOException 
    {
    	root = FXMLLoader.load(getClass().getResource("/application/LandingPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
}
