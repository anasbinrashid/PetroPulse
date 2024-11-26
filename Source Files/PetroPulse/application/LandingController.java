package application;

import java.io.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.stage.*;

public class LandingController 
{
    public Stage stage;
    public Scene scene;
    public Parent root;
    
    public void switchscenetologinowner(ActionEvent event) throws IOException 
    {
		root = FXMLLoader.load(getClass().getResource("/owner/LoginOwner.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    public void switchscenetologinworker(ActionEvent event) throws IOException 
    {
		root = FXMLLoader.load(getClass().getResource("/worker/LoginWorker.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    public void switchscenetologincustomer(ActionEvent event) throws IOException 
    {
		root = FXMLLoader.load(getClass().getResource("/customer/LoginCustomer.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
}
