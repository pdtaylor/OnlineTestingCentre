package myOTCpack;
import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

import java.sql.*;

public class NetworkModule extends JFrame {
	private JLabel lblDisplay;
	private ObjectInputStream readObject;
	private ObjectOutputStream writeObject;
	private ServerSocket server;
	private Socket connection;
	private InetAddress clientIP;
	private String clientName;
	private ResultSet result;
	private Student studentInfo;
	private int intTotalRows;
	
	public NetworkModule(){
		super("Network Connection Listener");
				
		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(lblDisplay=new JLabel("Waiting for Connection"));
		//runServer();
	}
	
	public void runServer(){
		try{
			//Create a Server socket listening on port 12345, and allowing 30 queued users
			server=new ServerSocket(12345,30);
			
			while(true){
				//receives a socket connection from a requesting client machine
				connection=server.accept();
				clientIP=connection.getInetAddress(); //gets the IP address of the host 
				clientName=connection.getInetAddress().getHostName();//gets the hostname of the host
				getStreams();
				processInfoReceived();
			}
			
		}
		catch (IOException ex){
			JOptionPane.showMessageDialog(null, "Error in client trying to establish a connection to the server. \n" + ex.getMessage()+"\n"+ ex.toString());
			//ex.printStackTrace();
		}	
	}
	
	private void getStreams()throws IOException{
		
		writeObject=new ObjectOutputStream(connection.getOutputStream());
		writeObject.flush();
				
		readObject=new ObjectInputStream(connection.getInputStream());
	}
	
	private void processInfoReceived()throws IOException{
		String message="";
		
		
		do{
			try{
				message=(String)readObject.readObject();
				if(message.equals("Client>>connect")){
					sendFirstData();
					this.lblDisplay.setText("Client>>connect");
				}
				else if(message.equals("Client>>Next")){
					sendNextData();
					this.lblDisplay.setText("Client>>Next");
				}
				else if(message.equals("Client>>Previous")){
					sendPreviousData();
					this.lblDisplay.setText("Client>>Previous");
				}
				else if(message.equals("Client>>disconnect")){
					sendPreviousData();
					this.lblDisplay.setText("Waiting for Connection");
				}
			}
			catch (ClassNotFoundException ex){
				JOptionPane.showMessageDialog(null, "Unknown object received. \n" + ex.getMessage()+"\n"+ ex.toString());
				//ex.printStackTrace();
			}
			
		}while(!message.equals("Client>>disconnect"));
	}

	private void sendFirstData(){
		DatabaseUser dbUser=new DatabaseUser();
		Connection conStudent;
		Statement statStudent;
		String strStudent="SELECT * FROM students";
		intTotalRows=0;
		try{
			conStudent=dbUser.createConnection();
			statStudent=conStudent.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			result=statStudent.executeQuery(strStudent);
			
			while (result.next()){
				intTotalRows+=1;
			}
			
			result.first();
			
			studentInfo=new Student(result.getInt("s_ID"), result.getString("coursename"), result.getString("fname"), result.getString("lname"), result.getDouble("score"));
			
			
			try{
				//Writes/Sends the record to the Client to receive it.
				writeObject.writeObject(studentInfo);
				writeObject.flush();
				
			}
			catch (IOException ex){
				JOptionPane.showMessageDialog(null, "Error in client trying to establish a connection to the server. \n" + ex.getMessage()+"\n"+ ex.toString());
				//ex.printStackTrace();
			}	
		
		}
		catch(SQLException ex){
				JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
				//System.exit(1);
				//ex.printStackTrace();
		}
		
	}
	
	private void sendNextData(){
		try{
			if(!result.isLast()){
				result.next();
				studentInfo=new Student(result.getInt("s_ID"), result.getString("coursename"), result.getString("fname"), result.getString("lname"), result.getDouble("score"));
				try{
					//Writes/Sends the record to the Client to receive it.
					writeObject.writeObject(studentInfo);
					writeObject.flush();
					
				}
				catch (IOException ex){
					JOptionPane.showMessageDialog(null, "Error in client trying to establish a connection to the server. \n" + ex.getMessage()+"\n"+ ex.toString());
					//ex.printStackTrace();
				}	
			}
			else{
				//JOptionPane.showMessageDialog(null, "You cannot go to the position specified.");
				studentInfo=new Student();
				try{
					//Writes/Sends the record to the Client to receive it.
					writeObject.writeObject(studentInfo);
					writeObject.flush();
					
				}
				catch (IOException ex){
					JOptionPane.showMessageDialog(null, "Error in client trying to establish a connection to the server. \n" + ex.getMessage()+"\n"+ ex.toString());
					//ex.printStackTrace();
				}	
			}
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
			//System.exit(1);
			//ex.printStackTrace();
	}
		
	}
	
	private void sendPreviousData(){
		try{
			if(!result.isFirst()){
				result.previous();
				studentInfo=new Student(result.getInt("s_ID"), result.getString("coursename"), result.getString("fname"), result.getString("lname"), result.getDouble("score"));
				try{
					//Writes/Sends the record to the Client to receive it.
					writeObject.writeObject(studentInfo);
					writeObject.flush();
					
				}
				catch (IOException ex){
					JOptionPane.showMessageDialog(null, "Error in client trying to establish a connection to the server. \n" + ex.getMessage()+"\n"+ ex.toString());
					//ex.printStackTrace();
				}	
			}
			else{
				//JOptionPane.showMessageDialog(null, "You cannot go to the position specified.");
				studentInfo=new Student();
				try{
					//Writes/Sends the record to the Client to receive it.
					writeObject.writeObject(studentInfo);
					writeObject.flush();
					
				}
				catch (IOException ex){
					JOptionPane.showMessageDialog(null, "Error in client trying to establish a connection to the server. \n" + ex.getMessage()+"\n"+ ex.toString());
					//ex.printStackTrace();
				}	
			}
			
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "A Database Error occurred. \n Please check the data being entered or contact the Database Administrator. \n" + ex.getMessage()+"\n"+ ex.toString());
			//System.exit(1);
			//ex.printStackTrace();
	}
		
	}
}
