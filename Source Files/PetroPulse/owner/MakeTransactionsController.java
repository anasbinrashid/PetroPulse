package owner;
import application.*;
import java.io.IOException;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class MakeTransactionsController 
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
    private Label amountLabel;
	
    @FXML
    private ToggleGroup Payment;

    @FXML
    private TextField amount;

    @FXML
    private RadioButton cashr1;

    @FXML
    private RadioButton cashr2;
    
    @FXML
    public ComboBox<String> cb;  
    
    public double dp;
    public void setdp(double phoneno) {
        this.dp = phoneno;
    	int val = (int) this.dp;
    	
    	amountLabel.setText("Rs. " + String.valueOf(val) + ".0");
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
    
    public void setid(String id)
    {
    	this.ownerid = Integer.parseInt(id);
    }
    
    @FXML
    void initialize()
    {
    	ArrayList<String> num = new ArrayList<>();
        
        num.add("Attock");
        num.add("Shell");
        num.add("Total");
        num.add("Aramco");
        num.add("Caltex");
        
        cb.setItems(FXCollections.observableArrayList(num));
    }
    
    @FXML
    void enterPayment(ActionEvent event) throws SQLException, IOException 
    {
        SQLHandler.getInstance().buildconnection();;

        String enteredAmount = amount.getText().trim();
        RadioButton selectedPaymentType = (RadioButton) Payment.getSelectedToggle();
        String n = cb.getValue();

        if (enteredAmount.isEmpty() || selectedPaymentType == null || n.isEmpty()) 
        {
            showAlert("Error", "Please enter an amount and select a payment type!");
            return;
        }

        String paymentType = selectedPaymentType.getText();
        int oid = this.ownerid; 
        
        String q = "select supplierid from supplier where name = ?";
        int sid = SQLHandler.getInstance().getid(q, n);

        try 
        {
            double amountValue = Double.parseDouble(enteredAmount);

            if (insertPayment(paymentType, amountValue, oid, sid)) 
            {
                showAlert("Success", "Transaction has been made successfully.");
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MakeTransactions.fxml"));	
        		root = loader.load();	    		
        		MakeTransactionsController hw = loader.getController();
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
            else 
            {
                showAlert("Error", "Failed to save payment details.");
            }
        } 
        catch (NumberFormatException e) 
        {
            showAlert("Error", "Please enter a valid numeric amount.");
        }
        
        SQLHandler.getInstance().closeconnection();

    }

    private boolean insertPayment(String paymentType, double amount, int oid, int sid) throws SQLException 
    {
        SQLHandler.getInstance().buildconnection();;

    	
    	String q = "INSERT INTO transaction (paymenttype, amount, ownerid, supplierid) VALUES (?, ?, ?, ?)";
        int ra = SQLHandler.getInstance().insertpayment(q, paymentType, amount, oid, sid);
        
        if(ra>0)
        {
        	this.dp -=amount;
        	
        	q = "update owner set owner.duepayment = ? where owner.ownerid = ?";
        	SQLHandler.getInstance().updateearnings(q, String.valueOf(this.ownerid), String.valueOf(this.dp));
        	
        	SQLHandler.getInstance().closeconnection();
        	
        	return true;
        	
        }
        else
        {
        	SQLHandler.getInstance().closeconnection();

            showAlert("Database Error", "Failed to record sale!");
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
