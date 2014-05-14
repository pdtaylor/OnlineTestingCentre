package myOTCpack;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.GridLayout;
import java.sql.*;

public class GUIuseradminscreen extends JDialog implements ActionListener 
{
	private JTextField intro;
	private JTextField txtuserid,txtfname,txtlname;
	private JComboBox cmbLevel;
	private JButton add,delete,modify,find;
	private JPasswordField txtpassword, txtconfirmpassword;
	String strFname, strLname;
	int intLevel;
	
	/****************MAIN**************/
	
	/*public static void main(String args[])
	{
		GUIuseradminscreen frame=new GUIuseradminscreen();
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("USER ACCOUNT ADMINISTRATION");
		frame.setSize(500, 400);
	}*/
	
	
	public void LoadCombobox(JComboBox t)
	{
		String[] comboboxItems ={"1", "2"};
		for (int i=0;i<=comboboxItems.length-1; i++)
			t.addItem(comboboxItems[i]);//adds items to the combobox
	}
	
	private void settoblank(){
		 txtuserid.setText("");
		 cmbLevel.setSelectedIndex(0);
		 txtfname.setText("");
		 txtlname.setText("");
		 txtpassword.setText("");
		 txtconfirmpassword.setText("");
	}
	
	public GUIuseradminscreen(JDialog parent,String u_fname,String u_lname,int u_level,boolean addmode)
	{
		super(parent);
		strFname=u_fname;
		strLname=u_lname;
		intLevel=u_level;
		
		setTitle("USER ADMINISTRATION");
		setBackground(Color.black);
		setForeground(Color.pink);
		
		
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(6,2,10,20));
		
		p1.add(new JLabel("USER ID"));
		p1.add(txtuserid=new JTextField(5));
		
		p1.add(new JLabel("PASSWORD"));
		p1.add(txtpassword=new JPasswordField(10));
		
		p1.add(new JLabel("CONFIRM PASSWORD"));
		p1.add(txtconfirmpassword=new JPasswordField(10));
		
		p1.add(new JLabel("FIRST NAME"));
		p1.add(txtfname=new JTextField(10));
		
		p1.add(new JLabel("LAST NAME"));
		p1.add(txtlname=new JTextField(10));
		
		p1.add(new JLabel("LEVEL"));
				
		cmbLevel=new JComboBox();
		LoadCombobox(cmbLevel);//loads combobox items
		p1.add(cmbLevel);
		
		JPanel p2=new JPanel();
		p2.setLayout(new FlowLayout(1));
		p2.add(add=new JButton("ADD USER"));
		p2.add(delete=new JButton("DELETE USER"));
		p2.add(modify=new JButton("MODIFY USER ACCOUNT"));
		p2.add(find=new JButton("FIND USER"));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p1,BorderLayout.NORTH);
		getContentPane().add(p2,BorderLayout.SOUTH);
				
		add.addActionListener(this);
		delete.addActionListener(this);
		modify.addActionListener(this);
		find.addActionListener(this);
		
		if(addmode){
			add.setEnabled(true);
			delete.setEnabled(false);
			modify.setEnabled(false);
			find.setEnabled(false);
		}
		else{
			add.setEnabled(false);
			delete.setEnabled(true);
			modify.setEnabled(true);
			find.setEnabled(true);
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String pwd=new String(txtpassword.getPassword());
		String cpwd=new String(txtconfirmpassword.getPassword());
		String tempUID=txtuserid.getText();
		String tempFname=txtfname.getText();
		String tempLname=txtlname.getText();
		Integer tempLevel=Integer.parseInt(cmbLevel.getSelectedItem().toString());
		
		if(e.getSource()==add)
		{
			User J=new User(tempUID,pwd,tempFname,tempLname,cpwd,tempLevel);
			
			if (pwd.equals(cpwd)){
				J.addUser();
				settoblank();
			}
			else
				JOptionPane.showMessageDialog(null, "Password not same as Confirm Password");
			
		}
		
		if (e.getSource()==delete)
		{
			User F=new User();
			F.setUserID(txtuserid.getText());
			F.setPassword(pwd);
			F.deleteUser();
			settoblank();
		}
		
		if (e.getSource()==find)
		{
			ResultSet obj;
			User V=new User();
			V.setUserID(txtuserid.getText());
			V.setPassword(pwd);
			obj=V.findUser();
							
				try{
					obj.next();
					this.txtuserid.setText("");
					this.txtpassword.setText("");
					if(obj.getRow()!=0){
						this.txtuserid.setText(obj.getString("u_ID"));
						this.cmbLevel.setSelectedItem(obj.getInt("u_level"));
						this.txtfname.setText(obj.getString("u_fname"));
						this.txtlname.setText(obj.getString("u_lname"));
						this.txtpassword.setText(obj.getString("pword"));
						this.txtconfirmpassword.setText(obj.getString("confirm_pword"));
					}
					else{
						
						JOptionPane.showMessageDialog(null, "Record not Found in the Database");
						settoblank();
					}
					
				}
				catch(SQLException ex){
						JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
						//System.exit(1);
						//ex.printStackTrace();
				}
									
		}
		
		if (e.getSource()==modify)
		{
			User B=new User(tempUID,pwd,tempFname,tempLname,cpwd,tempLevel);
			B.modifyUser();
		}
	}
}




