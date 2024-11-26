package owner;
import application.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.*;
import javafx.stage.*;

public class AddStationController 
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
    private TextField fname;
    @FXML
    private TextField fcom;
    @FXML
    private TextField floc;
    @FXML
    private DatePicker fdate;
    
    public double dp;
    public void setdp(double phoneno) {
        this.dp = phoneno;
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
    public void setid(String phoneno) {
        this.ownerid = Integer.parseInt(phoneno);
    }
    
    @FXML
    public void enterclicked(ActionEvent event) throws IOException, SQLException 
    {
        SQLHandler.getInstance().buildconnection();;
    	
        String name = fname.getText();
        String company = fcom.getText();
        String location = floc.getText();
        String date = (fdate.getValue() != null) ? fdate.getValue().toString() : null;

        
        if (name.isEmpty() || company.isEmpty() || location.isEmpty() || date.isEmpty()) 
        {
            showalert(AlertType.WARNING, "Form Error!", "Please enter complete details!");
            return;
        }
        
        String q = "select * from station where name = ?";
        ResultSet rea = SQLHandler.getInstance().checkstation(q, name);
        
        if(rea.next())
        {
        	showalert(AlertType.ERROR, "Error", "Station Already Exists!");
        	return;
        }
        
        q = "insert into station (name, location, totalcapacity, company, totalearnings) values (?, ?, ?, ?, ?)";

        int ra = SQLHandler.getInstance().addstation(q, name, location, company);
        
        if (ra > 0) 
        {
            q = "select stationid from station where name = ?";
            int sid = SQLHandler.getInstance().getid(q, name);
        	
        	q = "insert into owns (ownerid, stationid, ownershipdate) values (?, ?, ?)";
            int rs = SQLHandler.getInstance().addowns(q, this.ownerid, sid, date);
        	
            if(rs>0)
            {
            	showalert(AlertType.INFORMATION, "Successful", "Station Added!");

            	SQLHandler.getInstance().closeconnection();
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStation.fxml"));	
        		root = loader.load();	    		
        		AddStationController hw = loader.getController();
                hw.setid(String.valueOf(ownerid));
        		hw.setUserName(this.name);
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
        else 
        {
        	showalert(AlertType.ERROR, "Failed", "Station Creation failed!");
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
