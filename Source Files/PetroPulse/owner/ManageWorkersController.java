package owner;
import application.*;
import worker.*;
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

public class ManageWorkersController 
{

    @FXML
    private TextField email;

    @FXML
    private TextField fname;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField salary;
    @FXML
    private TextField fsid;
    @FXML
    private TextField fwid;

    @FXML
    private TableColumn<Worker, Integer> workerId;

    @FXML
    private TableColumn<Worker, String> workerName;

    @FXML
    private TableColumn<Worker, String> workerEmail;

    @FXML
    private TableColumn<Worker, String> workerPhoneNumber;

    @FXML
    private TableColumn<Worker, Integer> workerSalary;
    
    @FXML
    private TableColumn<Worker, Integer> station;

    @FXML
    private TableView<Worker> workerTable;

    private ObservableList<Worker> workerList = FXCollections.observableArrayList();
    
    public Stage stage;
    public Scene scene;
    public Parent root;
	
    public int ownerid;
    public String name;
    public String Email;
    public String password;
    public String phonenumber;
    public double dp;
    public void setdp(double phoneno) {
        this.dp = phoneno;
    }
    public ArrayList<Integer> stationids = new ArrayList<>();
    
    @FXML
    public Label userNameLabel;
    
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
    public void initialize() throws SQLException 
    {
        SQLHandler.getInstance().buildconnection();;

        workerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        workerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        workerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        workerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        workerSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        station.setCellValueFactory(new PropertyValueFactory<>("station"));

        workerList.clear();

        String q = "SELECT WorkerID, worker.Name, worker.Email, worker.PhoneNumber, worker.Salary, worker.stationid FROM Worker inner join owns on worker.stationid = owns.stationid inner join owner on owns.ownerid = owner.ownerid where owner.ownerid = ?";

        try (ResultSet rs = SQLHandler.getInstance().loaddata(q, this.ownerid)) 
        {
            while (rs.next()) 
            {
                workerList.add(new Worker(
                        rs.getInt("WorkerID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber"),
                        rs.getInt("Salary"),
                        rs.getInt("StationID")
                ));
            }

            workerTable.setItems(workerList);
        } 
        catch (SQLException e) 
        {
            showAlert("Database Error", "Error loading data: " + e.getMessage(), Alert.AlertType.ERROR);
        }   
        
        q= "select stationid from owns where ownerid = ?";
        ResultSet rs = SQLHandler.getInstance().loaddata(q, ownerid);
        
        while(rs.next())
        {
        	stationids.add(rs.getInt(1));
        }
        
        rs.close();
        
        SQLHandler.getInstance().closeconnection();
    }

    @FXML
    void addWorker(ActionEvent event) throws IOException 
    {
        SQLHandler.getInstance().buildconnection();;
    	
        String wname = fname.getText();
        String workerEmail = email.getText();
        String wphone = phoneNumber.getText();
        String workerSalary = salary.getText();
        String workerstation = fsid.getText();
        
        if (wname.isEmpty() || workerEmail.isEmpty() || wphone.isEmpty() || workerSalary.isEmpty()) 
        {
            showAlert("Error", "Please fill in all fields (Except WorkerID)", Alert.AlertType.ERROR);
            
            return;
        }

        try 
        {
            double salaryValue = Double.parseDouble(workerSalary);

            String wpassword = wname + "123";
            
            boolean check = false;
            
            for(Integer sid : stationids)
            {
            	if(Integer.parseInt(workerstation) == sid)
            	{
            		String q = "INSERT INTO Worker (Name, Password, Email, PhoneNumber, Salary, StationID) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";
            		int ra = SQLHandler.getInstance().addworker(q, wname, wpassword, workerEmail, wphone, salaryValue, Integer.parseInt(workerstation));
	
	               if(ra>0)
	               {
	                    showAlert("Success", "Worker added successfully!", Alert.AlertType.INFORMATION);
	                   
	                    check = true;
	                    
	                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageWorkers.fxml"));	
		           		root = loader.load();	    		
		           		ManageWorkersController hw = loader.getController();
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
            }
            
            if(check == false)
            {
                showAlert("Error", "Wrong Input for Station ID.", Alert.AlertType.ERROR);

            }
            
            
        } 
        catch (NumberFormatException e) 
        {
            showAlert("Error", "Salary must be a number.", Alert.AlertType.ERROR);
        } 
        catch (SQLException e) 
        {
            showAlert("Database Error", "Error adding worker: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        
        SQLHandler.getInstance().closeconnection();
    }

    @FXML
    void removeWorker(ActionEvent event) throws IOException 
    {
        SQLHandler.getInstance().buildconnection();;

        String workerIdText = fwid.getText();

        if (workerIdText.isEmpty()) 
        {
            showAlert("Error", "Please enter the Worker ID to remove.", Alert.AlertType.ERROR);
            return;
        }

        try 
        {
            String q = "DELETE FROM Worker WHERE WorkerID = ?";
            int ra = SQLHandler.getInstance().updatemembership(q, workerIdText);

            if(ra>0)
        	{
            	showAlert("Success", "Worker removed successfully!", Alert.AlertType.INFORMATION);
            	
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageWorkers.fxml"));	
        		root = loader.load();	    		
        		ManageWorkersController hw = loader.getController();
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
            showAlert("Error", "Worker ID must be a number.", Alert.AlertType.ERROR);
        } 
        catch (SQLException e) 
        {
            showAlert("Database Error", "Error removing worker: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        
        SQLHandler.getInstance().closeconnection();
    }

    @FXML
    void updateWorker(ActionEvent event) throws IOException 
    {
        SQLHandler.getInstance().buildconnection();;

        String workerIdText = fwid.getText();
        String wname = fname.getText();
        String workerEmail = email.getText();
        String phone = phoneNumber.getText();
        String workerSalary = salary.getText();
        String stid = fsid.getText();

        if (workerIdText.isEmpty() || wname.isEmpty() || workerEmail.isEmpty() || phone.isEmpty() || workerSalary.isEmpty() || stid.isEmpty()) {
            showAlert("Error", "Please fill in all fields.", Alert.AlertType.ERROR);
            return;
        }

        try 
        {
            int id = Integer.parseInt(workerIdText);
            double salaryValue = Double.parseDouble(workerSalary);
            int staid = Integer.parseInt(stid);
            
            boolean check = false;
            
            for(Integer sid : stationids)
            {
            	if(Integer.parseInt(stid) == sid)
            	{
            		String q = "UPDATE Worker SET Name = ?, Email = ?, PhoneNumber = ?, Salary = ?, stationid=? WHERE WorkerID = ?";
                    int ra = SQLHandler.getInstance().updateworker(q, wname, workerEmail, phone, salaryValue, staid, id);

                    if(ra>0)
                	{
                        showAlert("Success", "Worker updated successfully!", Alert.AlertType.INFORMATION);
                    	
                        check = true;
                        
                    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageWorkers.fxml"));	
                		root = loader.load();	    		
                		ManageWorkersController hw = loader.getController();
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
            }
            
            if(check == false)
            {
                showAlert("Error", "Wrong Input for Station ID.", Alert.AlertType.ERROR);

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
