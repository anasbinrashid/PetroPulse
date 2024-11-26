package owner;
import application.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.*;
import javafx.stage.*;

public class LoginOwnerController 
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

        String q = "select * from owner where name = ? AND password = ?";

        ResultSet rs= SQLHandler.getInstance().loginverification(q, username, password);
                
        if (rs.next()) 
        {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeOwner.fxml"));	
    		root = loader.load();	    		
            HomeOwnerController ho = loader.getController();
    		ho.setUserName(rs.getString(3));
    		ho.setEmail(rs.getString(2));
    		ho.setPhoneNo(rs.getString(4));
    		ho.setName(rs.getString(3));
    		ho.setid(rs.getString(1));
    		ho.setpass(rs.getString(5));
    		ho.setdp(rs.getDouble(6));
        	
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
    	root = FXMLLoader.load(getClass().getResource("SignupOwner.fxml"));
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
