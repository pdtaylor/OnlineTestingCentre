package myOTCpack;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;


public class QuestionAdmin extends JDialog implements ActionListener{
	private JLabel lblQid;
	private JLabel lblCourse;
	private JLabel lblQuestion;
	private JLabel lbloptionA;
	private JLabel lbloptionB;
	private JLabel lbloptionC;
	private JLabel lbloptionD;
	private JLabel lblAnswer;
	
	private JTextField txtQid;
	private JComboBox cmbCourse;
	private JTextArea txtQuestion;
	private JTextField txtoptionA;
	private JTextField txtoptionB;
	private JTextField txtoptionC;
	private JTextField txtoptionD;
	private JTextField txtAnswer;
	
	private JButton btnAdd;
	private JButton btnSave;
	private JButton btnDelete;
	private JButton btnFind;
	private JButton btnFirst;
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnLast;
	
	private JPanel pnl1;
	private JPanel pnl2;
	private JPanel pnl3;
	private JPanel pnl4;
	
	String strFname;
	String strLname;
	int intLevel;
	
	ResultSet questions;
	Map<String,Integer> mapIDs;
	
	
	public QuestionAdmin(JDialog parent, String u_fname, String u_lname, int u_level, boolean addmode){
		super(parent);
		this.setTitle("Question Administration Form");
		
		strFname=u_fname;
		strLname=u_lname;
		intLevel=u_level;
		
		/////////////////Panel 1/////////////////
		pnl1=new JPanel();
		FlowLayout lytPnl1=new FlowLayout();
		pnl1.setLayout(lytPnl1);
		lytPnl1.setAlignment(FlowLayout.CENTER);//specifies alignment of the components in the JPanel
		pnl1.add(lblCourse=new JLabel("Course:"));
		
		cmbCourse=new JComboBox();
		LoadCombobox(cmbCourse);//loads combobox items
		pnl1.add(cmbCourse);
		
		/////////////////Panel 2/////////////////
		pnl2=new JPanel();
		FlowLayout lytPnl2=new FlowLayout();
		pnl2.setLayout(lytPnl2);
		lytPnl2.setAlignment(FlowLayout.LEFT);//specifies alignment of the components in the JPanel
		pnl2.add(lblQid=new JLabel("Ques. ID:"));
		pnl2.add(txtQid=new JTextField(5));
		pnl2.add(lblQuestion=new JLabel("Question:"));
		
		txtQuestion=new JTextArea(3,27);//sets rows and columns of text area
		txtQuestion.setLineWrap(true);//sets text wrapping
		
		pnl2.add(new JScrollPane(txtQuestion));//adds scrollbars to the text area and adds it to the panel
		
		/////////////////Panel 3/////////////////
		pnl3=new JPanel(new FlowLayout());
		//creates border around the Panel using TitledBorder (For Borders with Titles) and EtchedBorder (For Lines) objects.
		pnl3.setBorder(new TitledBorder(new EtchedBorder(), "Answers:"));
		
		//Adds components to a Box using box layout//
		Box aTob=Box.createVerticalBox();
		Box b1=Box.createHorizontalBox();
		b1.add(lbloptionA=new JLabel("A."));
		b1.add(txtoptionA=new JTextField(20));
		
		Box b2=Box.createHorizontalBox();
		b2.add(lbloptionB=new JLabel("B."));
		b2.add(txtoptionB=new JTextField(20));
		
		aTob.add(b1);
		aTob.add(b2);
		
		Box cTod=Box.createVerticalBox();
		Box b3=Box.createHorizontalBox();
		b3.add(lbloptionC=new JLabel("C."));
		b3.add(cTod.add(txtoptionC=new JTextField(20)));
		
		Box b4=Box.createHorizontalBox();
		b4.add(lbloptionD=new JLabel("D."));
		b4.add(txtoptionD=new JTextField(20));
		
		
		cTod.add(b3);
		cTod.add(b4);
		
		Box answers=Box.createHorizontalBox();
		
		answers.add(aTob);
		answers.add(cTod);
		pnl3.add(answers);//adds Boxed items to panel 3
		
		/////////////////Panel 4/////////////////
		pnl4=new JPanel();
		pnl4.setLayout(new FlowLayout());
		
		pnl4.add(btnAdd=new JButton("Add"));
		pnl4.add(btnSave=new JButton("Save"));
		pnl4.add(btnDelete=new JButton("Delete"));
		pnl4.add(btnFind=new JButton("Find"));
		
		btnFirst=new JButton("|<");
		btnFirst.setToolTipText("First");
		pnl4.add(btnFirst);
		
		btnPrevious=new JButton("<");
		btnPrevious.setToolTipText("Previous");
		pnl4.add(btnPrevious);
		
		btnNext=new JButton(">");
		btnNext.setToolTipText("Next");
		pnl4.add(btnNext);
		
		btnLast=new JButton(">|");
		btnLast.setToolTipText("Last");
		pnl4.add(btnLast);
				
		//Adds Panels and components to the JFrame Container//		
		/*this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(pnl1, BorderLayout.NORTH);
		this.getContentPane().add(pnl2, BorderLayout.CENTER);
		this.getContentPane().add(answers, BorderLayout.SOUTH);	*/		
		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(pnl1);
		this.getContentPane().add(pnl2);
		this.getContentPane().add(pnl3);
		this.getContentPane().add(lblAnswer=new JLabel("Ans.:"));
		this.getContentPane().add(txtAnswer=new JTextField(20));
		this.getContentPane().add(pnl4);
		
		////Add the Listeners for the relevant components////
		btnAdd.addActionListener(this);
		btnSave.addActionListener(this);
		btnDelete.addActionListener(this);
		btnFind.addActionListener(this);
		btnFirst.addActionListener(this);
		btnPrevious.addActionListener(this);
		btnNext.addActionListener(this);
		btnLast.addActionListener(this);
		/*cmbCourse.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				
			}
		});*/
		
		if(addmode){
			btnAdd.setEnabled(true);
			btnSave.setEnabled(false);
			btnDelete.setEnabled(false);
			btnFind.setEnabled(false);
			btnFirst.setEnabled(false);
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(false);
			btnLast.setEnabled(false);
			BindQuestions();
			settoblank();
		}
		else{
			btnAdd.setEnabled(false);
			btnSave.setEnabled(true);
			btnDelete.setEnabled(true);
			btnFind.setEnabled(true);
			btnFirst.setEnabled(true);
			btnPrevious.setEnabled(true);
			btnNext.setEnabled(true);
			btnLast.setEnabled(true);
			BindQuestions();
		}
		
		
		
	}
	
	/*private void LoadCombobox(JComboBox t){
		
		String[] comboboxItems ={"Advanced Programming using Java", "Cost and Management Accounting"};
		
		for (int i=0;i<=comboboxItems.length-1; i++){
			
			t.addItem(comboboxItems[i]);//adds items to the combobox
		}
		
	}*/
	private void LoadCombobox(JComboBox t){
		String strCourse="Select * From Course";
		Connection conCourse;
		Statement statCourse;
		ResultSet rsCourse;
		DatabaseUser dbCourse=new DatabaseUser();
		conCourse=dbCourse.createConnection();
		try
		{
			
			statCourse=conCourse.createStatement();
			rsCourse=statCourse.executeQuery(strCourse);
			while(rsCourse.next()){
				t.addItem(rsCourse.getString("Course Description"));
			}
			conCourse.close();
			statCourse.close();
						
		}
		
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
			//System.exit(1);
			//e.printStackTrace();
		}
		
	}
	
	
	private void settoblank(){
		 txtQid.setText("");
		 cmbCourse.setSelectedIndex(0);
		 txtQuestion.setText("");
		 txtoptionA.setText("");
		 txtoptionB.setText("");
		 txtoptionC.setText("");
		 txtoptionD.setText("");
		 txtAnswer.setText("");
	}
	
	public void actionPerformed(ActionEvent e){
		Questions question;
		
		if (e.getSource()==btnAdd){
			question=new Questions(this.txtQid.getText().trim(), this.cmbCourse.getSelectedItem().toString(), this.txtQuestion.getText().trim(), this.txtoptionA.getText().trim(), this.txtoptionB.getText().trim(), this.txtoptionC.getText().trim(), this.txtoptionD.getText().trim(), this.txtAnswer.getText().trim());
			//JOptionPane.showMessageDialog(null, question);
			//question.insertQuestion();
			
			try{
				questions.moveToInsertRow();
				questions.updateString("ID", question.getQID());
				questions.updateString("course_code", question.getCourse());
				questions.updateString("question", question.getQuestion());
				questions.updateString("optionA", question.getOptionA());
				questions.updateString("optionB", question.getOptionB());
				questions.updateString("optionC", question.getOptionC());
				questions.updateString("optionD", question.getOptionD());
				questions.updateString("answer", question.getAnswer());
				questions.insertRow();
				questions.moveToCurrentRow();
			}
			catch(SQLException ex){
				JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
				//System.exit(1);
				//ex.printStackTrace();
			}
			settoblank();
		}
		else if(e.getSource()==btnSave){
			question=new Questions(this.txtQid.getText().trim(), this.cmbCourse.getSelectedItem().toString(), this.txtQuestion.getText().trim(), this.txtoptionA.getText().trim(), this.txtoptionB.getText().trim(), this.txtoptionC.getText().trim(), this.txtoptionD.getText().trim(), this.txtAnswer.getText().trim());
			//JOptionPane.showMessageDialog(null, question);
			//question.updateQuestion();
			try{
				//questions.moveToInsertRow();
				//questions.moveToCurrentRow();
				questions.updateString("ID", question.getQID());
				questions.updateString("course_code", question.getCourse());
				questions.updateString("question", question.getQuestion());
				questions.updateString("optionA", question.getOptionA());
				questions.updateString("optionB", question.getOptionB());
				questions.updateString("optionC", question.getOptionC());
				questions.updateString("optionD", question.getOptionD());
				questions.updateString("answer", question.getAnswer());
				questions.updateRow();
				BindQuestions();
				//questions.previous();
				//questions.moveToCurrentRow();
				//setFields();
				JOptionPane.showMessageDialog(null, "Question " + question.getQID()+ " saved.");
			}
			catch(SQLException ex){
				JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
				//System.exit(1);
				//ex.printStackTrace();
			}
		}
		else if(e.getSource()==btnDelete){
			question=new Questions();
			question.setQID(this.txtQid.getText().trim());
			//question.deleteQuestion();
			//settoblank();
			try{
				questions.moveToCurrentRow();
				questions.deleteRow();
				JOptionPane.showMessageDialog(null, "Question " + question.getQID()+ " Deleted.");
				questions.last();
				setFields();
			}
			catch(SQLException ex){
				JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
				//System.exit(1);
				ex.printStackTrace();
			}
			
			
			//questions.//
		}
		else if(e.getSource()==btnFind){
			//question=new Questions();
			//question.setQID(this.txtQid.getText().trim());
			Set<String> keys=mapIDs.keySet();
			boolean flag=false;
			
			for(String key:keys){
				//JOptionPane.showMessageDialog(null, key + ": " + mapIDs.get(key));
				if(key.equals(this.txtQid.getText().trim())){
					try{
						questions.absolute(mapIDs.get(key));
						setFields();
						/*this.txtQid.setText(questions.getString("ID"));
						this.cmbCourse.setSelectedItem(questions.getString("course_code"));
						this.txtQuestion.setText(questions.getString("question"));
						this.txtoptionA.setText(questions.getString("optionA"));
						this.txtoptionB.setText(questions.getString("optionB"));
						this.txtoptionC.setText(questions.getString("optionC"));
						this.txtoptionD.setText(questions.getString("optionD"));
						this.txtAnswer.setText(questions.getString("answer"));*/
						flag=true;
					}
				
				catch(SQLException ex){
					JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
					//System.exit(1);
					//ex.printStackTrace();
				}
					
				}
			}//END FOR
			if(!flag){
				JOptionPane.showMessageDialog(null, "Record not Found in the Database");
			}
			
			//this.txtQid.setText("");
								
		}	//END BUTTON	
		else if(e.getSource()==btnFirst){
			
			try{
				
				questions.first();
				setFields();
			}
		
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
			//System.exit(1);
			//ex.printStackTrace();
		}
			
		}//END BUTTON
		
		else if(e.getSource()==btnPrevious){
			try{
				//questions.refreshRow();
				if(!questions.isFirst()){
					questions.previous();
					setFields();
				}
				else{
					JOptionPane.showMessageDialog(null, "You cannot go to the position specified.");
				}
				
			}
		
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
			//System.exit(1);
			//ex.printStackTrace();
		}
		}
		else if(e.getSource()==btnNext){
			try{
				if(!questions.isLast()){
					questions.next();
					setFields();
				}
				else{
					JOptionPane.showMessageDialog(null, "You cannot go to the position specified.");
				}
				
			}
		
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
			//System.exit(1);
			//ex.printStackTrace();
		}
		}
		else if(e.getSource()==btnLast){
			try{
				
				questions.last();
				setFields();
			}
		
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
			//System.exit(1);
			//ex.printStackTrace();
		}
		}
		
	}//End Handler
	
	private void setFields(){
		try{
			//questions.refreshRow();
			this.txtQid.setText(questions.getString("ID"));
			this.cmbCourse.setSelectedItem(questions.getString("course_code"));
			this.txtQuestion.setText(questions.getString("question"));
			this.txtoptionA.setText(questions.getString("optionA"));
			this.txtoptionB.setText(questions.getString("optionB"));
			this.txtoptionC.setText(questions.getString("optionC"));
			this.txtoptionD.setText(questions.getString("optionD"));
			this.txtAnswer.setText(questions.getString("answer"));
			
		}
	
	catch(SQLException ex){
		JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
		//System.exit(1);
		//ex.printStackTrace();
	}
	}
	
	private void BindQuestions(){
		Questions questionsToBind=new Questions();
		
		questions=questionsToBind.findQuestion();
		createHashMap(questions);
		try{
			questions.first();
			
			this.txtQid.setText(questions.getString("ID"));
			this.cmbCourse.setSelectedItem(questions.getString("course_code"));
			this.txtQuestion.setText(questions.getString("question"));
			this.txtoptionA.setText(questions.getString("optionA"));
			this.txtoptionB.setText(questions.getString("optionB"));
			this.txtoptionC.setText(questions.getString("optionC"));
			this.txtoptionD.setText(questions.getString("optionD"));
			this.txtAnswer.setText(questions.getString("answer"));
			
			
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
			//System.exit(1);
			//ex.printStackTrace();
		}
		
	}
	
	private void createHashMap(ResultSet r){
		mapIDs=new HashMap<String,Integer>();
		//int count=0;		
		try{
			r.first();
			mapIDs.put(r.getString("ID"),r.getRow() );
			while(r.next()){
				mapIDs.put(r.getString("ID"),r.getRow());
			}
			
			/*Set<String> keys=mapIDs.keySet();
			
			for(String key:keys){
				JOptionPane.showMessageDialog(null, key + ": " + mapIDs.get(key));
			}*/
			
			
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
			//System.exit(1);
			//ex.printStackTrace();
		}
		
	}
}//End Class
	
	
	
	

