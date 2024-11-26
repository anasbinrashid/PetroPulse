package customer;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.SQLHandler;
import javafx.scene.control.Alert;

public class Customer 
{
    public int customerid;
    public String name;
    public String email;
    public String phonenumber;
    public String password;
    public int loyaltypoints;
    public int membershipstatus;
    public int stationid; // Foreign Key
    public double duepayment;
    PaymentContext paymentContext = new PaymentContext();

    
    public Customer()
    {
    	
    }
    
    public Customer(int i, String string, String string2, String string3, int j) 
    {
    	this.customerid = i;
    	this.name = string;
    	this.email = string2;
    	this.phonenumber = string3;
    	this.stationid = j;
    	
    }
    
    
	public void setstation(String station) {
    	this.stationid = Integer.parseInt(station);
    }
    public void setid(String id) {
    	this.customerid = Integer.parseInt(id);
    }
    public void setpass(String p) {
    	this.password= p;
    }
    public void setUserName(String name) {
    	this.name = name;
    }
    public void setEmail(String e) {
    	this.email = e;
    }
    public void setPhoneNo(String phoneno) {
    	this.phonenumber = phoneno;
    }
    public void setLoyaltyPoints(String cs) {
    	this.loyaltypoints = Integer.parseInt(cs);
    }
    public void setmem(String cs) {
    	this.membershipstatus= Integer.parseInt(cs);
    }
    public void setdue(String cs) {
    	this.duepayment= Double.parseDouble(cs);
    }
    
    public void applymembership() throws NumberFormatException, SQLException
    {
    	SQLHandler.getInstance().buildconnection();;
        
        String q = "update customer set customer.membershipstatus=1, customer.loyaltypoints=0 where customer.customerid= ?";
        SQLHandler.getInstance().updatemembership(q, String.valueOf(this.customerid));
    	
    	this.duepayment +=2500.0;
    	this.membershipstatus = 1;
    	this.loyaltypoints =0 ;
    	
    	q = "update customer set customer.duepayment = ? where customer.customerid = ?";
    	SQLHandler.getInstance().updateearnings(q, String.valueOf(this.customerid), String.valueOf(this.duepayment));
    	
    	SQLHandler.getInstance().closeconnection();
    }

    public boolean makePayment(String enteredAmount, String paymentType) throws SQLException {
        double amountValue;
        try {
            amountValue = Double.parseDouble(enteredAmount);
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid numeric amount.");
            return false;
        }

        try {
            // Set the appropriate payment strategy
                paymentContext.setPaymentStrategy(PaymentStrategyFactory.getInstance().getPaymentStrategy(paymentType));

            // Execute payment
            if (paymentContext.executePayment(amountValue, this)) {
                showAlert("Success", "Payment has been made successfully.");
                return true;
            } else {
                showAlert("Error", "Failed to save payment details.");
                return false;
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred during payment: " + e.getMessage());
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
    
    public double calculateFuelBill(String fueltype, double amountOfFuel) throws SQLException {
    	
        SQLHandler.getInstance().buildconnection();;

    	String q = "SELECT fuelstandid FROM fuelstand WHERE stationid = ? and fueltype=? LIMIT 1";
        int fuelstandid = SQLHandler.getInstance().getfuelstand(q, this.stationid, fueltype);
		
        if(fuelstandid == 0)
        {
            showAlert("Error", "No Fuel Stand on this Station!", Alert.AlertType.ERROR);
            return 0.0;
        }
        
		q= "select fueltype, fuelprice from fuelstand where fuelstand.fuelstandid=?";
        ResultSet rs = SQLHandler.getInstance().loginverification(q, String.valueOf(fuelstandid), "");
                
        double fuelprice = 0;
        if(rs.next())
        {
        	fuelprice = rs.getDouble(2);
        }
        
    	int updatedearnings = (int) (amountOfFuel * fuelprice);
        SQLHandler.getInstance().closeconnection();;

        return updatedearnings;
    }

    public boolean purchasefuel(String date, String time, double amountOfFuel, String customerID, String fueltype, double fuelprice)
    {
    	SQLHandler.getInstance().buildconnection();
    	try 
        {
    		String q = "SELECT fuelstandid FROM fuelstand WHERE stationid = ? and fueltype=? LIMIT 1";
            int fuelstandid = SQLHandler.getInstance().getfuelstand(q, this.stationid, fueltype);
    		
            if(fuelstandid == 0)
            {
                showAlert("Error", "No Fuel Stand on this Station!", Alert.AlertType.ERROR);
                return false;
            }
            
    		q= "select fueltype, fuelprice from fuelstand where fuelstand.fuelstandid=?";
            ResultSet rs = SQLHandler.getInstance().loginverification(q, String.valueOf(fuelstandid), "");
            
            if(rs.next())
            {
            	fuelprice = rs.getDouble(2);
            	
            	q= "select totalcapacity, capacity from fuelstand inner join station where fuelstand.stationid = station.stationid and station.stationid=? and fuelstand.fuelstandid=?";
                rs = SQLHandler.getInstance().loginverification(q, String.valueOf(stationid), String.valueOf(fuelstandid));
                
                int updatedstation, updatedstand;
                
                if(rs.next())
                {
                	int cap = rs.getInt(2);

                	if(cap>=amountOfFuel)
                	{
                		q = "INSERT INTO OrderDetails (OrderDate, Time, AmountOfFuel, CustomerID, fuelstandid) VALUES (?, ?, ?, ?, ?)";
                        int ra = SQLHandler.getInstance().insertorder(q, time, date, String.valueOf(amountOfFuel), customerID, String.valueOf(fuelstandid));
                        
                        if(ra>0)
                        {                 	                            
                            updatedstation = (int) (rs.getInt(1) - amountOfFuel);
                        	updatedstand = (int) (rs.getInt(2) - amountOfFuel);
                        	
                        	q = "update station set station.totalcapacity = ? where station.stationid = ?";
                        	SQLHandler.getInstance().update(q, String.valueOf(stationid), String.valueOf(updatedstation));
                        	
                        	q = "update fuelstand set fuelstand.capacity = ? where fuelstand.fuelstandid = ?";
                        	SQLHandler.getInstance().update(q, String.valueOf(fuelstandid), String.valueOf(updatedstand));
                        	
                        	this.loyaltypoints = this.loyaltypoints + (int)(5 * amountOfFuel);
                        	q = "update customer set customer.loyaltypoints= ? where customer.customerid= ?";
                        	SQLHandler.getInstance().updateearnings(q, String.valueOf(customerid), String.valueOf(this.loyaltypoints));
                        	
                        	double updatedearnings = (amountOfFuel * fuelprice);
                        	this.duepayment += updatedearnings;
                    		q = "update customer set customer.duepayment= ? where customer.customerid= ?";
                    		SQLHandler.getInstance().updateearnings(q, String.valueOf(customerid), String.valueOf(this.duepayment));
                        	
                            showAlert("Success", "Purchase Made successfully!", Alert.AlertType.INFORMATION);

                            
                            return true;
                        }
                        else
                        {
                            showAlert("Database Error", "Failed to record sale!", Alert.AlertType.ERROR);
                            return false;
                        }
                	}
                	else
                	{
                        showAlert("Error", "Not enough Fuel!", Alert.AlertType.ERROR);
                        return false;
                	}                	
                }
                else
                {
                    showAlert("Database Error", "Failed to record sale!", Alert.AlertType.ERROR);
                    return false;

                }
            }
            else
            {
                showAlert("Database Error", "Failed to record sale!", Alert.AlertType.ERROR);
                return false;

            }
        } 
    	catch (Exception e) 
    	{
            e.printStackTrace();
            showAlert("Database Error", "Failed to record sale: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
        
    }
    
	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLoyaltypoints() {
		return loyaltypoints;
	}

	public void setLoyaltypoints(int loyaltypoints) {
		this.loyaltypoints = loyaltypoints;
	}

	public int getMembershipstatus() {
		return membershipstatus;
	}

	public void setMembershipstatus(int membershipstatus) {
		this.membershipstatus = membershipstatus;
	}

	public int getStationid() {
		return stationid;
	}

	public void setStationid(int stationid) {
		this.stationid = stationid;
	}

	public double getDuepayment() {
		return duepayment;
	}

	public void setDuepayment(double duepayment) {
		this.duepayment = duepayment;
	}

	public String getEmail() {
		return email;
	}
	
	private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
