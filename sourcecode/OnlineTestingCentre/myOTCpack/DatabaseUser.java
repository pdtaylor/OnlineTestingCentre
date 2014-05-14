package myOTCpack;
import java.sql.*;
import javax.swing.JOptionPane;

public class DatabaseUser {
	protected final String DBDRIVER="sun.jdbc.odbc.JdbcOdbcDriver";
	protected final String CONSTRING="jdbc:odbc:assign_con";
	
	public String getDBDriver(){
		return DBDRIVER;
	}
	
	public String getConString(){
		return CONSTRING;
	}
	
	protected Connection createConnection(){
		Connection c=null;
		try{
		//step 1. Intialize the Database Driver Class
		Class.forName(DBDRIVER);
		//step 2. Use the DriverManager to create a connection. 
		c=DriverManager.getConnection(CONSTRING,"","");
		
		}
		catch(ClassNotFoundException e){
			JOptionPane.showMessageDialog(null, "System could not load JDBC/ODBC driver.\n" + e.getMessage()+"\n" + e.toString());
			System.exit(1);
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Unable to connect to Database specified.\n" + e.getMessage()+"\n" + e.toString());
			//System.exit(1);
			e.printStackTrace();
		}
		return c;		
	}
}
