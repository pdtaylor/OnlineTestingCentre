package myOTCpack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JDialog implements ActionListener {
	
	private JMenuBar barMain;
	private JMenu mnuFile;
	private JMenu mnuNew;
	private JMenu mnuEdit;
	private JMenu mnuTest;
	private JMenu mnuView;
	private JMenu mnuHelp;
	
	private JMenuItem itmNewQuestion;
	private JMenuItem itmNewStudent;
	private JMenuItem itmNewUser;
	private JMenuItem itmNewCourse;
	private JMenuItem itmLogout;
	private JMenuItem itmExit;
	private JMenuItem itmOnlineTest;
	private JMenuItem itmEditQuestion;
	private JMenuItem itmEditStudent;
	private JMenuItem itmEditUser;
	private JMenuItem itmEditCourse;
	private JMenuItem itmReport;
	
	private String strFname;
	private String strLname;
	private int intLevel;
	
	public MainMenu(String u_fname, String u_lname, int u_level){
		
		strFname=u_fname;
		strLname=u_lname;
		intLevel=u_level;
		
		barMain=new JMenuBar();
		
		mnuFile=new JMenu("File");
		mnuFile.setMnemonic('F');
		mnuEdit=new JMenu("Edit");
		mnuEdit.setMnemonic('E');
		mnuTest=new JMenu("Test");
		mnuTest.setMnemonic('T');
		mnuView=new JMenu("View");
		mnuView.setMnemonic('V');
		mnuHelp=new JMenu("Help");
		mnuHelp.setMnemonic('H');
		
		mnuNew=new JMenu("New");
		mnuNew.setMnemonic('N');
		itmNewQuestion=new JMenuItem("Question");
		itmNewStudent=new JMenuItem("Student");
		itmNewUser=new JMenuItem("User");
		itmNewCourse=new JMenuItem("Course");
		itmLogout=new JMenuItem("Log Out");
		itmExit=new JMenuItem("Exit");
		itmOnlineTest=new JMenuItem("Online Test");
		itmEditQuestion=new JMenuItem("Question");
		itmEditStudent=new JMenuItem("Student");
		itmEditUser=new JMenuItem("User");
		itmEditCourse=new JMenuItem("Course");
		itmReport=new JMenuItem("Progress Report");
		
		
		this.setJMenuBar(barMain);
		this.setTitle("Welcome to the Online Testing Centre. User:" + strFname + " " + strLname);
		
		barMain.add(mnuFile);
		barMain.add(mnuEdit);
		barMain.add(mnuTest);
		barMain.add(mnuView);
		barMain.add(mnuHelp);
		
		mnuFile.add(mnuNew);
		mnuNew.add(itmNewQuestion);
		mnuNew.add(itmNewStudent);
		mnuNew.add(itmNewUser);
		mnuNew.add(itmNewCourse);
		mnuFile.add(itmLogout);
		mnuFile.add(itmExit);
		
		mnuEdit.add(itmEditQuestion);
		mnuEdit.add(itmEditStudent);
		mnuEdit.add(itmEditUser);
		mnuEdit.add(itmEditCourse);
		
		mnuTest.add(itmOnlineTest);
		
		mnuView.add(itmReport);
		
		if (intLevel==1){
			mnuNew.setEnabled(false);
			mnuEdit.setEnabled(false);
		}
		
		//this.
		
		itmNewQuestion.addActionListener(this);
		itmNewStudent.addActionListener(this);
		itmNewUser.addActionListener(this);
		itmNewCourse.addActionListener(this);
		itmLogout.addActionListener(this);
		itmExit.addActionListener(this);
		
		itmEditQuestion.addActionListener(this);
		itmEditStudent.addActionListener(this);
		itmEditUser.addActionListener(this);
		itmEditCourse.addActionListener(this);
		
		itmOnlineTest.addActionListener(this);
		itmReport.addActionListener(this);
		
		
	}
	
	/*public static void main(String[] args){
		//QuestionAdmin frmQAdmin=new QuestionAdmin();
		MainMenu frmMain=new MainMenu();
		
		//frmQAdmin.pack();
		frmMain.pack();
		//frmQAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frmQAdmin.setSize(500,300);
		frmMain.setSize(800, 600);
		//frmQAdmin.setLocation(300, 150);
		frmMain.setLocation(200, 50);
		//frmQAdmin.setVisible(true);
		frmMain.setVisible(true);
	}*/
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource()==itmOnlineTest){
			OnlineTest newTest=new OnlineTest(this,strFname,strLname,intLevel);
			newTest.pack();
			newTest.setModal(false);
			newTest.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			newTest.setVisible(true);	
			newTest.setLocation(180, 120);
			newTest.setSize(750,500);
			
					
			//frmDeposit.setSize(400,200);
			//frmDeposit.setLocation(300,300);
			//this.setVisible(false);
			//this.setVisible(true);
		}
		else if (e.getSource()==itmNewQuestion){
		
			QuestionAdmin newQuestion=new QuestionAdmin(this,strFname,strLname,intLevel,true);
			newQuestion.pack();
			newQuestion.setModal(false);
			newQuestion.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			newQuestion.setVisible(true);	
			newQuestion.setLocation(320, 120);
			newQuestion.setSize(500,300);
		}
		else if (e.getSource()==itmEditQuestion){
			
			QuestionAdmin editQuestion=new QuestionAdmin(this,strFname,strLname,intLevel,false);
			editQuestion.pack();
			editQuestion.setModal(false);
			editQuestion.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			editQuestion.setVisible(true);	
			editQuestion.setLocation(320, 120);
			editQuestion.setSize(500,300);
		}
		else if(e.getSource()==itmNewStudent){
			StudentGui newStudent=new StudentGui(this,strFname,strLname,intLevel,true);
			newStudent.pack();
			newStudent.setModal(false);
			newStudent.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			newStudent.setVisible(true);	
			newStudent.setLocation(220, 120);
			newStudent.setSize(580,300);
		}
		else if(e.getSource()==itmEditStudent){
			StudentGui editStudent=new StudentGui(this,strFname,strLname,intLevel,false);
			editStudent.pack();
			editStudent.setModal(false);
			editStudent.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			editStudent.setVisible(true);	
			editStudent.setLocation(220, 120);
			editStudent.setSize(580,300);
		}
		else if(e.getSource()==itmNewUser){
			GUIuseradminscreen newUser=new GUIuseradminscreen(this,strFname,strLname,intLevel,true);
			newUser.pack();
			newUser.setModal(false);
			newUser.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			newUser.setVisible(true);	
			newUser.setLocation(220, 120);
			newUser.setSize(500,400);
		}
		else if(e.getSource()==itmEditUser){
			GUIuseradminscreen editUser=new GUIuseradminscreen(this,strFname,strLname,intLevel,false);
			editUser.pack();
			editUser.setModal(false);
			editUser.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			editUser.setVisible(true);	
			editUser.setLocation(220, 120);
			editUser.setSize(500,400);
		}
		else if(e.getSource()==itmNewCourse){
			CourseGUI newCourse=new CourseGUI(this,strFname,strLname,intLevel,true);
			newCourse.pack();
			newCourse.setModal(false);
			newCourse.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			newCourse.setVisible(true);	
			newCourse.setLocation(220, 120);
			newCourse.setSize(600,170);
		}
		else if(e.getSource()==itmEditCourse){
			CourseGUI editCourse=new CourseGUI(this,strFname,strLname,intLevel,false);
			editCourse.pack();
			editCourse.setModal(false);
			editCourse.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			editCourse.setVisible(true);	
			editCourse.setLocation(220, 120);
			editCourse.setSize(600,170);
		}
		else if(e.getSource()==itmExit){
			System.exit(1);
		}
		else if(e.getSource()==itmLogout){
			
		}
	}
	
	
}
