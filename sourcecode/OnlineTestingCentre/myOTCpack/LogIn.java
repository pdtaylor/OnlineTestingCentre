package myOTCpack;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.GridLayout;
import java.sql.*;
//import java.sql.SQLException;

public class LogIn extends JFrame implements ActionListener 
{
	private JTextField userid;
	private JButton login;
	private JButton cancel;
	private JPasswordField pwd;
		
	/****************MAIN**************/
	
	public LogIn()
	{
		setTitle("LOG IN");
		setBackground(Color.black);
		setForeground(Color.white);
				
		JPanel p1=new JPanel();
		p1.setLayout(new FlowLayout());
		
		p1.add(new JLabel("USER ID"));
		p1.add(userid=new JTextField(10));
		
		p1.add(new JLabel("PASSWORD"));
		p1.add(pwd=new JPasswordField(10));
				
		
		JPanel p2=new JPanel();
		p2.setLayout(new FlowLayout(1));
		p2.add(login=new JButton("LOG IN"));
		p2.add(cancel=new JButton("CANCEL"));
		
		JPanel p3=new JPanel();
		//how do i center this
		
		p3.setLayout(new GridLayout(2,1,5,5));
		//p3.setLayout(new FlowLayout());
		p3.add(new JLabel ("WELCOME TO THE ADVANCED JAVA PROGRAMMING INSTITUION Inc (AJPI)!"));
		p3.add(new JLabel ("PLEASE ENTER YOUR ASSIGNED USERID AND PASSWORD BELOW"));
		
		getContentPane().setLayout(new BorderLayout(50,50));
		getContentPane().add(p3,BorderLayout.NORTH);
		getContentPane().add(p1,BorderLayout.CENTER);
		getContentPane().add(p2,BorderLayout.SOUTH);
				
		login.addActionListener(this);
		cancel.addActionListener(this);
	}
	
	/*public static void main(String args[])
	{
		LogIn frame=new LogIn();
		frame.pack();
		frame.setSize(500,300);
		frame.setLocation(250,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}*/
	
	public void actionPerformed(ActionEvent e)
	{
		String password=new String(pwd.getPassword());
		
		
		
		if (e.getSource()==login)
		{
			String u_fname,u_lname;
			int intLevel;
			
			ResultSet obj=null;
			User newuser=new User();
			newuser.setUserID(userid.getText());
			newuser.setPassword(password);
			obj=newuser.findUser();
			if (obj!=null){	
				
					try{
						obj.next();
						if (obj.getRow()!=0){
							intLevel=obj.getInt("u_level");
							u_fname=obj.getString("u_fname");
							u_lname=obj.getString("u_lname");
								
							MainMenu frmMain=new MainMenu(u_fname,u_lname,intLevel);
							this.setVisible(false);
							frmMain.setModal(true);
							frmMain.pack();
							frmMain.setSize(800, 600);
							frmMain.setLocation(150, 50);
							this.setVisible(false);
							frmMain.setVisible(true);
							frmMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							this.setVisible(true);
						}
						else{
							JOptionPane.showMessageDialog(null, "User Name and/or Password is invalid!");
						}
						
					}
					catch(SQLException ex){
							JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
							//System.exit(1);
							//ex.printStackTrace();
					}
				
				
			}
			/*else if (obj==null){
				JOptionPane.showMessageDialog(null, "User Name and/or Password is invalid!");
			}*/
						
		}
		else if(e.getSource()==cancel){
				System.exit(1);
			}
		
	
	}
		
}//end class



