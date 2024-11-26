package worker;
import application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewScheduleController 
{
	public Stage stage;
    public Scene scene;
    public Parent root;
	
    @FXML
    public Label nameLabel;
    
    public int workerid;
    public String name;
    public String email;
    public String password;
    public String phonenumber;
    public double salary;
    public int stationid;
    
    
    public void setUserName(String name) {
    	this.name = name;
        nameLabel.setText(name);
    }
    public void setEmail(String e) {
    	this.email = e;
    }
    public void setPhoneNo(String phoneno) {
    	this.phonenumber = phoneno;
    }
    public void setsalary(String sal) {
    	this.salary = Double.parseDouble(sal);
    }
    public void setstation(String station) {
    	this.stationid = Integer.parseInt(station);
    }
    public void setid(String id) throws SQLException {
    	 this.workerid = Integer.parseInt(id);
    	 loadSchedule();
    }
    public void setpass(String p) {
    	this.password= p;
    }

    
    @FXML
    private TableColumn<Schedule, Integer> stationId;
    @FXML
    private TableColumn<Schedule, String> date;
    @FXML
    private TableColumn<Schedule, String> day;
    @FXML
    private TableColumn<Schedule, String> startTime;
    @FXML
    private TableColumn<Schedule, String> eTime;
    @FXML
    private TableColumn<Schedule, String> workerId;
    @FXML
    private TableView<Schedule> scheduleTable;

    private ObservableList<Schedule> ScheduleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException 
    {
    }


    private void loadSchedule() throws SQLException 
    {
        SQLHandler.getInstance().buildconnection();;

    	
    	stationId.setCellValueFactory(new PropertyValueFactory<>("stationId"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        eTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        day.setCellValueFactory(new PropertyValueFactory<>("day"));
        workerId.setCellValueFactory(new PropertyValueFactory<>("workerId"));
    	
        String query = "SELECT schedule.StationID, schedule.date, schedule.day ,schedule.StartingTime, schedule.EndingTime, worker.workerid FROM worker INNER JOIN schedule ON worker.workerid = schedule.workerid where worker.workerid = ?";
        
        ResultSet resultSet = SQLHandler.getInstance().loaddata(query, this.workerid);
        
        while (resultSet.next()) 
        {
            ScheduleList.add(new Schedule(resultSet.getInt("StationID"),resultSet.getString("Date"),resultSet.getString("Day"),resultSet.getString("StartingTime"),resultSet.getString("EndingTime"),resultSet.getInt("WorkerID")));
        }

        scheduleTable.setItems(ScheduleList);
        
        SQLHandler.getInstance().closeconnection();

    }
    
    public void goback(ActionEvent event) throws IOException 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeWorker.fxml"));	
		root = loader.load();	    		
        HomeWorkerController hw = loader.getController();
        hw.setid(String.valueOf(workerid));
		hw.setUserName(name);
		hw.setpass(password);
		hw.setEmail(email);
		hw.setPhoneNo(phonenumber);
		hw.setsalary(String.valueOf(salary));
		hw.setstation(String.valueOf(stationid));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
}
