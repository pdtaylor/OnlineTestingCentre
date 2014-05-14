	package myOTCpack;
	import java.sql.*;
	import java.io.*;
	import javax.swing.JOptionPane;

	public class Student extends DatabaseUser implements Serializable {
		private int S_ID;
		private String coursename;
		private String fname;
		private String lname;
		private double score; 
		
		public Student(){
			S_ID=1111;
			coursename="";
			fname="";
			lname="";
			score = 0.00;			
		}
		public Student(int id, String cn, String f, String l,double s){
			S_ID= id;
			coursename= cn;
			fname = f;
			lname = l;
			score = s;
		}
		
		public int getS_ID(){
			return S_ID;
		}
		public String getcoursename(){
			return coursename;
		}
		public String getfname(){
			return fname;
		}
		public String getlname(){
			return lname;
		}
		public double getscore(){
			return score;
		}
		public void setS_ID(int id){
			S_ID= id;
		}
		public void setCourse(String cn){
			coursename= cn;
		}
		public void setfname(String f){
			fname = f;
		}
		public void setlname(String l){
			lname = l;
		}
		
		public void setscore(double s){
			score = s;
		}
		public String toString(){
			return "Student ID:" + S_ID + " Course Name:" + coursename + " fname:" + fname + " lname:" + lname;
		}
		public void insertStudent(){
			Connection conStudent;
			Statement statInsert=null;
			String strInsert="INSERT INTO students(s_ID, coursename, fname, lname, score)VALUES('" + S_ID + "', '" + coursename +"','" + fname + "', '" + lname + "', '"  + score + "')";
			int result;
			
			conStudent=createConnection();
			try{
				//step 3. Use the connection object derived to Create a SQL Statement
				statInsert=conStudent.createStatement();
				//step 4. Execute the statement and store the result.
				result=statInsert.executeUpdate(strInsert);
				//statInsert.executeQuery(arg0)
				statInsert.close();
				conStudent.close();
				if (result==1){
					JOptionPane.showMessageDialog(null, "Student was added successfully.");
				}
				else{
					JOptionPane.showMessageDialog(null, "Student was not added to the Database.");
				}
			
			}
			catch(SQLException e){
				JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
				//System.exit(1);
				//e.printStackTrace();
			}
					
		}
		public void editStudent(){
			Connection conStudent;
			Statement statUpdate=null;
			String strUpdate="UPDATE students SET s_ID="+ S_ID + ", coursename='" + coursename +"',fname='"+ fname +"', lname ='"+ lname +"',  score=" + score + " WHERE s_ID=" + S_ID + " AND coursename='"+ coursename +"'";
			
			//JOptionPane.showMessageDialog(null, strUpdate);
			int result;
			
			conStudent=this.createConnection();
			try{
				//step 3. Use the connection object derived to Create a SQL Statement
				statUpdate=conStudent.createStatement();
				//step 4. Execute the statement and store the result.
				result=statUpdate.executeUpdate(strUpdate);
				statUpdate.close();
				conStudent.close();
				if (result==1){
					JOptionPane.showMessageDialog(null, "Changes to Student are saved.");
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
		public void deleteStudent(){
			Connection conStudent;
			Statement statDelete=null;
			String strDelete="DELETE FROM students WHERE s_ID=" + S_ID + " AND coursename='"+ coursename +"'";
			int result;
			
			conStudent=this.createConnection();
			try{
				//step 3. Use the connection object derived to Create a SQL Statement
				statDelete=conStudent.createStatement();
				//step 4. Execute the statement and store the result.
				result=statDelete.executeUpdate(strDelete);
				statDelete.close();
				conStudent.close();
				if (result==1){
					JOptionPane.showMessageDialog(null, "The Student is deleted from the database.");
				}
				else{
					JOptionPane.showMessageDialog(null, "The Student could not be deleted.");
				}
			
			}
			catch(SQLException e){
				JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
				//System.exit(1);
				//e.printStackTrace();
			}
		}
		public ResultSet SearchStudent(boolean OTCSearch){
			Connection conStudent;
			Statement statFind=null;
			String strFind;
			ResultSet result=null;
			
			if (OTCSearch){
				strFind="SELECT * FROM students WHERE fname LIKE '" + fname + "' AND lname LIKE '" + lname +"'";
			}
			else{
				strFind="SELECT * FROM students WHERE s_ID=" + S_ID + " AND coursename='"+ coursename +"'" ;
			}
			
			conStudent=this.createConnection();
			try{
				
				statFind=conStudent.createStatement();
				result=statFind.executeQuery(strFind);
				
			}
			catch(SQLException e){
				JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
				//System.exit(1);
				//e.printStackTrace();
			}
			
			return result;			
			
		}
		
	}

