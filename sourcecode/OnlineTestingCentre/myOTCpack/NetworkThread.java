package myOTCpack;

import javax.swing.JFrame;

public class NetworkThread implements Runnable {
	
	private NetworkModule newnet;
	
	public void run(){
		newnet=new NetworkModule();
		newnet.runServer();
		newnet.pack();
		newnet.setSize(200,80);
		newnet.setLocation(10,660);
		newnet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newnet.setVisible(true);
	}

}
