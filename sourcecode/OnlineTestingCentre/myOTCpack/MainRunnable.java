package myOTCpack;

public class MainRunnable {
	
	Thread dbase;
	Thread network;
	
	public MainRunnable(){
		dbase=new Thread(new LoginThread());
		network=new Thread(new NetworkThread());
		
		dbase.start();
		network.start();
	}
	
	public static void main(String[]args){
		new MainRunnable();
	}

}
