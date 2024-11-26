package customer;
import application.*;
import java.io.IOException;
import java.sql.SQLException;

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

public class PurchaseFuelController 
{
	@FXML
    public Label nameLabel;
	public Stage stage;
    public Scene scene;
    public Parent root;
	
    Customer c = new Customer();

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
    	
        nameLabel.setText(this.c.name);

    }
    
    
    @FXML
    private TextField TimeText;
    
    @FXML
    private TextField customertext;

    @FXML
    private Label myLabel;

    @FXML
    private Slider mySlider;

    @FXML
    private ComboBox<String> comboBox;

    @FXML 
    private Label bill;

    
    @FXML
    private DatePicker datePicker;

    @FXML
    public void initialize() 
    {
        comboBox.setItems(FXCollections.observableArrayList("Petrol", "Diesel", "Hi-Octane"));

        // Bind the label text to the slider value
        myLabel.textProperty().bind(mySlider.valueProperty().asString("Number of Litres: %.1f"));

        // Optional: Set an initial value for the slider if needed
        mySlider.setValue(0); // Default slider value
    }

    @FXML
    void showbill(ActionEvent event) throws SQLException
    {

        String fueltype = comboBox.getValue();

        double amountOfFuel = mySlider.getValue();
        
        
        double updatedearningss = this.c.calculateFuelBill(fueltype, amountOfFuel);
        
        int updatedearnings = (int) updatedearningss;
        
    	bill.setText("Bill: Rs. "+updatedearnings+".0");


    }
    
    @FXML
    void saleDetected(ActionEvent event) throws NumberFormatException, SQLException, IOException 
    {
        SQLHandler.getInstance().buildconnection();;

        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : null;
        String time = TimeText.getText();
        double amountOfFuel = mySlider.getValue();
        String customerID = String.valueOf(this.c.customerid); 
        String fueltype = comboBox.getValue();
        double fuelprice = 0;

        if (date == null || time.isEmpty() || comboBox.getValue() == null || customerID.isEmpty()) 
        {
            showAlert("Error", "Please ensure all fields are filled!", Alert.AlertType.ERROR);
            return;
        }

        this.c.purchasefuel(date, time, amountOfFuel, customerID, fueltype, fuelprice);
        
        SQLHandler.getInstance().closeconnection();

        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PurchaseFuel.fxml"));	
		root = loader.load();	    		
		PurchaseFuelController hw = loader.getController();
		hw.setval(this.c);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

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
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeCustomer.fxml"));	
		root = loader.load();	    		
        HomeCustomerController hw = loader.getController();
        hw.setval(this.c);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
}
