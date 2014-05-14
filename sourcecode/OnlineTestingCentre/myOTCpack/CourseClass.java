package myOTCpack;

import java.sql.*;

import javax.swing.JOptionPane;

public class CourseClass extends DatabaseUser{
	private String courseCode;
	private String courseDescription;
	
	
	//Default constructor
	public CourseClass(){
		courseCode = " ";
		courseDescription = " ";
	}
	
	//Primary constructor
	public CourseClass(String cCode, String cDescription){
		courseCode = cCode;
		courseDescription = cDescription;
	}
	
	//Accessors
	public String getCourseCode(){
		return courseCode;
	}
	public String getCourseDescription(){
		return courseDescription;
	}
	
	//Mutators
	void setCourseCode(String cCode){
		courseCode = cCode;
	}
	void setCourseDescription(String cDescription){
		courseDescription = cDescription;
	}
	
	//Show Method
	void show (){
		//JOptionPane.showMessageDialog(null,"Testing...");
		JOptionPane.showMessageDialog(null,"Testing");
	}
	
	//Method for converting to string
	/*public String toString(){
		return "ID:" + _qID + " Course:" + _course + " Question:" + _question + " A:" + _optionA + " B:" + _optionB + " C:" + _optionC + " D:" + _optionD + " Answer:" + _answer;
	}*/
	
	//Create a method for Adding/Inserting Records in the Database
	public void insertCourse(){
		Connection conCourses;
		Statement statInsert=null;
		String strInsert="INSERT INTO Course(Course_Code,[Course Description])VALUES('" + courseCode + "', '" + courseDescription + "')";
		int result;
		
		conCourses=createConnection();
		try{
			//step 3. Use the connection object derived to Create a SQL Statement
			statInsert=conCourses.createStatement();
			//step 4. Execute the statement and store the result.
			result=statInsert.executeUpdate(strInsert);
			statInsert.close();
			conCourses.close();
			if (result==1){
				JOptionPane.showMessageDialog(null, "Question was added successfully.");
			}
			else{
				JOptionPane.showMessageDialog(null, "Question was not added to the Database.");
			}
		
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
			//System.exit(1);
			//e.printStackTrace();
		}
				
	}
	//Create a method for Saving/Updating Records in the Database
	public void updateCourse(){
		Connection conCourses;
		Statement statUpdate=null;
		String strUpdate="UPDATE Course SET Course_Code = '"+courseCode+"', [Course Description] = '"+ courseDescription +"' WHERE Course_Code = '"+courseCode+"'";
		int result;
		
		conCourses=this.createConnection();
		try{
			//step 3. Use the connection object derived to Create a SQL Statement
			statUpdate=conCourses.createStatement();
			//step 4. Execute the statement and store the result.
			result=statUpdate.executeUpdate(strUpdate);
			statUpdate.close();
			conCourses.close();
			if (result==1){  //when result ==1 database query was successful
				JOptionPane.showMessageDialog(null, "Changes to the question are saved.");
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
	
	//Create a method for Deleting from the Database
	public void deleteCourse(){
		Connection conQuestions;
		Statement statDelete=null;
		String strDelete="DELETE FROM Course WHERE Course_Code='" + courseCode + "'";
		int result;
		
		conQuestions=this.createConnection();
		try{
			//step 3. Use the connection object derived to Create a SQL Statement
			statDelete=conQuestions.createStatement();
			//step 4. Execute the statement and store the result.
			result=statDelete.executeUpdate(strDelete);
			statDelete.close();
			conQuestions.close();
			if (result==1){
				JOptionPane.showMessageDialog(null, "The Question is deleted from the database.");
			}
			else{
				JOptionPane.showMessageDialog(null, "The Question could not be deleted.");
			}
		
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
			//System.exit(1);
			//e.printStackTrace();
		}
	}
	
	
	//Create a method for Finding Item in the data base Key: Course Code
	public ResultSet findCourse(){
		Connection conCourses;
		Statement statFind=null;
		//String strFind = "SELECT Course_Code FROM Course";
		
		String strFind="SELECT * FROM Course WHERE Course_Code='" + courseCode + "'";
		ResultSet result=null;
		
		conCourses=this.createConnection();
		//System.out.println(" Connection done");
		try{
			
			statFind=conCourses.createStatement();
			result=statFind.executeQuery(strFind);
			//System.out.println(" Qeury done");
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
			//System.exit(1);
			//e.printStackTrace();
		}
		
		return result;			
		
	}
	
}


