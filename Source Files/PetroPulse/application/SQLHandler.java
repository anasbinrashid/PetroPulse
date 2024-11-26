package application;
import java.sql.*;

public class SQLHandler extends PersistenceHandler
{
	private static SQLHandler instance; // Singleton instance
    private Connection connection;

    private SQLHandler() 
    {
    }

    public static SQLHandler getInstance() 
    {
        if (instance == null) 
        {
            synchronized (SQLHandler.class) 
            { // Thread safety
                if (instance == null) 
                {
                    instance = new SQLHandler();
                }
            }
        }
        
        return instance;
    }
    
    public void buildconnection()
    {
    	String url = "jdbc:mysql://localhost:3306/PetroPulse";
        String user = "root";
        String password = "anasbinrashid";

        try 
        {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("\nConnection to the database was successful!");
        } 
        catch (SQLException e) 
        {
            System.out.println("\nFailed to connect to the database!");
            e.printStackTrace();
        }
    }

    public ResultSet loginverification(String q, String u, String p) throws SQLException 
    {		    	
    	if (p == null || p.isEmpty()) 
    	{
    		PreparedStatement pstmt = connection.prepareStatement(q);
        	pstmt.setString(1, u);

            ResultSet rs = pstmt.executeQuery();
            
            return rs;
    	}
    	
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, u);
        pstmt.setString(2, p);

        ResultSet rs = pstmt.executeQuery();
        
        return rs;
    }
	
    public ResultSet onlyquery(String q) throws SQLException
    {
    	PreparedStatement pstmt = connection.prepareStatement(q);

        ResultSet rs = pstmt.executeQuery();
        
        return rs;
    }
    
    public int signupcustomer(String q, String n, String e, String p, String no, String s) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, n);
        pstmt.setString(2, e);
        pstmt.setString(3, p);
        pstmt.setString(4, no);
        pstmt.setInt(5, 0);
        Boolean b = false;
        pstmt.setBoolean(6, b);
        int st = Integer.parseInt(s); 
        pstmt.setInt(7, st);
        pstmt.setDouble(8, 0.0);

        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int addworker(String q, String n, String e, String p, String no, double sal, int stid) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, n);
        pstmt.setString(2, e);
        pstmt.setString(3, p);
        pstmt.setString(4, no);
        pstmt.setDouble(5, sal);
        pstmt.setInt(6, stid);

        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int addstation(String q, String n, String e, String p) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, n);
        pstmt.setString(2, e);
        pstmt.setInt(3, 0);
        pstmt.setString(4, p);
        pstmt.setDouble(5, 0.0);

        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int addowns(String q, int n, int e, String p) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setInt(1, n);
        pstmt.setInt(2, e);
        pstmt.setString(3, p);
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int insertfuelstand(String q, int n, String e, int p, int stid, double p2) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setInt(1, n);
        pstmt.setString(2, e);
        pstmt.setDouble(3, p2);
        pstmt.setInt(4, p);
        pstmt.setInt(5, stid);

        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int update(String q, String id, String val) throws NumberFormatException, SQLException
    {
    	PreparedStatement pstmt = connection.prepareStatement(q);
        pstmt.setInt(1, Integer.parseInt(val));
        pstmt.setInt(2, Integer.parseInt(id));
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int updateearnings(String q, String id, String val) throws NumberFormatException, SQLException
    {
    	PreparedStatement pstmt = connection.prepareStatement(q);
        pstmt.setDouble(1, Double.parseDouble(val));
        pstmt.setInt(2, Integer.parseInt(id));
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int updatefp(String q, String id, String val, String ft) throws NumberFormatException, SQLException
    {
    	PreparedStatement pstmt = connection.prepareStatement(q);
        pstmt.setDouble(1, Double.parseDouble(id));
        pstmt.setInt(2, Integer.parseInt(val));
        pstmt.setString(3, ft);
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int updatemembership(String q, String id) throws NumberFormatException, SQLException
    {
    	PreparedStatement pstmt = connection.prepareStatement(q);
        pstmt.setInt(1, Integer.parseInt(id));
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public ResultSet loaddata(String q, int id) throws SQLException
    {
    	PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();
        
        return rs;
    }
    
    public ResultSet checkstation (String q, String name)  throws SQLException
    {
    	PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, name);

        ResultSet rs = pstmt.executeQuery();
        
        return rs;
    }
    
    public int getvalue(String q, int id) throws SQLException 
    {
        PreparedStatement pstmt = connection.prepareStatement(q);
        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        // Assuming we are expecting exactly one result and one column
        int value = 0;
        if (rs.next()) 
        {
            value = (int) rs.getInt(1); // Fetch the first column's value
        }

        rs.close();  // Close the ResultSet
        pstmt.close(); // Close the PreparedStatement
        
        return value; // Return the single value as an Object
    }
    
    public int getid(String q, String n) throws SQLException 
    {
        PreparedStatement pstmt = connection.prepareStatement(q);
        pstmt.setString(1, n);

        ResultSet rs = pstmt.executeQuery();

        // Assuming we are expecting exactly one result and one column
        int value = 0;
        if (rs.next()) 
        {
            value = (int) rs.getInt(1); // Fetch the first column's value
        }

        rs.close();  // Close the ResultSet
        pstmt.close(); // Close the PreparedStatement
        
        return value; // Return the single value as an Object
    }
    
    public double getfuelprice(String q, int id) throws SQLException 
    {
        PreparedStatement pstmt = connection.prepareStatement(q);
        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        // Assuming we are expecting exactly one result and one column
        double value = 0;
        if (!rs.next()) {
            System.out.println("No records found for workerid: " + id);
        } else {
            value = rs.getDouble(1);
        } 

        rs.close();  // Close the ResultSet
        pstmt.close(); // Close the PreparedStatement
        
        return value; // Return the single value as an Object
    }
    
    public int getfuelstand(String q, int id, String ft) throws SQLException 
    {
        PreparedStatement pstmt = connection.prepareStatement(q);
        pstmt.setInt(1, id);
        pstmt.setString(2, ft);

        ResultSet rs = pstmt.executeQuery();

        // Assuming we are expecting exactly one result and one column
        int value = 0;
        if (rs.next()) 
        {
            value = (int) rs.getInt(1); // Fetch the first column's value
        }

        rs.close();  // Close the ResultSet
        pstmt.close(); // Close the PreparedStatement
        
        return value; // Return the single value as an Object
    }
    
    public ResultSet getfuelstand1(String q, int id, String ft) throws SQLException 
    {
        PreparedStatement pstmt = connection.prepareStatement(q);
        pstmt.setInt(1, id);
        pstmt.setString(2, ft);

        ResultSet rs = pstmt.executeQuery();
        
        return rs; // Return the single value as an Object
    }
 
    public int insertorder(String q, String n, String e, String p, String no, String s) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, e);
        pstmt.setString(2, n);
        pstmt.setDouble(3, Double.parseDouble(p));
        pstmt.setInt(4, Integer.parseInt(no));
        pstmt.setInt(5, Integer.parseInt(s));
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int insertmaintenance(String q, String n, int e, int p, String no, int oid) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, n);
        pstmt.setInt(2, e);
        pstmt.setInt(3, p);
    	pstmt.setString(4, no);
    	pstmt.setInt(5, oid);
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int insertschedule(String q, String n, String e, String p, String no, int oid, int wid) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, n);
        pstmt.setString(2, e);
        pstmt.setString(3, p);
    	pstmt.setString(4, no);
    	pstmt.setInt(5, oid);
    	pstmt.setInt(6, wid);
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int insertfuelorder(String q, double n, String s, int e, int p) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setDouble(1, n);
        pstmt.setString(2, s);
    	pstmt.setInt(3, e);
    	pstmt.setInt(4, p);
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int updateworker(String q, String n, String e, String p, double sal, int st, int wid) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, n);
        pstmt.setString(2, e);
        pstmt.setString(3, p);
        pstmt.setDouble(4, sal);
        pstmt.setInt(5, st);
        pstmt.setInt(6, wid);
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int updatecustomer(String q, String n, String e, String p, int cid) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, n);
        pstmt.setString(2, e);
        pstmt.setString(3, p);
        pstmt.setInt(4, cid);
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int insertpayment(String q, String paymentType, double amount, int customerID, int stationID) throws SQLException 
    {		
        PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, paymentType);
        pstmt.setDouble(2, amount);
        pstmt.setInt(3, customerID);
        pstmt.setInt(4, stationID);
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    public int clean(String q, int wid, String c) throws SQLException
    {
    	PreparedStatement pstmt = connection.prepareStatement(q);
    	pstmt.setString(1, "Worker");
        pstmt.setString(2, "Worker cleaned " + c);
        pstmt.setInt(3, wid);
        
        int ra = pstmt.executeUpdate();
        
        return ra;
    }
    
    // Close connection

    @Override
    public void closeconnection() 
    {
        if (connection != null) 
        {
            try 
            {
                connection.close();
                System.out.println("\nDatabase connection closed!");
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
