package myOTCpack;
import javax.swing.*;
import java.sql.*;

public class User extends DatabaseUser 
{
	private String userid;
	private String password;
	private String fname;
	private String lname;
	private String confirmpassword;
	private int level;
	
	public User()
	{
		userid="";
		password="";
		fname="";
		lname="";
		confirmpassword="";
		level=0;
	}
	
	public User(String u, String p, String f, String l, String cp,int v)
	{
		userid=u;
		password=p;
		fname=f;
		lname=l;
		level=v;
		confirmpassword=cp;
	}
	
	public void setUserID(String u)
	{
		userid=u;
	}
	
	public void setLevel(int v)
	{
		level=v;
	}
	
	public void setCPassword(String cp)
	{
		confirmpassword=cp;
	}
	
	public void setLName(String l)
	{
		lname=l;
	}
	
	public void setPassword(String p)
	{
		password=p;
	}

	public void setFName(String f)
	{
		fname=f;
	}
	
	void addUser()
	{
		Connection conUser;
		Statement statInsert=null;
		String strInsert="INSERT INTO users(u_ID, u_fname, u_lname, pword, confirm_pword, u_level) VALUES ('" + userid + "', '" + fname + "', '" + lname + "', '" + password +"', '"+ confirmpassword + "', "+ level +")";
		int result;
		
		conUser=createConnection();
		try{
			//step 3. Use the connection object derived to Create a SQL Statement
			statInsert=conUser.createStatement();
			//step 4. Execute the statement and store the result.
			result=statInsert.executeUpdate(strInsert);
			statInsert.close();
			conUser.close();
			if (result==1){
				JOptionPane.showMessageDialog(null, "User was added successfully.");
			}
			else{
				JOptionPane.showMessageDialog(null, "User was not added to the Database.");
			}
		
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
			//System.exit(1);
			//e.printStackTrace();
		}
				
	}
	
	public void deleteUser()
	{
		Connection conUser;
		Statement statDelete=null;
		String strDelete="DELETE FROM users WHERE u_ID='" + userid + "' AND pword='"+ password +"'";
		int result;
		
		conUser=this.createConnection();
		try{
			//step 3. Use the connection object derived to Create a SQL Statement
			statDelete=conUser.createStatement();
			//step 4. Execute the statement and store the result.
			result=statDelete.executeUpdate(strDelete);
			statDelete.close();
			conUser.close();
			if (result==1){
				JOptionPane.showMessageDialog(null, "The User is deleted from the database.");
			}
			else{
				JOptionPane.showMessageDialog(null, "The User could not be deleted.");
			}
		
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
			//System.exit(1);
			//e.printStackTrace();
		}
	}
	
	public void modifyUser()
	{
			Connection conUser;
			Statement statUpdate=null;
			String strUpdate="UPDATE users SET u_ID='"+ userid + "', pword='"+ password +"', u_fname='"+ fname +"', u_lname='" + lname + "', confirm_pword='" + confirmpassword + "', u_level=" + level + " WHERE u_ID='" + userid + "' AND pword='" + password +"'";
			//JOptionPane.showMessageDialog(null, strUpdate);
			int result;
			
			conUser=this.createConnection();
			try{
				//step 3. Use the connection object derived to Create a SQL Statement
				statUpdate=conUser.createStatement();
				//step 4. Execute the statement and store the result.
				result=statUpdate.executeUpdate(strUpdate);
				statUpdate.close();
				conUser.close();
				if (result==1){
					JOptionPane.showMessageDialog(null, "Changes to user account are saved.");
				}
				else{
					JOptionPane.showMessageDialog(null, "Changes could not be saved to the Database.");
				}
			
			}
			catch(SQLException e){
				JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
				//System.exit(1);
				//e.printStackTrace();
			}
	}

	
	
	public ResultSet findUser(){
		Connection conUsers;
		Statement statFind=null;
		String strFind="SELECT * FROM users WHERE u_ID='" + userid + "' AND pword='" + password + "'";
		ResultSet result=null;
		
		conUsers=this.createConnection();
		try
		{
			
			statFind=conUsers.createStatement();
			result=statFind.executeQuery(strFind);
			
		}
		
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
			//System.exit(1);
			//e.printStackTrace();
		}
		
		return result;			
		
	}	
	
	public String getUserid()
	{
		return userid;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getFName()
	{
		return fname;
	}
	
	public String getLName()
	{
		return lname;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	
}
