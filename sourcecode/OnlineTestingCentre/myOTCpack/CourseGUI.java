package myOTCpack;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

//Courses Screen

 
public class CourseGUI extends JDialog implements ActionListener {
	private JButton jbtAdd; 
	private JButton jbtSave; 
	private JButton jbtDelete ;
	private JButton jbtFind;
	
	private JTextField txtCourseCode;
	private JTextArea txtCourseDescription;
	private String strFname,strLname;
	private int intLevel;	
	
	//Default Constructor
	public CourseGUI(JDialog parent,String u_fname,String u_lname,int u_level,boolean addmode){
		super(parent);			
		//getContentPane
		//Container cContainer = new Container();
		//FlowLayout cLayout = new FlowLayout();
		//cLayout.setAlignment(FlowLayout.CENTER);
		//cLayout.set(FlowLayout.CENTER);
		this.getContentPane().setLayout(new BorderLayout());
		
		this.setTitle("Course Administration Form");
		
		strFname=u_fname;
		strLname=u_lname;
		intLevel=u_level;
		
		//Creare panels course1,course2
		JPanel pnl1= new JPanel();
		FlowLayout pnl1Layout = new FlowLayout();
		//pnl1Layout.setAlignment(FlowLayout.LEFT);
		pnl1.setLayout(pnl1Layout);
		pnl1.setBorder(new TitledBorder(new EtchedBorder(), null));//
		
		JPanel pnl2= new JPanel();
		FlowLayout pnl2Layout = new FlowLayout();
		pnl2.setLayout(pnl2Layout);
		pnl2.setBorder(new TitledBorder(new EtchedBorder(), null));//
		
		JPanel pnl3 = new JPanel();
		FlowLayout pnl3Layout = new FlowLayout();
		pnl3.setLayout(pnl3Layout);
		pnl3.setBorder(new TitledBorder(new EtchedBorder(), null));//
		
		
		//create labels
		JLabel lblCourseCode = new JLabel("Course Code : ");
		JLabel lblCourseDescription = new JLabel("Course Description : ");
	
		//create textfields
		txtCourseCode = new JTextField(10);
		//txtCourseCode.setLineWrap(true);
		
		txtCourseDescription = new JTextArea(3,20);//text area
		txtCourseDescription.setLineWrap(true);
		JScrollPane scroller =new JScrollPane(txtCourseDescription);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	
		//create buttons
		jbtAdd = new JButton("ADD");
		jbtSave = new JButton("SAVE");
		jbtDelete = new JButton("DELETE");
		jbtFind = new JButton("FIND");
		
		//Add components
		pnl1.add(lblCourseCode);//add course label to panel 1
		pnl1.add(txtCourseCode); //add course code text field to panel 1
		this.getContentPane().add(pnl1,BorderLayout.WEST);
		//this.getContentPane().add(pnl1,BorderLayout.NORTH);
		
		
		pnl2.add(lblCourseDescription);
		pnl2.add(scroller);
		this.getContentPane().add(pnl2, BorderLayout.EAST);
		//this.getContentPane().add(pnl2,BorderLayout.CENTER);
		
		pnl3.add(jbtAdd);
		pnl3.add(jbtSave);
		pnl3.add(jbtDelete);
		pnl3.add(jbtFind);
		this.getContentPane().add(pnl3,BorderLayout.SOUTH);
		//this.getContentPane().add(pnl3,BorderLayout.SOUTH);
		
		
				
		//Register buttons
		jbtAdd.addActionListener(this);
		jbtSave.addActionListener(this);
		jbtDelete.addActionListener(this);
		jbtFind.addActionListener(this);
		
		if(addmode){
			jbtAdd.setEnabled(true);
			jbtDelete.setEnabled(false);
			jbtSave.setEnabled(false);
			jbtFind.setEnabled(false);
		}
		else{
			jbtAdd.setEnabled(false);
			jbtDelete.setEnabled(true);
			jbtSave.setEnabled(true);
			jbtFind.setEnabled(true);
		}
		
	}
	
	private void settoblank(){
		 txtCourseCode.setText("");
		 txtCourseDescription.setText("");
	}
	
	/*
	//Main Method for testing only
	public static void main(String []args){
		CourseGUI frmCourseAdmin=new CourseGUI();
		//frmCourseAdmin.pack();
		frmCourseAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCourseAdmin.setSize(500,300);
		frmCourseAdmin.setLocation(300, 150);
		frmCourseAdmin.setVisible(true);
	}*/
	
	public void actionPerformed(ActionEvent e){
		//Declare an oblect of type CourseClass to be used in connection
		CourseClass cC1;
		
		if (e.getSource()==jbtAdd){
			System.out.println("Add Button");
			cC1=new CourseClass(this.txtCourseCode.getText().trim(), this.txtCourseDescription.getText().toString());
			//JOptionPane.showMessageDialog(null, question);
			cC1.insertCourse();
			settoblank();
		}
		else if (e.getSource()==jbtSave){
			System.out.println("Save Button");
			cC1=new CourseClass(this.txtCourseCode.getText().toString(), this.txtCourseDescription.getText().trim());
			cC1.updateCourse();
		}
		else if (e.getSource()==jbtDelete){
			System.out.println("Delete button");
				cC1=new CourseClass();
				cC1.setCourseCode(this.txtCourseCode.getText().trim());
				cC1.deleteCourse();
				settoblank();
		
		}
		else if(e.getSource()==jbtFind){
			System.out.println("Find button");
			cC1 =new CourseClass();
			cC1.setCourseCode(this.txtCourseCode.getText().trim());
			ResultSet row= cC1.findCourse();
				
				if (row !=null){
					this.txtCourseCode.setText("");
					try{
						while(row.next()){
							this.txtCourseCode.setText(row.getString("Course_Code"));
							this.txtCourseDescription.setText(row.getString("Course Description"));
						}
					}
					catch(SQLException ex){
						JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
						//System.exit(1);
						//ex.printStackTrace();
					}
				}
				else{
					
					JOptionPane.showMessageDialog(null, "Record not Found in the Database");
				}
		}
	}//End Action Handler
	
}//End Class
	
	
	
	
	
	


