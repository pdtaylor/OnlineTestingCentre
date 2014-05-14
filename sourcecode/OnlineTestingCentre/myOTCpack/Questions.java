package myOTCpack;
import java.sql.*;

import javax.swing.JOptionPane;

public class Questions extends DatabaseUser {
	private String _qID;
	private String _course;
	private String _question;
	private String _optionA;
	private String _optionB;
	private String _optionC;
	private String _optionD;
	private String _answer;
	
	public Questions(){
		_qID="";
		_course="";
		_question="";
		_optionA="";
		_optionB="";
		_optionC="";
		_optionD="";
		_answer="";			
	}
	public Questions(String id, String c, String q, String oa, String ob, String oc, String od, String a){
		_qID=id;
		_course=c;
		_question=q;
		_optionA=oa;
		_optionB=ob;
		_optionC=oc;
		_optionD=od;
		_answer=a;			
	}
	
	public String getQID(){
		return _qID;
	}
	public String getCourse(){
		return _course;
	}
	public String getQuestion(){
		return _question;
	}
	public String getOptionA(){
		return _optionA;
	}
	public String getOptionB(){
		return _optionB;
	}
	public String getOptionC(){
		return _optionC;
	}
	public String getOptionD(){
		return _optionD;
	}
	public String getAnswer(){
		return _answer;
	}
	public void setQID(String id){
		_qID=id;
	}
	public void setCourse(String c){
		_course=c;
	}
	public void setQuestion(String q){
		_question=q;
	}
	public void setOptionA(String oa){
		_optionA=oa;
	}
	public void setOptionB(String ob){
		_optionB=ob;
	}
	public void setOptionC(String oc){
		_optionC=oc;
	}
	public void setOptionD(String od){
		_optionD=od;
	}
	public void setAnswer(String a){
		_answer=a;
	}
	public String toString(){
		return "ID:" + _qID + " Course:" + _course + " Question:" + _question + " A:" + _optionA + " B:" + _optionB + " C:" + _optionC + " D:" + _optionD + " Answer:" + _answer;
	}
	public void insertQuestion(){
		Connection conQuestions;
		Statement statInsert=null;
		String strInsert="INSERT INTO questions(ID, course_code, question, optionA, optionB, optionC, optionD, answer)VALUES('" + _qID + "', '" + _course + "', '" + _question + "', '" + _optionA +"', '"+_optionB + "', '"+_optionC+"', '"+ _optionD+"', '"+_answer+"')";
		int result;
		
		conQuestions=this.createConnection();
		try{
			//step 3. Use the connection object derived to Create a SQL Statement
			statInsert=conQuestions.createStatement();
			//step 4. Execute the statement and store the result.
			result=statInsert.executeUpdate(strInsert);
			statInsert.close();
			conQuestions.close();
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
	public void updateQuestion(){
		Connection conQuestions;
		Statement statUpdate=null;
		String strUpdate="UPDATE questions SET ID='"+ _qID + "', course_code='"+ _course +"', question='"+ _question +"', optionA='" + _optionA + "', optionB='" + _optionB + "', optionC='" + _optionC + "', optionD='" + _optionD + "', answer='" + _answer +"' WHERE ID='" + _qID + "'";
		int result;
		
		conQuestions=this.createConnection();
		try{
			//step 3. Use the connection object derived to Create a SQL Statement
			statUpdate=conQuestions.createStatement();
			//step 4. Execute the statement and store the result.
			result=statUpdate.executeUpdate(strUpdate);
			statUpdate.close();
			conQuestions.close();
			if (result==1){
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
	public void deleteQuestion(){
		Connection conQuestions;
		Statement statDelete=null;
		String strDelete="DELETE FROM questions WHERE ID='" + _qID + "'";
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
	/*public ResultSet findQuestion(){
		Connection conQuestions;
		Statement statFind=null;
		String strFind="SELECT * FROM questions WHERE ID='" + _qID + "'";
		ResultSet result=null;
		
		conQuestions=this.createConnection();
		try{
			
			statFind=conQuestions.createStatement();
			result=statFind.executeQuery(strFind);
			
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
			//System.exit(1);
			//e.printStackTrace();
		}
		
		return result;			
		
	}*/
	
	public ResultSet findQuestion(){
		Connection conQuestions;
		Statement statFind=null;
		//String strFind="SELECT * FROM questions WHERE ID='" + _qID + "'";
		String strFind="SELECT * FROM questions";
		ResultSet result=null;
		
		conQuestions=this.createConnection();
		try{
			statFind=conQuestions.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			//if (bind){
				
				result=statFind.executeQuery(strFind);
				
			//}
			//else if(!bind){
				//statFind=conQuestions.createStatement();
				//result=statFind.executeQuery(strFind);
				//result.
			//}
			
			
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
			//System.exit(1);
			//e.printStackTrace();
		}
		
		return result;			
		
	}
	
	/*public ResultSet createScrollableResultset(){ 
		Connection conQuestions;
		ResultSet result;
		Statement statFind=null;
		conQuestions=this.createConnection();
		try{
			
			statFind=conQuestions.createStatement();
			result=statFind.executeQuery(strFind);
			
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
			//System.exit(1);
			//e.printStackTrace();
		}
	}*/
	
}