package customer;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeCustomerController 
{
	public Stage stage;
    public Scene scene;
    public Parent root;
    
    Customer c = new Customer();
    
    @FXML
    private Label userNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label loyaltypointsLabel;

    public void setval(Customer c)
    {
    	this.c.customerid = c.customerid;
    	this.c.duepayment = c.duepayment;
    	this.c.email = c.email;
    	this.c.loyaltypoints = c.loyaltypoints;
    	this.c.membershipstatus = c.membershipstatus;
    	this.c.name = c.name;
    	this.c.password = c.password;
    	this.c.phonenumber = c.phonenumber;
    	this.c.stationid = c.stationid;
    	
        userNameLabel.setText(this.c.name);
    	emailLabel.setText(this.c.email);
        phoneLabel.setText(this.c.phonenumber);
    }

    
    public void makepayment(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("MakePayment.fxml"));	
		root = loader.load();	    		
		MakePaymentController hw = loader.getController();
		hw.setval(this.c);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    public void membership(ActionEvent event) throws IOException, SQLException 
    {
    	if(this.c.membershipstatus== 1)
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("AlreadyMember.fxml"));	
    		root = loader.load();	    		
    		AlreadyMemberController hw = loader.getController();
    		hw.setval(this.c);
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    	else
    	{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("ApplyMember.fxml"));	
    		root = loader.load();	    		
    		ApplyMemberController hw = loader.getController();
    		hw.setval(this.c);

    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    		
    		
    	}
    }
    
    public void purchasefuel(ActionEvent event) throws IOException, SQLException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("PurchaseFuel.fxml"));	
		root = loader.load();	    		
		PurchaseFuelController hw = loader.getController();
		hw.setval(this.c);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    public void signout(ActionEvent event) throws IOException 
    {
    	root = FXMLLoader.load(getClass().getResource("/application/LandingPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
}
