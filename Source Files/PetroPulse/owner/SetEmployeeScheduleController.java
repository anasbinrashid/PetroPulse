package owner;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetEmployeeScheduleController 
{

    @FXML
    private TextField TimeText;
    @FXML
    private TextField TimeText1;

    @FXML
    private ComboBox<String> comboBox;
    
    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private DatePicker datePicker;
    public ArrayList<String> workernames = new ArrayList<>();
    public ArrayList<Integer> workerids = new ArrayList<>();


    public Stage stage;
    public Scene scene;
    public Parent root;
	
    public int ownerid;
    public String name;
    public String email;
    public String password;
    public String phonenumber;
    public double dp;
    public void setdp(double phoneno) {
        this.dp = phoneno;
    }
    
    @FXML
    private Label userNameLabel;
    
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
    
    public void setid(String id) throws SQLException
    {
    	this.ownerid = Integer.parseInt(id);
    	
        SQLHandler.getInstance().buildconnection();;
    	    
        String q= "select worker.workerid, worker.name from worker inner join owns on owns.stationid = worker.stationid inner join owner on owner.ownerid = owns.ownerid where owner.ownerid=?";
        ResultSet rs = SQLHandler.getInstance().loaddata(q, ownerid);
        
        while(rs.next())
        {
        	workernames.add(rs.getString(2));
        	workerids.add(rs.getInt(1));
        }
        
        rs.close();
        
        comboBox1.setItems(FXCollections.observableArrayList(workernames));
                
        SQLHandler.getInstance().closeconnection();
    	
    }
    
    @FXML
    void saleDetected(ActionEvent event) throws SQLException, IOException 
    {
        SQLHandler.getInstance().buildconnection();;
    	
        String day = comboBox.getValue();
        String workername = comboBox1.getValue();
        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : null;
        String stime = TimeText.getText();
        String etime = TimeText1.getText();
        
        if (date.isEmpty() || workername.isEmpty() || date.isEmpty() || stime.isEmpty() || etime.isEmpty()) 
        {
            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            
            return;
        }
        
        String q = "select workerid from worker where name = ?";
        int wid = SQLHandler.getInstance().getid(q, workername);
        
        q = "select stationid from worker where workerid = ?";
        int sid = SQLHandler.getInstance().getvalue(q, wid);
        
        q = "INSERT INTO schedule (day, date, startingtime, endingtime, stationid, workerid) VALUES (?, ?, ?, ?, ?, ?)";
        int ra = SQLHandler.getInstance().insertschedule(q, day, date, stime, etime, sid, wid);
        
        
        if(ra>0)
        {        	
            showAlert("Success", "Schedule Set successfully!", Alert.AlertType.INFORMATION);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("SetEmployeeSchedule.fxml"));	
    		root = loader.load();	    		
    		SetEmployeeScheduleController hw = loader.getController();
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
        
        SQLHandler.getInstance().closeconnection();
    }
    
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    @FXML
    public void initialize() 
    {
        // Initialize ComboBox
        comboBox.setItems(FXCollections.observableArrayList("Weekdays", "Weekends", "Price Change Day"));

        
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
