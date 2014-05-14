package myOTCpack;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import java.net.*;

public class StudentGui extends JDialog implements ActionListener {

	private JTextField fname, lname, S_ID, score;
	private JButton add, delete, edit, search;
	private JComboBox course;
		
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	private JPanel p5;
	private JPanel p6;
	
	private JCheckBox chkNetworkClient;
	private JTextField txtIP;
	private JButton btnConnect;
	private JButton btnDisconnect;
	private JButton btnNext;
	private JButton btnPrevious;
	
	String strFname;
	String strLname;
	int intLevel;
	
	private ObjectOutputStream writeInfo;
	private ObjectInputStream readInfo;
	private Student studentFromServer;
	private String server;
	private Socket clientConnection;
	
	
	public StudentGui(JDialog parent,String u_fname,String u_lname,int u_level,boolean addmode){
		super(parent);
		setTitle("STUDENT ADMINISTRATION FORM");
				
		p1=new JPanel();
		FlowLayout layout1=new FlowLayout();
		p1.setLayout(layout1);
		layout1.setAlignment(FlowLayout.CENTER);
		p1.add(new JLabel("Student ID"));
		p1.add(S_ID=new JTextField(9));
		
		p2=new JPanel();
		FlowLayout layout2=new FlowLayout();
		p2.setLayout(layout2);
		layout2.setAlignment(FlowLayout.LEFT);
		p2.add(new JLabel("Student Name"));
		p2.add(fname=new JTextField(20));
		p2.add(lname=new JTextField(20));
		
		p3=new JPanel();
		FlowLayout layout3=new FlowLayout();
		p3.setLayout(layout3);
		layout3.setAlignment(FlowLayout.LEFT);
		p3. add(new JLabel("Course Name"));
		course=new JComboBox();
		LoadCombobox(course);
		p3.add(course);
		
		p4=new JPanel();
		FlowLayout layout4=new FlowLayout();
		p4.setLayout(layout4);
		layout4.setAlignment(FlowLayout.LEFT);
		p4. add(new JLabel("Student Grade"));
		score=new JTextField(3);
		score.setEditable(false);
		p4.add(score);
		
		
		p5=new JPanel();
		FlowLayout layout5=new FlowLayout();
		p5.setLayout(layout5);
		layout5.setAlignment(FlowLayout.LEFT);
		p5.add(chkNetworkClient=new JCheckBox());
		chkNetworkClient.setText("Connect To Server");
		p5. add(add = new JButton("Add"));
		p5. add(delete = new JButton("Delete"));
		p5. add(edit = new JButton("Edit"));
		p5. add(search = new JButton("Search"));
		
		
		
		p6=new JPanel();
		//FlowLayout layout6=new FlowLayout();
		p6.setLayout(new BorderLayout());
		//layout6.setAlignment(FlowLayout.CENTER);
		p6.setBorder(new TitledBorder(new EtchedBorder(),"Type the IP Address of the Server and Press Connect"));
		p6.add(txtIP=new JTextField(50),BorderLayout.NORTH);
		Box b=Box.createHorizontalBox();
		b.add(btnConnect=new JButton("Connect"));
		b.add(btnDisconnect=new JButton("Disconnect"));
		b.add(btnPrevious=new JButton("<<"));
		btnPrevious.setToolTipText("Previous");
		b.add(btnNext=new JButton(">>"));
		btnNext.setToolTipText("Next");
		p6.add(b,BorderLayout.CENTER);
		//p6.add();
		
		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(p1);
		this.getContentPane().add(p2);
		this.getContentPane().add(p3);
		this.getContentPane().add(p4);
		this.getContentPane().add(p5);
		this.getContentPane().add(p6);
		
		
		add.addActionListener(this);
		delete.addActionListener(this);
		edit.addActionListener(this);
		search.addActionListener(this);
		btnConnect.addActionListener(this);
		btnDisconnect.addActionListener(this);
		btnNext.addActionListener(this);
		btnPrevious.addActionListener(this);
		chkNetworkClient.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(chkNetworkClient.isSelected()){
					btnConnect.setEnabled(true);
					btnDisconnect.setEnabled(true);
					btnNext.setEnabled(true);
					btnPrevious.setEnabled(true);
					p6.setEnabled(true);
					txtIP.setEnabled(true);
				}
				else{
					btnConnect.setEnabled(false);
					btnDisconnect.setEnabled(false);
					btnNext.setEnabled(false);
					btnPrevious.setEnabled(false);
					p6.setEnabled(false);
					txtIP.setEnabled(false);
				}
			}
		});
		
		if(addmode){
			add.setEnabled(true);
			delete.setEnabled(false);
			edit.setEnabled(false);
			search.setEnabled(false);
			btnConnect.setEnabled(false);
			btnDisconnect.setEnabled(false);
			btnNext.setEnabled(false);
			btnPrevious.setEnabled(false);
			p6.setEnabled(false);
			txtIP.setEnabled(false);
		}
		else{
			add.setEnabled(false);
			delete.setEnabled(true);
			edit.setEnabled(true);
			search.setEnabled(true);
			btnConnect.setEnabled(false);
			btnDisconnect.setEnabled(false);
			btnNext.setEnabled(false);
			btnPrevious.setEnabled(false);
			p6.setEnabled(false);
			txtIP.setEnabled(false);
		}
	}
	
	/*private void LoadCombobox(JComboBox s)
	{
		String[] comboboxItems ={"Advanced Programming using Java", "Cost and Management Accounting"};
		for (int i=0;i<=comboboxItems.length-1; i++){
		s.addItem(comboboxItems[i]);
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
	
	public void actionPerformed(ActionEvent s)
	{
		
		if (s.getSource()== add)
		{
			try{
			int a = Integer.parseInt(S_ID.getText());
			Student b = new Student(a, course.getSelectedItem().toString(),fname.getText(),lname.getText(), 0.00);
			b.insertStudent();
			settoblank();
			
			}
			catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(null, "The SID must be an Integer value.\n" + ex.getMessage()+"\n"+ ex.toString());
			}
				
		}
		
		else if (s.getSource()== delete)
		{
			try{
				int a = Integer.parseInt(S_ID.getText());
				Student b=new Student();
				b.setS_ID(Integer.parseInt(this.S_ID.getText()));
				b.setCourse(this.course.getSelectedItem().toString());
				b.deleteStudent();
				settoblank();
				
				}
				catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "The SID must be an Integer value.\n" + ex.getMessage()+"\n"+ ex.toString());
				}
			
		}
		
		else if (s.getSource()== edit)
		{
			
			
			try{
				int a = Integer.parseInt(S_ID.getText());
				Student b = new Student(a, course.getSelectedItem().toString(),fname.getText(),lname.getText(), 0.00);
				b.editStudent();
				
				}
				catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "The SID must be an Integer value.\n" + ex.getMessage()+"\n"+ ex.toString());
				}
		}
		
		else if (s.getSource()== search)
		{
			try{
				int a = Integer.parseInt(S_ID.getText());
				Student b=new Student();
				b.setS_ID(a);
				b.setCourse(this.course.getSelectedItem().toString());
				ResultSet row=b.SearchStudent(false);
				
				this.S_ID.setText("");
				this.course.setSelectedItem("");
				row.next();
				if (row.getRow()!=0){
					this.S_ID.setText(String.valueOf(row.getInt("s_ID")));
					this.fname.setText(row.getString("fname"));
					this.lname.setText(row.getString("lname"));
					this.course.setSelectedItem(row.getString ("coursename"));
					this.score.setText(String.valueOf(row.getDouble("score")));
				}
				else{
										
					JOptionPane.showMessageDialog(null, "Record not Found in the Database");
					settoblank();
					}
							
				}
				catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "The SID must be an Integer value.\n" + ex.getMessage()+"\n"+ ex.toString());
				}
				catch(SQLException ex){
					JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
				}
			
			
		}
		else if(s.getSource()==btnConnect){
			String servercommand="Client>>connect"; 
			
			runClient(this.txtIP.getText());
			try{
				sendData(servercommand);
				studentFromServer=(Student)readInfo.readObject();
				this.S_ID.setText(String.valueOf(studentFromServer.getS_ID()));
				this.fname.setText(studentFromServer.getfname());
				this.lname.setText(studentFromServer.getlname());
				this.course.setSelectedItem(studentFromServer.getcoursename());
				this.score.setText(String.valueOf(studentFromServer.getscore()));
				
			}
			catch (IOException ex){
				JOptionPane.showMessageDialog(null, "Error connecting to the specified server. \n" + ex.getMessage()+"\n"+ ex.toString());
				//ex.printStackTrace();
			}
			catch(ClassNotFoundException c){
				JOptionPane.showMessageDialog(null, "Unknown object received. \n" + c.getMessage()+"\n"+ c.toString());
			}
		}
		else if(s.getSource()==btnNext){
			String servercommand="Client>>Next"; 
			
			try{
				sendData(servercommand);
				studentFromServer=(Student)readInfo.readObject();
				this.S_ID.setText(String.valueOf(studentFromServer.getS_ID()));
				this.fname.setText(studentFromServer.getfname());
				this.lname.setText(studentFromServer.getlname());
				this.course.setSelectedItem(studentFromServer.getcoursename());
				this.score.setText(String.valueOf(studentFromServer.getscore()));
				
			}
			catch (IOException ex){
				JOptionPane.showMessageDialog(null, "Error connecting to the specified server. \n" + ex.getMessage()+"\n"+ ex.toString());
			}
			catch(ClassNotFoundException c){
				JOptionPane.showMessageDialog(null, "Unknown object received. \n" + c.getMessage()+"\n"+ c.toString());
			}
		}
		else if(s.getSource()==btnPrevious){
			String servercommand="Client>>Previous"; 
			
			try{
				sendData(servercommand);
				studentFromServer=(Student)readInfo.readObject();
				this.S_ID.setText(String.valueOf(studentFromServer.getS_ID()));
				this.fname.setText(studentFromServer.getfname());
				this.lname.setText(studentFromServer.getlname());
				this.course.setSelectedItem(studentFromServer.getcoursename());
				this.score.setText(String.valueOf(studentFromServer.getscore()));
				
			}
			catch (IOException ex){
				JOptionPane.showMessageDialog(null, "Error connecting to the specified server. \n" + ex.getMessage()+"\n"+ ex.toString());
			}
			catch(ClassNotFoundException c){
				JOptionPane.showMessageDialog(null, "Unknown object received. \n" + c.getMessage()+"\n"+ c.toString());
			}
		}
		else if(s.getSource()==btnDisconnect){
			String servercommand="Client>>disconnect"; 
					
			try{
				sendData(servercommand);
				writeInfo.close();
				readInfo.close();
				clientConnection.close();
				
			}
			catch (IOException ex){
				JOptionPane.showMessageDialog(null, "Error connecting to the specified server. \n" + ex.getMessage()+"\n"+ ex.toString());
			}
		}
	
	}
	
	private void settoblank(){
		 fname.setText("");
		 course.setSelectedIndex(0);
		 lname.setText("");
		 S_ID.setText("");
		 score.setText("");
	}
	
	private void runClient(String hostaddress){
		try{
			//creates a socket connection to the server specified
			clientConnection=new Socket(InetAddress.getByName(hostaddress), 12345);
			//clientConnection=new Socket(InetAddress.getLocalHost(), 12345);
			
			//Gets the Streams
			writeInfo=new ObjectOutputStream(clientConnection.getOutputStream());
			writeInfo.flush();
			readInfo=new ObjectInputStream(clientConnection.getInputStream());
					
		}
		catch (IOException ex){
			JOptionPane.showMessageDialog(null, "Error connecting to the specified server. \n" + ex.getMessage()+"\n"+ ex.toString());
			//ex.printStackTrace();
		}
		
	}
	
	private void sendData(String command) throws IOException{
		writeInfo.writeObject(command);
		writeInfo.flush();
	}
	
	
	
	/*public static void main(String args[])
	{
		
		StudentGui frame = new StudentGui();
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setTitle("STUDENT ADMINISTRATION FORM");
		frame.setSize(600, 250);
		frame.setVisible(true);
	}*/
}

	