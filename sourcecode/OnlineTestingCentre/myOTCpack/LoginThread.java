package myOTCpack;
import javax.swing.JFrame;

public class LoginThread implements Runnable {
	
	private LogIn newLogon;
		
	public void run(){
		newLogon=new LogIn();
		newLogon.pack();
		newLogon.setSize(500,300);
		newLogon.setLocation(250,200);
		newLogon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newLogon.setVisible(true);
	}

}
