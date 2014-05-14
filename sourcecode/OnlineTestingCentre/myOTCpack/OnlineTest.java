package myOTCpack;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class OnlineTest extends JDialog implements ItemListener {
		
		JLabel lblSelect;
		JLabel lblScore;
		JComboBox cmbCourse;
		JButton btnClose;
		JPanel panel1;
		JScrollPane spPanel1;
		GridBagLayout layout1;
		GridBagConstraints lytconstraints;
		
		String[] Answers;
		JPanel[] q_panels;
		JLabel[] lblQuestions;
		
		//JLabel lblScore;
		JRadioButton[] rdoOptionsA;
		JRadioButton[] rdoOptionsB;
		JRadioButton[] rdoOptionsC;
		JRadioButton[] rdoOptionsD;
		ButtonGroup[] grpOptions;
		JButton btnSave;
		
		Box[] atod;
		Box[] atob;
		Box[] ctod;
		
		int intScore,i,intLevel;
		String strFname,strLname;
		
		/*ItemListener[] listenerA;
		ItemListener[] listenerB;
		ItemListener[] listenerC;
		ItemListener[] listenerD;*/
		
		
		public OnlineTest(JDialog parent, String u_fname, String u_lname, int u_level){
			super(parent);
			this.setTitle("Online Tests");
			intScore=0;
			strFname=u_fname;
			strLname=u_lname;
			intLevel=u_level;
			
			panel1=new JPanel();
			layout1=new GridBagLayout();
			lytconstraints= new GridBagConstraints();
			panel1.setLayout(layout1);
			
			
			spPanel1=new JScrollPane(panel1);
			spPanel1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			spPanel1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
			
			lblSelect=new JLabel("Select a Course/Test to begin.");
			lytconstraints.weightx=1;
			lytconstraints.weighty=1;
			lytconstraints.gridwidth=1;
			//lytconstraints.gridheight=2;
			lytconstraints.anchor=GridBagConstraints.NORTHWEST;
			//lytconstraints.anchor=GridBagConstraints.CENTER;
			layout1.setConstraints(lblSelect, lytconstraints);
			panel1.add(lblSelect);
			
					
			cmbCourse=new JComboBox();
			LoadCombobox(cmbCourse);
			lytconstraints.gridwidth=GridBagConstraints.RELATIVE;
			//lytconstraints.gridheight=GridBagConstraints.RELATIVE;
			//lytconstraints.anchor=GridBagConstraints.CENTER;
			layout1.setConstraints(cmbCourse, lytconstraints);
			panel1.add(cmbCourse);
			
			/*lblScore=new JLabel("Test");
			lytconstraints.gridwidth=GridBagConstraints.REMAINDER;
			lytconstraints.gridheight=GridBagConstraints.REMAINDER;
			layout1.setConstraints(lblScore, lytconstraints);
			panel1.add(lblScore);*/
			
			lblScore=new JLabel();
			lblScore.setName("lblScore");
			btnSave=new JButton("Save Score");
			btnSave.setName("btnSave");
			btnSave.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(e.getSource()==btnSave){
						updateScore();
						
						
					}
				}
			});
			
			btnClose=new JButton("Close");
			lytconstraints.gridwidth=GridBagConstraints.REMAINDER;
			//lytconstraints.gridheight=GridBagConstraints.RELATIVE;
			//lytconstraints.anchor=GridBagConstraints.CENTER;
			layout1.setConstraints(btnClose, lytconstraints);
			panel1.add(btnClose);
			
			cmbCourse.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e){
					ResultSet r=null;
					
					if (e.getStateChange()==ItemEvent.SELECTED){
						//JOptionPane.showMessageDialog(null, cmbCourse.getSelectedItem().toString());
						r=getQuestionsFromTable(cmbCourse.getSelectedItem().toString());
						LoadQuestionPanels(r);
												
						try{

							r.close();
											
						}
						catch(SQLException ex){
							JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
							//System.exit(1);
							//e.printStackTrace();
						}	
						
						
					}
				}
			});
			
			//spPanel1.validate();
								
			this.getContentPane().setLayout(new BorderLayout());
			this.getContentPane().add(spPanel1);
			
			
			
		}
		
		/*private void updateScore(Student studentToUpdate){
			Connection conStudent;
			Statement statStudent;
			
			DatabaseUser dbUser=new DatabaseUser();
			
		}*/
		
		private void updateScore(){
			ResultSet r;
			
			Student studentToFind=new Student();
			studentToFind.setfname(strFname);
			studentToFind.setlname(strLname);
			
			r=studentToFind.SearchStudent(true);
			
			try{
				while(r.next()){
					if(r.getRow()!=0){
						String tempCourse=r.getString("coursename");
						//JOptionPane.showMessageDialog(null, tempCourse + " = " + this.cmbCourse.getSelectedItem().toString() );
						if (r.getString("fname").equals(strFname) && r.getString("lname").equals(strLname)&& tempCourse.equals(this.cmbCourse.getSelectedItem().toString())){
							studentToFind.setS_ID(r.getInt("s_ID"));
							studentToFind.setCourse(tempCourse);
							double average=((double)intScore/i)*100;
							//double average=(double)1/20;
							//JOptionPane.showMessageDialog(null, +average);
							studentToFind.setscore(average);
							studentToFind.editStudent();
						}
						/*else{
							JOptionPane.showMessageDialog(null, "There is no record of this course for this Student.\n Please add a Student/Course record to save the score for this course.");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "There is no Student record for this User.\n Please add a Student record to save the score.");
					}*/
					}
				}
				
				//r.next();
							
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
			
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
		
		public ResultSet getQuestionsFromTable(String itemSelected){
			//Question questions
			Statement statQuestions=null;
			ResultSet result=null;
			String strQuestions="SELECT * FROM questions WHERE course_code='" + itemSelected + "'";
			DatabaseUser questionLoader=new DatabaseUser();
			Connection conQuestions =questionLoader.createConnection();
			
			try{
				statQuestions=conQuestions.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				result=statQuestions.executeQuery(strQuestions);
												
			}
			catch(SQLException e){
				JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
				//System.exit(1);
				//e.printStackTrace();
			}				
			
			return result;
		}
		
		public void removePanels(){
			Component[] comp=panel1.getComponents();
			
			for(int i=0; i<=comp.length-1; i++){
				if(comp[i].getClass().toString().equals("class javax.swing.JPanel")){
					//JOptionPane.showMessageDialog(null,comp[i].getClass().toString());
					panel1.remove(comp[i]);
				}
				
				if (comp[i].getName()!=null){
					if(comp[i].getName().equals("btnSave")){
						panel1.remove(comp[i]);
						
					}
					else if(comp[i].getName().equals("lblScore")){
						panel1.remove(comp[i]);
						
					}
				}
							
			}
			
			lblScore.setText("");
			intScore=0;			
		}
		
		public void LoadQuestionPanels(ResultSet val){
			removePanels();
			int numberofrows=0;
			int v=0;
			
			String question;
			int question_no=0;
			try{
				while(val.next()){
					
					if (val.isLast()){
						numberofrows=val.getRow();
						//JOptionPane.showMessageDialog(null, +numberofrows);
					}
								
				}
				/*listenerA=new ItemListener [numberofrows];
				listenerB=new ItemListener [numberofrows];
				listenerC=new ItemListener [numberofrows];
				listenerD=new ItemListener [numberofrows];*/
				
				Answers=new String[numberofrows];
				q_panels=new JPanel[numberofrows];
				lblQuestions=new JLabel[numberofrows];
				rdoOptionsA=new JRadioButton[numberofrows];
				rdoOptionsB=new JRadioButton[numberofrows];
				rdoOptionsC=new JRadioButton[numberofrows];
				rdoOptionsD=new JRadioButton[numberofrows];
				grpOptions=new ButtonGroup[numberofrows];
				
				atod=new Box[numberofrows];
				atob=new Box[numberofrows];
				ctod=new Box[numberofrows];
				
				atod[0]=Box.createHorizontalBox();
				atob[0]=Box.createVerticalBox();
				ctod[0]=Box.createVerticalBox();
				
				val.first();//getRow value is 1
				Answers[0]=new String(val.getString("answer"));
				q_panels[0]=new JPanel(new BorderLayout());
				question_no+=1;
				question=String.valueOf(question_no) + ". " + val.getString("question");
				q_panels[0].add(lblQuestions[0]=new JLabel(question), BorderLayout.NORTH);
				
				atob[0].add(rdoOptionsA[0]=new JRadioButton("A. " + val.getString("optionA"),false));
				atob[0].add(rdoOptionsB[0]=new JRadioButton("B. "+ val.getString("optionB"),false));
				ctod[0].add(rdoOptionsC[0]=new JRadioButton("C. "+ val.getString("optionC"),false));
				ctod[0].add(rdoOptionsD[0]=new JRadioButton("D. "+ val.getString("optionD"),false));
				grpOptions[0]=new ButtonGroup();
				grpOptions[0].add(rdoOptionsA[0]);
				grpOptions[0].add(rdoOptionsB[0]);
				grpOptions[0].add(rdoOptionsC[0]);
				grpOptions[0].add(rdoOptionsD[0]);
				
				rdoOptionsA[0].addItemListener(this);
				rdoOptionsB[0].addItemListener(this);
				rdoOptionsC[0].addItemListener(this);
				rdoOptionsD[0].addItemListener(this);
				
				atod[0].add(atob[0]);
				atod[0].add(ctod[0]);
				q_panels[0].add(atod[0], BorderLayout.SOUTH);
				
				lytconstraints.gridwidth=GridBagConstraints.REMAINDER;
				lytconstraints.anchor=GridBagConstraints.WEST;
				//lytconstraints.anchor=GridBagConstraints.CENTER;
				layout1.setConstraints(q_panels[0], lytconstraints);
				
				q_panels[0].setBorder(new TitledBorder(new EtchedBorder()));
				panel1.add(q_panels[0]);
				panel1.validate();
				spPanel1.validate();
				//JOptionPane.showMessageDialog(null, val.getString("question"));
				
				for (i=1; i<=numberofrows-1; i++){
					val.next();
					//JOptionPane.showMessageDialog(null, "Array:" + i + " row:" +val.getRow());
					Answers[i]=new String(val.getString("answer"));
					q_panels[i]=new JPanel(new BorderLayout());
					question_no+=1;
					question=String.valueOf(question_no) + ". " + val.getString("question");
					q_panels[i].add(lblQuestions[i]=new JLabel(question), BorderLayout.NORTH);
					
					atod[i]=Box.createHorizontalBox();
					atob[i]=Box.createVerticalBox();
					ctod[i]=Box.createVerticalBox();
					
					atob[i].add(rdoOptionsA[i]=new JRadioButton("A. " + val.getString("optionA"),false));
					atob[i].add(rdoOptionsB[i]=new JRadioButton("B. "+ val.getString("optionB"),false));
					ctod[i].add(rdoOptionsC[i]=new JRadioButton("C. "+ val.getString("optionC"),false));
					ctod[i].add(rdoOptionsD[i]=new JRadioButton("D. "+ val.getString("optionD"),false));
					grpOptions[i]=new ButtonGroup();
					grpOptions[i].add(rdoOptionsA[i]);
					grpOptions[i].add(rdoOptionsB[i]);
					grpOptions[i].add(rdoOptionsC[i]);
					grpOptions[i].add(rdoOptionsD[i]);
					
										
					rdoOptionsA[i].addItemListener(this);
					rdoOptionsB[i].addItemListener(this);
					rdoOptionsC[i].addItemListener(this);
					rdoOptionsD[i].addItemListener(this);
					
					atod[i].add(atob[i]);
					atod[i].add(ctod[i]);
					q_panels[i].add(atod[i], BorderLayout.SOUTH);
					
					lytconstraints.gridwidth=GridBagConstraints.REMAINDER;
					lytconstraints.anchor=GridBagConstraints.WEST;
					//lytconstraints.anchor=GridBagConstraints.CENTER;
					layout1.setConstraints(q_panels[i], lytconstraints);
					
					q_panels[i].setBorder(new TitledBorder(new EtchedBorder()));
					panel1.add(q_panels[i]);
					panel1.validate();
					spPanel1.validate();
					//JOptionPane.showMessageDialog(null,i);
				}
				
				
				lytconstraints.gridwidth=GridBagConstraints.RELATIVE;
				lytconstraints.anchor=GridBagConstraints.SOUTHWEST;
				//lytconstraints.gridheight=GridBagConstraints.REMAINDER;
				layout1.setConstraints(btnSave, lytconstraints);
				panel1.add(btnSave);
				
				
				lytconstraints.gridwidth=GridBagConstraints.REMAINDER;
				lytconstraints.anchor=GridBagConstraints.SOUTHEAST;
				//lytconstraints.gridheight=GridBagConstraints.REMAINDER;
				layout1.setConstraints(lblScore, lytconstraints);
				panel1.add(lblScore);
				
				panel1.validate();
				spPanel1.validate();
				
				//panel1.validate();
			}
			catch(SQLException e){
				JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + e.getMessage()+"\n"+ e.toString());
				//System.exit(1);
				//e.printStackTrace();
			}
			catch (ArrayIndexOutOfBoundsException ex){
				JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
			}
			
		}
		
		public void itemStateChanged(ItemEvent e){
			String substring;
			
			for (int count=0;count<=i-1; count++){
				if(e.getSource()==rdoOptionsA[count]){
					substring=rdoOptionsA[count].getText().substring(3);
					if (e.getStateChange()==ItemEvent.SELECTED){
						if(substring.equals(Answers[count])){
							intScore+=1;
							//JOptionPane.showMessageDialog(null, "Score: " +intScore);
							lblScore.setText("Score=" + intScore + "/" + i);
						}
						
					}
					else if (e.getStateChange()==ItemEvent.DESELECTED){
						if(substring.equals(Answers[count])){
							intScore-=1;
							//JOptionPane.showMessageDialog(null, "Score: " +intScore);/
							lblScore.setText("Score=" + intScore + "/" + i);
						}
					}
				}
				else if(e.getSource()==rdoOptionsB[count]){
					substring=rdoOptionsB[count].getText().substring(3);
					if (e.getStateChange()==ItemEvent.SELECTED){
						if(substring.equals(Answers[count])){
							intScore+=1;
							//JOptionPane.showMessageDialog(null, "Score: " +intScore);
							lblScore.setText("Score=" + intScore + "/" + i);
						}
						
					}
					else if (e.getStateChange()==ItemEvent.DESELECTED){
						if(substring.equals(Answers[count])){
							intScore-=1;
							//JOptionPane.showMessageDialog(null, "Score: " +intScore);
							lblScore.setText("Score=" + intScore + "/" + i);
						}
					}
				}
				else if(e.getSource()==rdoOptionsC[count]){
					substring=rdoOptionsC[count].getText().substring(3);
					if (e.getStateChange()==ItemEvent.SELECTED){
						if(substring.equals(Answers[count])){
							intScore+=1;
							//JOptionPane.showMessageDialog(null, "Score: " +intScore);
							lblScore.setText("Score=" + intScore + "/" + i);
						}
						
					}
					else if (e.getStateChange()==ItemEvent.DESELECTED){
						if(substring.equals(Answers[count])){
							intScore-=1;
							//JOptionPane.showMessageDialog(null, "Score: " +intScore);
							lblScore.setText("Score=" + intScore + "/" + i);
						}
					}
				}
				else if(e.getSource()==rdoOptionsD[count]){
					substring=rdoOptionsD[count].getText().substring(3);
					if (e.getStateChange()==ItemEvent.SELECTED){
						if(substring.equals(Answers[count])){
							intScore+=1;
							//JOptionPane.showMessageDialog(null, "Score: " +intScore);
							lblScore.setText("Score=" + intScore + "/" + i);
						}
						
					}
					else if (e.getStateChange()==ItemEvent.DESELECTED){
						if(substring.equals(Answers[count])){
							intScore-=1;
							//JOptionPane.showMessageDialog(null, "Score: " +intScore);
							lblScore.setText("Score=" + intScore + "/" + i);
						}
					}
				}
				
			}//END FOR LOOP
		}//end of ItemListener Handler
		
		
	 
}//end of class
